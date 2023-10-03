package pama1234.gdx.util.info;

import pama1234.Tools;
import pama1234.gdx.util.app.UtilScreenCore;

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
  public boolean inbox(float a,float b,float w,float h) {
    return Tools.inBox(x,y,a,b,w,h);
  }
  // @Override
  // public void updateWithCam() {
  //   if(p.grabCursor) {
  //     Vector3 tv=p.screenToWorld(p.width/2f,p.height/2f);
  //     set(tv.x,tv.y);
  //   }else super.updateWithCam();
  // }
  // @Override
  // public void putRaw(int xIn,int yIn) {
  //   if(p.grabCursor) {
  //     ox=xIn;
  //     oy=yIn;
  //     Vector3 tv=p.screenToWorld(p.width/2f,p.height/2f);
  //     set(tv.x,tv.y);
  //   }else super.putRaw(xIn,yIn);
  // }
}