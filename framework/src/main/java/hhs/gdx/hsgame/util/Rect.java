package hhs.gdx.hsgame.util;

import pama1234.math.geometry.RectI;
import pama1234.util.function.GetFloat;

public interface Rect extends RectI{
  public abstract float getX();
  public abstract float getY();
  public abstract float getWidth();
  public abstract float getHeight();
  //---
  @Override
  default float w() {
    return getWidth();
  }
  @Override
  default float h() {
    return getHeight();
  }
  @Override
  default float x() {
    return getX();
  }
  @Override
  default float y() {
    return getY();
  }
  @Override
  default float x1() {
    return getX();
  }
  @Override
  default float y1() {
    return getY();
  }
  @Override
  default float x2() {
    return getWidth();
  }
  @Override
  default float y2() {
    return getHeight();
  }
  @Override
  default void w(GetFloat in) {
    throw new RuntimeException("hhs.gdx.hsgame.util.Rect.w() nop");
  }
}
