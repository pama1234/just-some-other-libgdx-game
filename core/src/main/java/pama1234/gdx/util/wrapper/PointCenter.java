package pama1234.gdx.util.wrapper;

import java.util.ListIterator;

import com.badlogic.gdx.Input.Buttons;

import pama1234.gdx.util.app.UtilScreen2D;
import pama1234.gdx.util.entity.PointEntity;
import pama1234.gdx.util.info.MouseInfo;

public class PointCenter<T extends PointEntity<?>>extends EntityCenter<T>{
  public float minDist,minDisplayDist;
  public T select;
  public PointCenter(UtilScreen2D p) {
    this(p,4);
  }
  public PointCenter(UtilScreen2D p,float u) {
    super(p);
    this.minDist=u;
    this.minDisplayDist=u;
  }
  @Override
  public void update() {
    super.update();
    if(dragFlag()) select.point.set(p.mouse.x,p.mouse.y);
  }
  public boolean dragFlag() {
    return p.mouse.pressed&&p.mouse.button==Buttons.LEFT&&select!=null;
  }
  @Override
  public void mousePressed(MouseInfo info) {
    if(p.mouse.button==Buttons.LEFT) find();
    if(select==null) super.mousePressed(info);
  }
  public void find() {
    float tmd=minDist;
    select=null;
    ListIterator<T> it=list.listIterator(list.size());
    while(it.hasPrevious()) {
      T i=it.previous();
      float td=i.point.pos.dist(p.mouse.x,p.mouse.y);
      if(td<tmd) {
        tmd=td;
        select=i;
      }
    }
  }
  @Override
  public void display() {
    super.display();
    //    UITools.cross(p.g,p.cam.mouseX,p.cam.mouseY,minDist/2,minDist/2);
    //  System.out.println(i.point.pos.dist(p.cam.mouseX,p.cam.mouseY));
    p.stroke(255,255,255,191);
    p.noFill();
    for(T i:list) {
      p.circle(i.point.pos.x,i.point.pos.y,minDist*2);
      if(i.point.pos.dist(p.mouse.x,p.mouse.y)<minDisplayDist) {
        p.cross(i.point.pos.x,i.point.pos.y,minDist*2,minDist*2);
        String ts=i.getName()+"\n"+i.point.pos.toString();
        //      p.text(ts,i.point.pos.x-p.textWidth(ts)/2,i.point.pos.y-p.g.textSize);
        p.text(ts,i.point.pos.x,i.point.pos.y);
      }
    }
  }
}
