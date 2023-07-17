package pama1234.math.geometry;

import pama1234.util.function.GetFloat;

public interface RectI{
  public float x();
  public float y();
  //------------------
  public float w();
  public float h();
  //------------------
  public float x1();
  public float y1();
  public float x2();
  public float y2();
  //------------------
  public void w(GetFloat in);
  public default float cx() {
    return x()+w()/2f;
  }
  public default float cy() {
    return y()+h()/2f;
  }
}
