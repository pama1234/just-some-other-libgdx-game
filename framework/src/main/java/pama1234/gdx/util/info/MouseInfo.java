package pama1234.gdx.util.info;

import com.badlogic.gdx.math.Vector3;

import pama1234.gdx.util.app.UtilScreenCore;
import pama1234.math.Tools;

public class MouseInfo extends ScreenCamInfo{
  /**
   * 表示鼠标按键是否被按下
   */
  public boolean pressed;
  /**
   * 表示鼠标被按下的是哪个键
   */
  public boolean left,right,center;
  /**
   * （未实现）用于记录鼠标开始时的当前帧数
   * 
   * @see pama1234.gdx.util.app.UtilScreenCore#frameCount
   */
  public int startTime;
  /**
   * 被按下的键的int表示
   * 
   * @see com.badlogic.gdx.Input.Buttons
   */
  public int button=-1;
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
    osx=ox;
    osy=oy;
  }
  public boolean inbox(float a,float b,float w,float h) {
    return Tools.inBox(x,y,a,b,w,h);
  }
}