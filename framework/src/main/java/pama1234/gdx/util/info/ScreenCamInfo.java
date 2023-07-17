package pama1234.gdx.util.info;

import pama1234.gdx.util.app.UtilScreenCore;

public class ScreenCamInfo{
  /**
   * 父实例 {@link pama1234.gdx.util.app.UtilScreenCore UtilScreenCore}
   */
  public UtilScreenCore p;
  /**
   * sx和sy是start-x的简写，含义是鼠标被按下或手指触碰时所处的位置
   * </p>
   */
  public float sx,sy;
  /**
   * dx和pdy是distance-x的简写，表示这一帧和上一帧的鼠标位置的数值差，可为负数
   */
  public float dx,dy;
  /**
   * px和py是previous-x的简写，表示上一帧时鼠标的位置
   */
  public float px,py;
  /**
   * x和y是当前这一帧的鼠标位置
   */
  public float x,y;
  /**
   * ox和oy是未经过相机视角变化的当前鼠标位置
   * 
   * @see pama1234.gdx.util.app.UtilScreen2D#unproject(float,float)
   */
  public int ox,oy;
  /**
   * osx是未经过相机视角变化的鼠标按下时的鼠标位置
   */
  public int osx,osy;
  /**
   * 用于记录鼠标开始时的当前帧数
   * 
   * @see pama1234.gdx.util.app.UtilScreenCore#frameCount
   */
  public int startTime;
  //---
  // public float ex,ey;
  public int state;
  public void set(float a,float b) {
    x=a;
    y=b;
  }
  public void flip() {
    sx=x;
    sy=y;
    osx=ox;
    osy=oy;
  }
  public void flipPD() {
    dx=x-px;
    dy=y-py;
    px=x;
    py=y;
  }
}