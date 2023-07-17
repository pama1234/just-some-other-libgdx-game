package pama1234.math.geometry;

import pama1234.util.function.GetFloat;

public class RectF implements RectI{
  public GetFloat x,y,w,h;
  public RectF(GetFloat x,GetFloat y,GetFloat w,GetFloat h) {
    this.x=x;
    this.y=y;
    this.w=w;
    this.h=h;
  }
  @Override
  public float x() {
    return x.get();
  }
  @Override
  public float y() {
    return y.get();
  }
  @Override
  public float w() {
    return w.get();
  }
  @Override
  public float h() {
    return h.get();
  }
  //------------------
  //TODO
  @Override
  public float x1() {
    return x.get();
  }
  @Override
  public float y1() {
    return y.get();
  }
  @Override
  public float x2() {
    return w.get();
  }
  @Override
  public float y2() {
    return h.get();
  }
  //------------------
  @Override
  public void w(GetFloat in) {
    w=in;
  }
}
