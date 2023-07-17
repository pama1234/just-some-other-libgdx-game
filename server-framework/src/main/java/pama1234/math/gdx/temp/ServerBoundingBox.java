package pama1234.math.gdx.temp;

import java.util.List;

import pama1234.math.mat.Mat4f;
import pama1234.math.vec.Vec3f;

public class ServerBoundingBox{
  private final static Vec3f tmpVector=new Vec3f();
  public final Vec3f min=new Vec3f();
  public final Vec3f max=new Vec3f();
  private final Vec3f cnt=new Vec3f();
  private final Vec3f dim=new Vec3f();
  public Vec3f getCenter(Vec3f out) {
    out.set(cnt);
    return out;
  }
  public float getCenterX() {
    return cnt.x;
  }
  public float getCenterY() {
    return cnt.y;
  }
  public float getCenterZ() {
    return cnt.z;
  }
  public Vec3f getCorner000(final Vec3f out) {
    out.set(min.x,min.y,min.z);
    return out;
  }
  public Vec3f getCorner001(final Vec3f out) {
    out.set(min.x,min.y,max.z);
    return out;
  }
  public Vec3f getCorner010(final Vec3f out) {
    out.set(min.x,max.y,min.z);
    return out;
  }
  public Vec3f getCorner011(final Vec3f out) {
    out.set(min.x,max.y,max.z);
    return out;
  }
  public Vec3f getCorner100(final Vec3f out) {
    out.set(max.x,min.y,min.z);
    return out;
  }
  public Vec3f getCorner101(final Vec3f out) {
    out.set(max.x,min.y,max.z);
    return out;
  }
  public Vec3f getCorner110(final Vec3f out) {
    out.set(max.x,max.y,min.z);
    return out;
  }
  public Vec3f getCorner111(final Vec3f out) {
    out.set(max.x,max.y,max.z);
    return out;
  }
  public Vec3f getDimensions(final Vec3f out) {
    out.set(dim);
    return out;
  }
  public float getWidth() {
    return dim.x;
  }
  public float getHeight() {
    return dim.y;
  }
  public float getDepth() {
    return dim.z;
  }
  public Vec3f getMin(final Vec3f out) {
    out.set(min);
    return out;
  }
  public Vec3f getMax(final Vec3f out) {
    out.set(max);
    return out;
  }
  public ServerBoundingBox() {
    clr();
  }
  public ServerBoundingBox(ServerBoundingBox bounds) {
    this.set(bounds);
  }
  public ServerBoundingBox(Vec3f minimum,Vec3f maximum) {
    this.set(minimum,maximum);
  }
  public ServerBoundingBox set(ServerBoundingBox bounds) {
    return this.set(bounds.min,bounds.max);
  }
  public ServerBoundingBox set(Vec3f minimum,Vec3f maximum) {
    min.set(minimum.x<maximum.x?minimum.x:maximum.x,minimum.y<maximum.y?minimum.y:maximum.y,
      minimum.z<maximum.z?minimum.z:maximum.z);
    max.set(minimum.x>maximum.x?minimum.x:maximum.x,minimum.y>maximum.y?minimum.y:maximum.y,
      minimum.z>maximum.z?minimum.z:maximum.z);
    update();
    return this;
  }
  public void update() {
    cnt.set(min);
    cnt.add(max);
    cnt.scl(0.5f);
    dim.set(max);
    dim.sub(min);
  }
  public ServerBoundingBox set(Vec3f[] points) {
    this.inf();
    for(Vec3f l_point:points) this.ext(l_point);
    return this;
  }
  public ServerBoundingBox set(List<Vec3f> points) {
    this.inf();
    for(Vec3f l_point:points) this.ext(l_point);
    return this;
  }
  public ServerBoundingBox inf() {
    min.set(Float.POSITIVE_INFINITY,Float.POSITIVE_INFINITY,Float.POSITIVE_INFINITY);
    max.set(Float.NEGATIVE_INFINITY,Float.NEGATIVE_INFINITY,Float.NEGATIVE_INFINITY);
    cnt.set(0,0,0);
    dim.set(0,0,0);
    return this;
  }
  public ServerBoundingBox ext(Vec3f point) {
    min.set(min(min.x,point.x),min(min.y,point.y),min(min.z,point.z));
    max.set(Math.max(max.x,point.x),Math.max(max.y,point.y),Math.max(max.z,point.z));
    return this.set(min,max);
  }
  public ServerBoundingBox clr() {
    min.set(0,0,0);
    max.set(0,0,0);
    return this.set(min,max);
  }
  public boolean isValid() {
    return min.x<=max.x&&min.y<=max.y&&min.z<=max.z;
  }
  public ServerBoundingBox ext(ServerBoundingBox a_bounds) {
    min.set(min(min.x,a_bounds.min.x),min(min.y,a_bounds.min.y),min(min.z,a_bounds.min.z));
    max.set(max(max.x,a_bounds.max.x),max(max.y,a_bounds.max.y),max(max.z,a_bounds.max.z));
    return this.set(min,max);
  }
  public ServerBoundingBox ext(Vec3f center,float radius) {
    min.set(min(min.x,center.x-radius),min(min.y,center.y-radius),min(min.z,center.z-radius));
    max.set(max(max.x,center.x+radius),max(max.y,center.y+radius),max(max.z,center.z+radius));
    return this.set(min,max);
  }
  public ServerBoundingBox ext(ServerBoundingBox bounds,Mat4f transform) {
    tmpVector.set(bounds.min.x,bounds.min.y,bounds.min.z);
    tmpVector.mul(transform);
    ext(tmpVector);
    tmpVector.set(bounds.min.x,bounds.min.y,bounds.max.z);
    tmpVector.mul(transform);
    ext(tmpVector);
    tmpVector.set(bounds.min.x,bounds.max.y,bounds.min.z);
    tmpVector.mul(transform);
    ext(tmpVector);
    tmpVector.set(bounds.min.x,bounds.max.y,bounds.max.z);
    tmpVector.mul(transform);
    ext(tmpVector);
    tmpVector.set(bounds.max.x,bounds.min.y,bounds.min.z);
    tmpVector.mul(transform);
    ext(tmpVector);
    tmpVector.set(bounds.max.x,bounds.min.y,bounds.max.z);
    tmpVector.mul(transform);
    ext(tmpVector);
    tmpVector.set(bounds.max.x,bounds.max.y,bounds.min.z);
    tmpVector.mul(transform);
    ext(tmpVector);
    tmpVector.set(bounds.max.x,bounds.max.y,bounds.max.z);
    tmpVector.mul(transform);
    ext(tmpVector);
    return this;
  }
  public ServerBoundingBox mul(Mat4f transform) {
    final float x0=min.x,y0=min.y,z0=min.z,x1=max.x,y1=max.y,z1=max.z;
    inf();
    tmpVector.set(x0,y0,z0);
    tmpVector.mul(transform);
    ext(tmpVector);
    tmpVector.set(x0,y0,z1);
    tmpVector.mul(transform);
    ext(tmpVector);
    tmpVector.set(x0,y1,z0);
    tmpVector.mul(transform);
    ext(tmpVector);
    tmpVector.set(x0,y1,z1);
    tmpVector.mul(transform);
    ext(tmpVector);
    tmpVector.set(x1,y0,z0);
    tmpVector.mul(transform);
    ext(tmpVector);
    tmpVector.set(x1,y0,z1);
    tmpVector.mul(transform);
    ext(tmpVector);
    tmpVector.set(x1,y1,z0);
    tmpVector.mul(transform);
    ext(tmpVector);
    tmpVector.set(x1,y1,z1);
    tmpVector.mul(transform);
    ext(tmpVector);
    return this;
  }
  public boolean contains(ServerBoundingBox b) {
    return !isValid()||(min.x<=b.min.x&&min.y<=b.min.y&&min.z<=b.min.z&&max.x>=b.max.x&&max.y>=b.max.y
      &&max.z>=b.max.z);
  }
  public boolean intersects(ServerBoundingBox b) {
    if(!isValid()) return false;
    float lx=Math.abs(this.cnt.x-b.cnt.x);
    float sumx=(this.dim.x/2.0f)+(b.dim.x/2.0f);
    float ly=Math.abs(this.cnt.y-b.cnt.y);
    float sumy=(this.dim.y/2.0f)+(b.dim.y/2.0f);
    float lz=Math.abs(this.cnt.z-b.cnt.z);
    float sumz=(this.dim.z/2.0f)+(b.dim.z/2.0f);
    return (lx<=sumx&&ly<=sumy&&lz<=sumz);
  }
  public boolean contains(Vec3f v) {
    return min.x<=v.x&&max.x>=v.x&&min.y<=v.y&&max.y>=v.y&&min.z<=v.z&&max.z>=v.z;
  }
  @Override
  public String toString() {
    return "["+min+"|"+max+"]";
  }
  public ServerBoundingBox ext(float x,float y,float z) {
    min.set(min(min.x,x),min(min.y,y),min(min.z,z));
    max.set(max(max.x,x),max(max.y,y),max(max.z,z));
    return this.set(min,max);
  }
  static final float min(final float a,final float b) {
    return a>b?b:a;
  }
  static final float max(final float a,final float b) {
    return a>b?a:b;
  }
}
