package pama1234.gdx.util.wrapper;

import java.util.ListIterator;

import com.badlogic.gdx.Input.Buttons;

import pama1234.gdx.util.app.UtilScreen;
import pama1234.gdx.util.entity.PointEntity;
import pama1234.gdx.util.info.MouseInfo;
import pama1234.math.physics.Point;

/**
 * 一种用于存储带位置实体的链表型容器
 */
public class PointCenter<T extends UtilScreen,P extends Point,E extends PointEntity<T,P>>extends EntityCenter<T,E>{
  public float minDist,minDisplayDist;
  public E select;
  public boolean displayCircle,enableDrag;//TODO
  public PointCenter(T p) {
    this(p,4);
  }
  public PointCenter(T p,float u) {
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
    if(!enableDrag) return;
    if(p.mouse.button==Buttons.LEFT) find();
    if(select==null) super.mousePressed(info);
  }
  public void find() {
    float tmd=minDist;
    select=null;
    ListIterator<E> it=list.listIterator(list.size());
    while(it.hasPrevious()) {
      E i=it.previous();
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

    if(displayCircle) {
      p.beginBlend();
      p.stroke(255,255,255,191);
      p.noFill();
      for(E i:list) {
        p.circle(i.point.pos.x,i.point.pos.y,minDist*2);
        if(i.point.pos.dist(p.mouse.x,p.mouse.y)<minDisplayDist) {
          p.cross(i.point.pos.x,i.point.pos.y,minDist*2,minDist*2);
          String ts=i.getClass().getSimpleName()+"\n"+i.point.pos.toString();
          //      p.text(ts,i.point.pos.x-p.textWidth(ts)/2,i.point.pos.y-p.g.textSize);
          p.text(ts,i.point.pos.x,i.point.pos.y);
        }
      }
      p.endBlend();
      p.doFill();
      p.noStroke();
    }
  }
}
