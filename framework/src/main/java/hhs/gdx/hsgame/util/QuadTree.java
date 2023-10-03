package hhs.gdx.hsgame.util;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool;

import hhs.gdx.hsgame.entities.BasicEntity;
import hhs.gdx.hsgame.tools.ImpactBox;

// 四叉树分割
// 加快搜索速度
public class QuadTree<T extends Rect>extends BasicEntity{
  int capacity;
  public Node root;
  Array<T> data;
  Array<Node> childs;
  Pool<Node> pool=new Pool<>() {
    @Override
    protected Node newObject() {
      return new Node();
    }
  };
  public QuadTree(float x,float y,float w,float h) {
    this(16,x,y,w,h);
  }
  public QuadTree(int capacity,float x,float y,float w,float h) {
    this.capacity=capacity;
    data=new Array<>(capacity*2);
    childs=new Array<>(capacity);
    root=new Node();
    root.set(x,y,w,h);
  }
  public void add(T value) {
    root.add(value);
  }
  public void addAll(Iterable<? extends T> array) {
    for(T t:array) {
      add(t);
    }
  }
  public Array<T> get(Rect r) {
    return root.getLeaf(r);
  }
  public Array<T> get(float x,float y,float w,float h) {
    return root.getLeaf(x,y,w,h);
  }
  public Array<T> getAll() {
    return root.getAll();
  }
  public void remove(T t) {
    root.remove(t);
  }
  public void removeAll(Iterable<T> array) {
    for(T t:array) {
      remove(t);
    }
  }
  public void debugDraw(ShapeRenderer sr) {
    sr.rect(root.x,root.y,root.width,root.height);
    for(Node n:root.getChild()) {
      sr.rect(n.x,n.y,n.width,n.height);
    }
  }
  class Node extends ImpactBox{
    Array<Node> child=new Array<>(4);
    Array<T> value=new Array<>(capacity+1);
    public Node set(float x,float y,float w,float h) {
      // TODO: Implement this method
      super.set(x,y,w,h);
      return this;
    }
    public void add(T v) {
      if(child.isEmpty()) {
        value.add(v);
        if(value.size>=capacity) {
          buildChild();
          addToChild();
        }
      }else {
        addToChild(v);
      }
    }
    public void buildChild() {
      child.add(pool.obtain().set(x,y,width/2,height/2));
      child.add(pool.obtain().set(x+width/2,y,width/2,height/2));
      child.add(pool.obtain().set(x,y+height/2,width/2,height/2));
      child.add(pool.obtain().set(x+width/2,y+height/2,width/2,height/2));
    }
    public void addToChild(T t) {
      for(Node n:child) {
        if(Rectangle.tmp.set(t.getX(),t.getY(),t.getWidth(),t.getHeight()).overlaps(n)) {
          n.add(t);
        }
      }
      value.clear();
    }
    public void addToChild() {
      for(T t:value) {
        for(Node n:child) {
          if(tmp.set(t.getX(),t.getY(),t.getWidth(),t.getHeight()).overlaps(n)) {
            n.add(t);
          }
        }
      }
      value.clear();
    }
    public Array<T> getLeaf(float x,float y,float w,float h) {
      data.clear();
      leaf(x,y,w,h);
      return data;
    }
    public Array<T> getLeaf(Rect r) {
      data.clear();
      leaf(r);
      return data;
    }
    private void leaf(float x,float y,float w,float h) {
      if(child.isEmpty()) {
        data.addAll(value);
        return;
      }
      for(Node n:child) {
        if(tmp.set(x,y,w,h).overlaps(n)) {
          n.leaf(x,y,w,h);
        }
      }
    }
    private void leaf(Rect r) {
      if(child.isEmpty()) {
        data.addAll(value);
        return;
      }
      for(Node n:child) {
        if(tmp.set(r.getX(),r.getY(),r.getWidth(),r.getHeight()).overlaps(n)) {
          n.leaf(r);
        }
      }
    }
    public Array<Node> getChild() {
      childs.clear();
      child();
      return childs;
    }
    private void child() {
      if(child.isEmpty()) {
        childs.add(this);
        return;
      }
      for(Node n:child) {
        n.child();
      }
    }
    public Array<T> getAll() {
      return getLeaf(this);
    }
    public void remove(T r) {
      if(child.isEmpty()) {
        value.removeValue(r,true);
      }
      for(Node n:child) {
        if(tmp.set(r.getX(),r.getY(),r.getWidth(),r.getHeight()).overlaps(n)) {
          n.remove(r);
        }
      }
    }
  }
  @Override
  public void dispose() {}
  @Override
  public void render(SpriteBatch batch) {}
}
