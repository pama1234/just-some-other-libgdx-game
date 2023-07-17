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
   * 被按下的键的int表示
   * 
   * @see com.badlogic.gdx.Input.Buttons
   */
  public int button=-1;
  public MouseInfo(UtilScreenCore p) {
    this.p=p;
  }
  public void update(UtilScreenCore p) {
    flipPD();
  }
  public void putRaw(int xIn,int yIn) {
    Vector3 tv=p.unproject(ox=xIn,oy=yIn);
    set(tv.x,tv.y);
  }
  public boolean inbox(float a,float b,float w,float h) {
    return Tools.inBox(x,y,a,b,w,h);
  }
}