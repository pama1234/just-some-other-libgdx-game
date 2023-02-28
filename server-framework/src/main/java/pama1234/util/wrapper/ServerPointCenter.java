package pama1234.util.wrapper;

import java.util.ListIterator;

import pama1234.util.entity.ServerPointEntity;

public class ServerPointCenter<T extends ServerPointEntity<?>>extends ServerEntityCenter<T>{
  public float minDist,minDisplayDist;
  public T select;
  public ServerPointCenter() {
    this(4);
  }
  public ServerPointCenter(float u) {
    this.minDist=u;
    this.minDisplayDist=u;
  }
  @Override
  public void update() {
    super.update();
  }
  public void find(float x,float y) {
    float tmd=minDist;
    select=null;
    ListIterator<T> it=list.listIterator(list.size());
    while(it.hasPrevious()) {
      T i=it.previous();
      float td=i.point.pos.dist(x,y);
      if(td<tmd) {
        tmd=td;
        select=i;
      }
    }
  }
}
