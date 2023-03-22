package pama1234.gdx.game.ui;

import pama1234.gdx.game.app.Screen0011;
import pama1234.gdx.util.entity.Entity;
import pama1234.gdx.util.info.ScreenCamInfo;
import pama1234.gdx.util.info.TouchInfo;

public abstract class GameController extends Entity<Screen0011>{
  public float dx,dy;
  public ScreenCamInfo data;
  public GameController(Screen0011 p) {
    super(p);
  }
  @Override
  public void display() {
    if(data==null) return;
    p.beginBlend();
    p.doStroke();
    p.stroke(127,127);
    p.strokeWeight(p.u/32f);
    p.cross(data.osx,data.osy,p.u,p.u);
    p.line(data.osx,data.osy,data.ox,data.oy);
    p.cross(data.ox,data.oy,p.u/2f,p.u/2f);
    p.noStroke();
    p.endBlend();
  }
  @Override
  public void update() {
    if(data==null) return;
    dx=data.ox-data.osx;
    dy=data.oy-data.osy;
  }
  // @Override
  // public void mouseReleased(MouseInfo info) {
  //   end(info);
  // }
  // @Override
  // public void mousePressed(MouseInfo info) {
  //   start(info);
  // }
  @Override
  public void touchStarted(TouchInfo info) {
    start(info);
  }
  @Override
  public void touchEnded(TouchInfo info) {
    end(info);
  }
  public void start(ScreenCamInfo info) {
    if(data==null&&inActiveRect(info.ox,info.oy)) {
      data=info;
      data.state=2;
    }
  }
  public void end(ScreenCamInfo info) {
    if(data==info) {
      data.state=0;
      data=null;
    }
    dx=0;
    dy=0;
  }
  public abstract boolean inActiveRect(float x,float y);
}