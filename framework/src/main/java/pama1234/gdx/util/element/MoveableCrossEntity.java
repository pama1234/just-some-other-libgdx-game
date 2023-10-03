package pama1234.gdx.util.element;

import com.badlogic.gdx.Input.Buttons;

import pama1234.gdx.util.app.UtilScreen;
import pama1234.gdx.util.entity.Entity;
import pama1234.gdx.util.info.TouchInfo;
import pama1234.math.physics.PathPoint;

public class MoveableCrossEntity<T extends UtilScreen>extends Entity<T>{
  public PathPoint point;
  public MoveableCross cross;
  public MoveableCrossEntity(T p,MoveableCross cross,PathPoint point) {
    super(p);
    this.cross=cross;
    this.point=point;
  }
  @Override
  public void display() {
    p.beginBlend();
    // p.println(p.pus+" "+p.cam.scale());
    // p.strokeWeight(p.u/16f);
    // p.strokeWeight(p.pus*p.cam.scale());
    p.strokeWeight(p.pus*p.cam.scale());
    // p.strokeWeight(p.u/16f*p.cam.scale());
    cross.display(p);
  }
  @Override
  public void update() {
    cross.testMouseInCross(p.mouse);
    cross.pos.set(point.x(),point.y());
    if(cross.info!=null) point.des.add(cross.info.dx,cross.info.dy);
  }
  @Override
  public void touchStarted(TouchInfo info) {
    if(info.button==Buttons.LEFT&&info.state==0) {
      if(cross.isInCross(info)) {
        cross.info=info;
        info.state=1;
      }
    }
  }
  @Override
  public void touchEnded(TouchInfo info) {
    if(cross.info==info) {
      cross.info=null;
      info.state=0;
    }
  }
  public void offset(int x,int y) {
    cross.offset.set(x,y);
  }
}
