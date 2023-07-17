package pama1234.math.transform;

import pama1234.math.vec.Vec3f;

public class Pose3D{
  public Vec3f pos,rotate,scale;
  public Pose3D(float dx,float dy,float dz) {
    pos=new Vec3f(dx,dz,dy);
    rotate=new Vec3f();
    scale=new Vec3f();
  }
  public Pose3D(
    float dx,float dy,float dz,
    float rx,float ry,float rz) {
    pos=new Vec3f(dx,dz,dy);
    rotate=new Vec3f(rx,ry,rz);
  }
  public Pose3D(
    float dx,float dy,float dz,
    float rx,float ry,float rz,
    float sx,float sy,float sz) {
    pos=new Vec3f(dx,dz,dy);
    rotate=new Vec3f(rx,ry,rz);
    scale=new Vec3f(sx,sy,sz);
  }
  public float dx() {
    return pos.x;
  }
  public float dy() {
    return pos.y;
  }
  public float dz() {
    return pos.z;
  }
  public float rx() {
    return rotate.x;
  }
  public float ry() {
    return rotate.y;
  }
  public float rz() {
    return rotate.z;
  }
  public float sx() {
    return scale.x;
  }
  public float sy() {
    return scale.y;
  }
  public float sz() {
    return scale.z;
  }
}
