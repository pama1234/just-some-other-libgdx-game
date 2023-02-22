package pama1234.gdx.util.info;

import pama1234.gdx.util.app.UtilScreenCore;

public class ScreenCamInfo{
  public UtilScreenCore p;
  /**
   * sx是start-x的简写，含义是鼠标被按下或手指触碰时所处的位置
   * </p>
   */
  public float sx,sy;
  /**
   * dx是distance-x的简写，表示这一帧和上一帧的鼠标位置的数值差，可为负数
   */
  public float dx,dy;
  /**
   * px是previous-x的简写，表示上一帧时鼠标的位置 x和y是当前这一帧的鼠标位置
   */
  public float px,py,x,y;
}
