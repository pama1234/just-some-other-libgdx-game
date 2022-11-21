package pama1234.math.physics;

import pama1234.math.vec.Vec3f;

public abstract class Point3D{
  public Vec3f pos;
  public float f;
  public abstract void update();
  public void set(float x,float y,float z) {
    pos.set(x,y,z);
  }
  public abstract void add(float x,float y,float z);
  public float x() {
    return pos.x;
  }
  public float y() {
    return pos.y;
  }
  public float z() {
    return pos.z;
  }
}
