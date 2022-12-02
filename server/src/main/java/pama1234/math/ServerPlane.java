package pama1234.math;

import pama1234.math.vec.Vec3f;

public class ServerPlane{
  public enum PlaneSide{
    OnPlane,Back,Front
  }
  public final Vec3f normal=new Vec3f();
  public float d=0;
  public ServerPlane() {}
  public ServerPlane(Vec3f normal,float d) {
    this.normal.set(normal);
    normal.nor();
    this.d=d;
  }
  public ServerPlane(Vec3f normal,Vec3f point) {
    this.normal.set(normal);
    normal.nor();
    this.d=-this.normal.dot(point);
  }
  public ServerPlane(Vec3f point1,Vec3f point2,Vec3f point3) {
    set(point1,point2,point3);
  }
  public void set(Vec3f point1,Vec3f point2,Vec3f point3) {
    normal.set(point1);
    normal.sub(point2);
    normal.crs(point2.x-point3.x,point2.y-point3.y,point2.z-point3.z);
    normal.nor();
    d=-point1.dot(normal);
  }
  public void set(float nx,float ny,float nz,float d) {
    normal.set(nx,ny,nz);
    this.d=d;
  }
  public float distance(Vec3f point) {
    return normal.dot(point)+d;
  }
  public PlaneSide testPoint(Vec3f point) {
    float dist=normal.dot(point)+d;
    if(dist==0) return PlaneSide.OnPlane;
    else if(dist<0) return PlaneSide.Back;
    else return PlaneSide.Front;
  }
  public PlaneSide testPoint(float x,float y,float z) {
    float dist=normal.dot(x,y,z)+d;
    if(dist==0) return PlaneSide.OnPlane;
    else if(dist<0) return PlaneSide.Back;
    else return PlaneSide.Front;
  }
  public boolean isFrontFacing(Vec3f direction) {
    float dot=normal.dot(direction);
    return dot<=0;
  }
  public Vec3f getNormal() {
    return normal;
  }
  public float getD() {
    return d;
  }
  public void set(Vec3f point,Vec3f normal) {
    this.normal.set(normal);
    d=-point.dot(normal);
  }
  public void set(float pointX,float pointY,float pointZ,float norX,float norY,float norZ) {
    this.normal.set(norX,norY,norZ);
    d=-(pointX*norX+pointY*norY+pointZ*norZ);
  }
  public void set(ServerPlane plane) {
    this.normal.set(plane.normal);
    this.d=plane.d;
  }
  public String toString() {
    return normal.toString()+", "+d;
  }
}
