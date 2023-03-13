package pama1234.gdx.game.duel.util;

import java.util.ArrayList;

public final class ObjectPool<T extends Poolable<?>>{
  public final int poolSize;
  public final ArrayList<T> pool;
  public int index=0;
  public final ArrayList<T> temporalInstanceList;
  public int temporalInstanceCount=0;
  public int allocationCount=0;
  public ObjectPool(int pSize) {
    poolSize=pSize;
    pool=new ArrayList<T>(pSize);
    temporalInstanceList=new ArrayList<T>(pSize);
  }
  public ObjectPool() {
    this(256);
  }
  public T allocate() {
    if(!isAllocatable()) {
      System.out.print("Object pool allocation failed. Too many objects created!");
      // Need exception handling
      return null;
    }
    T allocatedInstance=pool.get(index);
    allocatedInstance.setAllocated(true);
    allocatedInstance.setAllocationIdentifier(allocationCount);
    index++;
    allocationCount++;
    return allocatedInstance;
  }
  public T allocateTemporal() {
    T allocatedInstance=allocate();
    setTemporal(allocatedInstance);
    return allocatedInstance;
  }
  public void storeObject(T obj) {
    if(pool.size()>=poolSize) {
      System.out.print("Failed to store a new instance to object pool. Object pool is already full.");
      // Need exception handling
    }
    pool.add(obj);
    obj.setBelongingPool(this);
    obj.setAllocationIdentifier(-1);
    obj.setAllocated(false);
  }
  public boolean isAllocatable() {
    return index<poolSize;
  }
  public void deallocate(T killedObject) {
    if(!killedObject.isAllocated()) {
      return;
    }
    killedObject.initialize();
    killedObject.setAllocated(false);
    killedObject.setAllocationIdentifier(-1);
    index--;
    pool.set(index,killedObject);
  }
  public void update() {
    while(temporalInstanceCount>0) {
      temporalInstanceCount--;
      deallocate(temporalInstanceList.get(temporalInstanceCount));
    }
    temporalInstanceList.clear(); // not needed when array
  }
  public void setTemporal(T obj) {
    temporalInstanceList.add(obj); // set when array
    temporalInstanceCount++;
  }
}