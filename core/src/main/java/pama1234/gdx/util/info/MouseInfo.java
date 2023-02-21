package pama1234.gdx.util.info;

import com.badlogic.gdx.math.Vector3;

import pama1234.gdx.util.app.UtilScreenCore;
import pama1234.math.Tools;

public class MouseInfo{
  public UtilScreenCore p;
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
  /**
   * sx是start-x的简写，含义是鼠标被按下时所处的位置
   * </p>
   */
  public float sx,sy;
  /**
   * px是previous-x的简写，表示上一帧时鼠标的位置 x和y是当前这一帧的鼠标位置
   */
  public float px,py,x,y;
  /**
   * dx是distance-x的简写，表示这一帧和上一帧的鼠标位置的数值差，可为负数
   */
  public float dx,dy;
  /**
   * ox是未经过相机视角变化的当前鼠标位置
   * 
   * @see pama1234.gdx.util.app.UtilScreen2D#unproject(float,float)
   */
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