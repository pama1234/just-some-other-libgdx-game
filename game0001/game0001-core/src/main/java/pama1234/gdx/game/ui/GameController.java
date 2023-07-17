package pama1234.gdx.game.ui;

import com.badlogic.gdx.Input;

import pama1234.gdx.game.app.Screen0011;
import pama1234.gdx.util.entity.Entity;
import pama1234.gdx.util.info.ScreenCamInfo;
import pama1234.gdx.util.info.TouchInfo;

public abstract class GameController extends Entity<Screen0011>{
  public float maxDist;
  public float mx,my;
  // public float mag;
  public float dx,dy;
  public ScreenCamInfo data;
  //---
  public boolean left,right,up,down;
  public GameController(Screen0011 p) {
    super(p);
    frameResized(p.width,p.height);
  }
  @Override
  public void display() {
    if(data==null) return;
    p.beginBlend();
    p.doStroke();
    p.noFill();
    p.stroke(127,127);
    p.strokeWeight(p.u/32f);
    //---
    // p.rect(data.osx-mx,data.osy-my,mx*2,my*2);
    p.line(data.osx-mx,data.osy-my*2,data.osx-mx,data.osy+my*2);
    p.line(data.osx-mx*2,data.osy-my,data.osx+mx*2,data.osy-my);
    p.line(data.osx+mx,data.osy-my*2,data.osx+mx,data.osy+my*2);
    p.line(data.osx-mx*2,data.osy+my,data.osx+mx*2,data.osy+my);
    //---
    p.circle(data.osx,data.osy,p.u);
    // p.cross(data.osx,data.osy,p.u,p.u);
    p.line(data.osx,data.osy,data.ox,data.oy);
    p.circle(data.ox,data.oy,p.u/4f);
    // p.cross(data.ox,data.oy,p.u/2f,p.u/2f);
    //---
    // float deg=UtilMath.deg(UtilMath.atan2(dx,dy));
    // p.arc(data.osx,data.osy,mag,45-deg,90);
    p.noStroke();
    p.endBlend();
    p.doFill();
  }
  @Override
  public void update() {
    if(data==null) return;
    dx=data.ox-data.osx;
    dy=data.oy-data.osy;
    // mag=UtilMath.min(UtilMath.mag(dx,dy),maxDist);
    if(dx>mx) {
      leftReleased();
      rightPressed();
    }else if(dx<-mx) {
      rightReleased();
      leftPressed();
    }else {
      leftReleased();
      rightReleased();
    }
    if(dy>my) {
      downReleased();
      upPressed();
    }else if(dy<-my) {
      upReleased();
      downPressed();
    }else {
      upReleased();
      downReleased();
    }
  }
  @Override
  public void frameResized(int w,int h) {
    maxDist=p.u;
    mx=maxDist/2f;
    my=maxDist/2f;
  }
  public void downPressed() {
    if(down) return;
    down=true;
    p.inputProcessor.keyDown(Input.Keys.SPACE);
  }
  public void upPressed() {
    if(up) return;
    up=true;
    p.inputProcessor.keyDown(Input.Keys.S);
  }
  public void leftPressed() {
    if(left) return;
    left=true;
    p.inputProcessor.keyDown(Input.Keys.A);
  }
  public void rightPressed() {
    if(right) return;
    right=true;
    p.inputProcessor.keyDown(Input.Keys.D);
  }
  public void downReleased() {
    if(!down) return;
    down=false;
    p.inputProcessor.keyUp(Input.Keys.SPACE);
  }
  public void upReleased() {
    if(!up) return;
    up=false;
    p.inputProcessor.keyUp(Input.Keys.S);
  }
  public void leftReleased() {
    if(!left);
    left=false;
    p.inputProcessor.keyUp(Input.Keys.A);
  }
  public void rightReleased() {
    if(!right) return;
    right=false;
    p.inputProcessor.keyUp(Input.Keys.D);
  }
  @Override
  public void touchStarted(TouchInfo info) {
    dataStart(info);
  }
  @Override
  public void touchEnded(TouchInfo info) {
    dataEnd(info);
    end(info);
  }
  public void dataStart(ScreenCamInfo info) {
    if(data==null&&inActiveRect(info.ox,info.oy)) {
      data=info;
      data.state=2;
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
  public void end(ScreenCamInfo info) {
    rightReleased();
    leftReleased();
    upReleased();
    downReleased();
  }
  public abstract boolean inActiveRect(float x,float y);
}