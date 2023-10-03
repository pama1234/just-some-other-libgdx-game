package pama1234.math.transform;

import pama1234.math.vec.Vec2f;

public class Pose2D{
  public Vec2f pos,scale;
  public float rotate;
  public Pose2D(float dx,float dy) {
    pos=new Vec2f(dx,dy);
    scale=new Vec2f();
  }
  public Pose2D(
    float dx,float dy,
    float r) {
    pos=new Vec2f(dx,dy);
    rotate=r;
  }
  public Pose2D(
    float dx,float dy,
    float r,
    float sx,float sy) {
    pos=new Vec2f(dx,dy);
    rotate=r;
    scale=new Vec2f(sx,sy);
  }
  public float dx() {
    return pos.x;
  }
  public float dy() {
    return pos.y;
  }
  public float r() {
    return rotate;
  }
  public float sx() {
    return scale.x;
  }
  public float sy() {
    return scale.y;
  }
}
