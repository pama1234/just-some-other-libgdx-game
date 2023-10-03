package pama1234.gdx.util.element;

import com.badlogic.gdx.graphics.Color;

import pama1234.Tools;
import pama1234.gdx.util.UITools;
import pama1234.gdx.util.app.UtilScreen;
import pama1234.gdx.util.info.ScreenCamInfo;
import pama1234.math.geometry.RectD;
import pama1234.math.geometry.RectF;
import pama1234.math.geometry.RectI;
import pama1234.math.vec.Vec2f;
import pama1234.util.Annotations.DesktopOnly;
import pama1234.util.function.GetFloat;

public class MoveableCross{
  public Vec2f pos,offset;
  public RectI size;
  public Color strokeColor;
  @DesktopOnly
  public boolean mouseInCross;
  public ScreenCamInfo info;
  public MoveableCross(RectI size) {
    pos=new Vec2f();
    offset=new Vec2f();
    this.size=size;
  }
  public MoveableCross(float a,float b) {
    this(new RectD(-a/2,-b/2,a,b));
  }
  public MoveableCross(GetFloat a,GetFloat b) {
    this(new RectF(()->-a.get()/2,()->-b.get()/2,()->a.get(),()->b.get()));
  }
  public MoveableCross(Object object,Object object2) {}
  public float x() {
    return pos.x+offset.x;
  }
  public float y() {
    return pos.y+offset.y;
  }
  public void testMouseInCross(ScreenCamInfo info) {
    mouseInCross=isInCross(info);
  }
  public boolean isInCross(ScreenCamInfo info) {
    return Tools.inBox(info.x,info.y,x()+size.x(),y()+size.y(),size.w(),size.h());
  }
  public void display(UtilScreen p) {
    p.stroke(strokeColor,mouseInCross?255:127);
    UITools.cross(p,x(),y(),size.w(),size.h());
  }
}
