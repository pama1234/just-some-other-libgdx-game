package pama1234.gdx.util.info;

import com.badlogic.gdx.math.Vector3;

import pama1234.gdx.util.app.UtilScreenCore;

public class TouchInfo extends ScreenCamInfo{
  public boolean active;
  public int pointer,button;
  public TouchInfo(UtilScreenCore p) {
    this.p=p;
  }
  public void update(UtilScreenCore p) {
    if(!active) return;
    flipPD();
  }
  public void putRaw(int xIn,int yIn) {
    Vector3 tv=p.unproject(ox=xIn,oy=yIn);
    set(tv.x,tv.y);
  }
  public void begin(int xIn,int yIn,int pIn,int b,int time) {
    active=true;
    Vector3 tv=p.unproject(ox=osx=xIn,oy=osy=yIn);
    sx=x=tv.x;
    sy=y=tv.y;
    flipPD();
    pointer=pIn;
    button=b;
    startTime=time;
  }
  public void end() {
    active=false;
  }
}