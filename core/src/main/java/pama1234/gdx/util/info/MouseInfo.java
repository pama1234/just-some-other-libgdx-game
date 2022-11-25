package pama1234.gdx.util.info;

import com.badlogic.gdx.math.Vector3;

import pama1234.gdx.util.app.UtilScreenCore;
import pama1234.math.Tools;

public class MouseInfo{
  public UtilScreenCore p;
  public boolean pressed,left,right,center;
  public int startTime,button=-1;
  public float sx,sy;
  public float px,py,x,y;
  public float dx,dy;
  public int ox,oy;
  public MouseInfo(UtilScreenCore p) {
    this.p=p;
  }
  public void update(UtilScreenCore p) {
    px=x;
    py=y;
    putRaw(ox,oy);
  }
  public void putRaw(int x,int y) {
    Vector3 tv=p.unproject(ox=x,oy=y);
    put(tv.x,tv.y);
    // System.out.println(dx+" "+x+" "+px);
    // System.out.println(dy+" "+y+" "+py);
    dx=x-px;
    dy=y-py;
  }
  public void put(float a,float b) {
    x=a;
    y=b;
  }
  public void flip() {
    sx=x;
    sy=y;
  }
  public boolean inbox(float a,float b,float w,float h) {
    return Tools.inBox(x,y,a,b,w,h);
  }
}