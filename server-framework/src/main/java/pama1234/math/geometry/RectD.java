package pama1234.math.geometry;

import pama1234.util.function.GetFloat;

/**
 * 需要修改和添加更多规范
 */
public class RectD implements RectI{
  public float x,y,w,h;
  public RectD(float x,float y,float w,float h) {
    this.x=x;
    this.y=y;
    this.w=w;
    this.h=h;
  }
  @Override
  public float x() {
    return x;
  }
  @Override
  public float y() {
    return y;
  }
  @Override
  public float w() {
    return w;
  }
  @Override
  public float h() {
    return h;
  }
  //------------------
  //TODO
  @Override
  public float x1() {
    return x;
  }
  @Override
  public float y1() {
    return y;
  }
  @Override
  public float x2() {
    return w;
  }
  @Override
  public float y2() {
    return h;
  }
  //------------------
  @Override
  public void w(GetFloat in) {
    w=in.get();
    System.err.println("unsupported action RectD.w(GetFloat)");
  }
}
