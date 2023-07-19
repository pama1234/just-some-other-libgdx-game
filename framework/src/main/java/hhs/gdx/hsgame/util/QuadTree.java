package hhs.gdx.hsgame.util;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

public class QuadTree<T extends Rect>{
  public Node<T> root;
  int capacity=64;
  Rectangle rect;
  public Array<T> at=new Array<>();
  public QuadTree(Vector2 pos,Vector2 size) {
    this(pos.x,pos.y,size.x,size.y);
  }
  public QuadTree(float x,float y,float w,float h) {
    this(new Rectangle(x,y,w,h));
  }
  public QuadTree(Rectangle r) {
    rect=r;
    root=new Node<T>(rect);
  }
  public void add(T t) {
    root.add(t);
  }
  public Array<T> get(Vector2 pos) {
    return root.get(pos);
  }
  public Array<T> get(float x,float y) {
    return root.get(x,y);
  }
  public Array<T> get_rect(Rectangle role) {
    at.clear();
    Array<Node<T>> nodes=root.testRect_leaf(role);
    for(Node<T> n:nodes) {
      at.addAll(n.obj);
    }
    return at;
  }
  public class Node<T extends Rect>{
    Node<T> lt,lb,rt,rb;
    Array<T> obj;
    Array<T> tmp=new Array<>();
    Array<Node<T>> nodes=new Array<>();
    Array<Node<T>> nodeTmp=new Array<>();
    public Rectangle size;
    public Node(Vector2 pos,Vector2 size) {
      this(pos.x,pos.y,size.x,size.y);
    }
    public Node(float x,float y,float w,float h) {
      this(new Rectangle(x,y,w,h));
    }
    public Node(Rectangle r) {
      size=r;
      obj=new Array<>(capacity);
    }
    public Array<T> get(Vector2 pos) {
      return get(pos.x,pos.y);
    }
    public Array<T> get(float x,float y) {
      if(lt==null) return obj;
      return testPos(x,y).get(x,y);
    }
    public Array<T> get(Rectangle role) {
      if(nodes.isEmpty()) return obj;
      tmp.clear();
      for(Node<T> n:nodes) {
        if(role.overlaps(n.size)) {
          tmp.addAll(n.get(role));
        }
      }
      return tmp;
    }
    public void add(T t) {
      if(lt==null&&obj.size<capacity) {
        obj.add(t);
        return;
      }
      if(lt==null) {
        divide();
        move();
      }
      for(Node<T> n:testRect_m(t)) n.add(t);
      // testPos(t.getX(), t.getY()).add(t);
    }
    void divide() {
      nodes.add(lb=new Node<>(size.x,size.y,size.width/2,size.height/2));
      nodes.add(lt=new Node<>(size.x,size.y+size.height/2,size.width/2,size.height/2));
      nodes.add(
        rt=new Node<>(
          size.x+size.width/2,
          size.y+size.height/2,
          size.width/2,
          size.height/2));
      nodes.add(rb=new Node<>(size.x+size.width/2,size.y,size.width/2,size.height/2));
    }
    Node<T> testPos(Vector2 pos) {
      return testPos(pos.x,pos.y);
    }
    Node<T> testPos(float x,float y) {
      if(x<=size.x+size.width/2) {
        if(y<=size.y+size.height/2) {
          return lb;
        }else {
          return lt;
        }
      }else {
        if(y<=size.y+size.height/2) {
          return rb;
        }else {
          return rt;
        }
      }
    }
    public Array<T> getSons() {
      if(nodes.isEmpty()) return obj;
      tmp.clear();
      for(Node<T> n:nodes) {
        tmp.addAll(n.getSons());
      }
      return tmp;
    }
    public Array<Node<T>> testRect_m(T t) {
      return testRect_m(Rectangle.tmp.set(t.getX(),t.getY(),t.getWidth(),t.getHeight()));
    }
    public Array<Node<T>> testRect_m(Rectangle role) {
      if(nodes.isEmpty()) return null;
      nodeTmp.clear();
      for(Node<T> n:nodes) {
        if(role.overlaps(n.size)) {
          nodeTmp.add(n);
        }
      }
      return nodeTmp;
    }
    public Array<Node<T>> testRect_leaf(T t) {
      return testRect_leaf(Rectangle.tmp.set(t.getX(),t.getY(),t.getWidth(),t.getHeight()));
    }
    public Array<Node<T>> testRect_leaf(Rectangle role) {
      nodeTmp.clear();
      if(nodes.isEmpty()) {
        nodeTmp.add(this);
        return nodeTmp;
      }
      for(Node<T> n:nodes) {
        if(role.overlaps(n.size)) {
          nodeTmp.addAll(n.testRect_leaf(role));
        }
      }
      return nodeTmp;
    }
    Node<T> testRect(T t) {
      if(lt!=null) {
        Rectangle tmp=new Rectangle(t.getX(),t.getY(),t.getWidth(),t.getHeight());
        if(tmp.overlaps(lt.size)) {
          return lt;
        }
        if(tmp.overlaps(lb.size)) {
          return lb;
        }
        if(tmp.overlaps(rt.size)) {
          return rt;
        }
        if(tmp.overlaps(rb.size)) {
          return rb;
        }
      }
      return null;
    }
    void move() {
      for(T t:obj) {
        for(Node<T> n:testRect_m(t)) n.add(t);
      }
    }
    //    void move() {
    //      for (T t : obj) {
    //        testPos(t.getX(), t.getY()).add(t);
    //      }
    //    }
  }
}
