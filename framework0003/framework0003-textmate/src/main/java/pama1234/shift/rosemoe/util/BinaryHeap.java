package pama1234.shift.rosemoe.util;

import static pama1234.shift.rosemoe.util.IntPair.getFirst;
import static pama1234.shift.rosemoe.util.IntPair.getSecond;
import static pama1234.shift.rosemoe.util.IntPair.pack;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import com.badlogic.gdx.utils.IntIntMap;

public class BinaryHeap{
  public final Lock lock=new ReentrantLock();
  private final IntIntMap idToPosition;
  private int idAllocator=1;
  private int nodeCount;
  private long[] nodes;
  public BinaryHeap() {
    idToPosition=new IntIntMap();
    nodeCount=0;
    nodes=new long[129];
  }
  private static int id(long value) {
    return getFirst(value);
  }
  private static int data(long value) {
    return getSecond(value);
  }
  public void clear() {
    nodeCount=0;
    idToPosition.clear();
    idAllocator=-1;
  }
  public void ensureCapacity(int capacity) {
    capacity++;
    if(nodes.length<capacity) {
      var origin=nodes;
      if(nodes.length<<1>=capacity) {
        nodes=new long[nodes.length<<1];
      }else {
        nodes=new long[capacity];
      }
      System.arraycopy(origin,0,nodes,0,nodeCount+1);
    }
  }
  public int top() {
    if(nodeCount==0) {
      return 0;
    }
    return data(nodes[1]);
  }
  public int getNodeCount() {
    return nodeCount;
  }
  private void heapifyDown(int position) {
    for(int child=position*2;child<=nodeCount;child=position*2) {
      long parentNode=nodes[position],childNode;
      if(child+1<=nodeCount&&data(nodes[child+1])>data(nodes[child])) {
        child=child+1;
      }
      childNode=nodes[child];
      if(data(parentNode)<data(childNode)) {
        idToPosition.put(id(childNode),position);
        idToPosition.put(id(parentNode),child);
        nodes[child]=parentNode;
        nodes[position]=childNode;
        position=child;
      }else {
        break;
      }
    }
  }
  private void heapifyUp(int position) {
    for(int parent=position/2;parent>=1;parent=position/2) {
      long childNode=nodes[position],parentNode=nodes[parent];
      if(data(childNode)>data(parentNode)) {
        idToPosition.put(id(childNode),parent);
        idToPosition.put(id(parentNode),position);
        nodes[position]=parentNode;
        nodes[parent]=childNode;
        position=parent;
      }else {
        break;
      }
    }
  }
  public int push(int value) {
    ensureCapacity(nodeCount+1);
    if(idAllocator==Integer.MAX_VALUE) {
      throw new IllegalStateException("unable to allocate more id");
    }
    int id=idAllocator++;
    nodeCount++;
    nodes[nodeCount]=pack(id,value);
    idToPosition.put(id,nodeCount);
    heapifyUp(nodeCount);
    return id;
  }
  public void update(int id,int newValue) {
    int position=idToPosition.get(id,0);
    if(position==0) {
      throw new IllegalArgumentException("trying to update with an invalid id");
    }
    int origin=data(nodes[position]);
    nodes[position]=pack(id(nodes[position]),newValue);
    if(origin<newValue) {
      heapifyUp(position);
    }else if(origin>newValue) {
      heapifyDown(position);
    }
  }
  public void remove(int id) {
    int position=idToPosition.get(id,0);
    if(position==0) {
      throw new IllegalArgumentException("trying to remove with an invalid id");
    }
    idToPosition.remove(id,0);
    // idToPosition.delete(id);
    //Replace removed node with last node
    nodes[position]=nodes[nodeCount];
    //Release node
    nodes[nodeCount--]=0;
    //Do not update heap if it is just the last node
    if(position==nodeCount+1) {
      return;
    }
    idToPosition.put(id(nodes[position]),position);
    heapifyUp(position);
    heapifyDown(position);
  }
}
