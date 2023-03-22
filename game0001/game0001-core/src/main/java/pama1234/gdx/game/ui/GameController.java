package pama1234.gdx.game.ui;

import com.badlogic.gdx.Input;

import pama1234.gdx.game.app.Screen0011;
import pama1234.gdx.util.entity.Entity;
import pama1234.gdx.util.info.ScreenCamInfo;
import pama1234.gdx.util.info.TouchInfo;
import pama1234.math.UtilMath;

public abstract class GameController extends Entity<Screen0011>{
  public float maxDist;
  public float mag;
  public float dx,dy;
  public ScreenCamInfo data;
  //---
  public boolean left,right,up,down;
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
    float deg=UtilMath.deg(UtilMath.atan2(dx,dy));
    p.arc(data.osx,data.osy,mag,45-deg,90);
    p.noStroke();
    p.endBlend();
  }
  @Override
  public void update() {
    if(data==null) return;
    dx=data.ox-data.osx;
    dy=data.oy-data.osy;
    mag=UtilMath.min(UtilMath.mag(dx,dy),maxDist);
    if(dx>maxDist/2f) {
      if(!right) {
        right=true;
        p.inputProcessor.keyDown(Input.Keys.D);
      }
    }else if(dx<-maxDist/2f) {
      if(!left) {
        left=true;
        p.inputProcessor.keyDown(Input.Keys.A);
      }
    }else {
      if(right) {
        right=false;
        p.inputProcessor.keyUp(Input.Keys.D);
      }else if(left) {
        left=false;
        p.inputProcessor.keyUp(Input.Keys.A);
      }
    }
    if(dy>maxDist/2f) {
      if(!up) {
        up=true;
        p.inputProcessor.keyDown(Input.Keys.S);
      }
    }else if(dy<-maxDist/2f) {
      if(!down) {
        down=true;
        p.inputProcessor.keyDown(Input.Keys.SPACE);
      }
    }else {
      if(up) {
        up=false;
        p.inputProcessor.keyUp(Input.Keys.S);
      }else if(down) {
        down=false;
        p.inputProcessor.keyUp(Input.Keys.SPACE);
      }
    }
  }
  @Override
  public void frameResized(int w,int h) {
    maxDist=p.u;
  }
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
    dataEnd(info);
    if(right) {
      right=false;
      p.inputProcessor.keyUp(Input.Keys.D);
    }else if(left) {
      left=false;
      p.inputProcessor.keyUp(Input.Keys.A);
    }
    if(up) {
      up=false;
      p.inputProcessor.keyUp(Input.Keys.S);
    }else if(down) {
      down=false;
      p.inputProcessor.keyUp(Input.Keys.SPACE);
    }
  }
  public void dataEnd(ScreenCamInfo info) {
    if(data==info) {
      data.state=0;
      data=null;
    }
    dx=0;
    dy=0;
  }
  public abstract boolean inActiveRect(float x,float y);
}