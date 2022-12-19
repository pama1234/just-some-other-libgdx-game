package pama1234.math.physics;

import pama1234.math.vec.Vec2f;

public abstract class Point{
  public Vec2f pos;
  public float f,step=1;
  public abstract void update();
  public void set(float x,float y) {
    pos.set(x,y);
  }
  public abstract void add(float x,float y);
  public float x() {
    return pos.x;
  }
  public float y() {
    return pos.y;
  }
}
