package pama1234.gdx.util.info;

import com.badlogic.gdx.math.Vector3;

import pama1234.gdx.util.app.UtilScreenCore;

public class TouchInfo extends ScreenCamInfo{
  /**
   * 表示此触碰信息是否参与按钮等信息，不由{@link UtilScreenCore}进行赋值
   */
  public boolean active;
  /**
   * （未实现）用于记录鼠标开始时的当前帧数
   * 
   * @see pama1234.gdx.util.app.UtilScreenCore#frameCount
   */
  public int startTime;
  public int pointer,button;
  //---------------------------
  public int state;
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
  public void end() {
    active=false;
  }
}