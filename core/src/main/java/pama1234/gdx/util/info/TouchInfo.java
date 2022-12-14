package pama1234.gdx.util.info;

import com.badlogic.gdx.math.Vector3;

import pama1234.gdx.util.app.UtilScreenCore;

public class TouchInfo{
  public UtilScreenCore p;
  public boolean active;
  public int startTime;
  public int pointer,button;
  public float sx,sy;
  public float dx,dy;
  public float px,py,x,y;
  public int ox,oy,osx,osy;
  public TouchInfo(UtilScreenCore p) {
    this.p=p;
  }
  public void update(UtilScreenCore p) {
    if(!active) return;
    px=x;
    py=y;
    putRaw(ox,oy);
  }
  public void putRaw(int x,int y) {
    Vector3 tv=p.unproject(ox=x,oy=y);
    put(tv.x,tv.y);
    dx=x-px;
    dy=y-py;
  }
  public void put(float a,float b) {
    x=a;
    y=b;
  }
  public void begin(int xIn,int yIn,int p,int b) {
    active=true;
    Vector3 tv=this.p.unproject(ox=osx=xIn,oy=osy=yIn);
    sx=x=tv.x;
    sy=y=tv.y;
    pointer=p;
    button=b;
  }
  // public void put(int xIn,int yIn,int p,int b) {
  //   Vector3 tv=this.p.unproject(ox=xIn,oy=yIn);
  //   sx=x=tv.x;
  //   sy=y=tv.y;
  //   pointer=p;
  //   button=b;
  // }
  public void end() {
    active=false;
  }
}