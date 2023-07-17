package pama1234.gdx.util.color;

import com.badlogic.gdx.graphics.Color;

public class ColorD extends Color implements ColorI{
  @Override
  public int r() {
    return (int)(r*256);
  }
  @Override
  public int g() {
    return (int)(g*256);
  }
  @Override
  public int b() {
    return (int)(b*256);
  }
  @Override
  public int a() {
    return (int)(a*256);
  }
  @Override
  public float rf() {
    return r;
  }
  @Override
  public float gf() {
    return g;
  }
  @Override
  public float bf() {
    return b;
  }
  @Override
  public float af() {
    return a;
  }
}