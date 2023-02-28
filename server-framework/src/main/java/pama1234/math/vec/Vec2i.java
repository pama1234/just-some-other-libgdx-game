package pama1234.math.vec;

import java.nio.ByteBuffer;

import pama1234.data.nio.ByteBufferData;

/**
 * 基于vecmath的矢量数据类，只包含两个整数
 * 
 * @see {@link javax.vecmath.Tuple2i#x x成员变量的位置}
 * @see {@link javax.vecmath.Tuple2i#y y成员变量的位置}
 */
public class Vec2i implements ByteBufferData{
  public int x;
  public int y;
  public Vec2i(int x,int y) {
    this.x=x;
    this.y=y;
  }
  public Vec2i() {}
  public final int dot(Vec2i v1) {
    return (x*v1.x+y*v1.y);
  }
  public final int length() {
    return (int)Math.sqrt(x*x+y*y);
  }
  public final int lengthSquared() {
    return (x*x+y*y);
  }
  public final float angle(Vec2i v1) {
    double vDot=dot(v1)/(length()*v1.length());
    if(vDot<-1.0) vDot=-1.0;
    if(vDot>1.0) vDot=1.0;
    return ((float)(Math.acos(vDot)));
  }
  public final void set(int x,int y) {
    this.x=x;
    this.y=y;
  }
  public final void set(int[] t) {
    x=t[0];
    y=t[1];
  }
  public final void set(Vec2i t1) {
    x=t1.x;
    y=t1.y;
  }
  public final void get(int[] t) {
    t[0]=x;
    t[1]=y;
  }
  public final void add(Vec2i t1,Vec2i t2) {
    x=t1.x+t2.x;
    y=t1.y+t2.y;
  }
  public final void add(Vec2i t1) {
    x+=t1.x;
    y+=t1.y;
  }
  public final void sub(Vec2i t1,Vec2i t2) {
    x=t1.x-t2.x;
    y=t1.y-t2.y;
  }
  public final void sub(Vec2i t1) {
    x-=t1.x;
    y-=t1.y;
  }
  public final void negate(Vec2i t1) {
    x=-t1.x;
    y=-t1.y;
  }
  public final void negate() {
    x=-x;
    y=-y;
  }
  public final void scale(int s,Vec2i t1) {
    x=s*t1.x;
    y=s*t1.y;
  }
  public final void scale(int s) {
    x*=s;
    y*=s;
  }
  public final void scaleAdd(int s,Vec2i t1,Vec2i t2) {
    x=s*t1.x+t2.x;
    y=s*t1.y+t2.y;
  }
  public final void scaleAdd(int s,Vec2i t1) {
    x=s*x+t1.x;
    y=s*y+t1.y;
  }
  @Override
  public int hashCode() {
    final int prime=31;
    int result=1;
    result=prime*result+x;
    result=prime*result+y;
    return result;
  }
  @Override
  public boolean equals(Object obj) {
    if(this==obj) return true;
    if(obj==null) return false;
    if(getClass()!=obj.getClass()) return false;
    Vec2i other=(Vec2i)obj;
    if(x!=other.x) return false;
    if(y!=other.y) return false;
    return true;
  }
  @Override
  public String toString() {
    return "["+x+","+y+"]";
  }
  /**
   * 一段意义不明的业务逻辑
   * 
   * @param min
   * @param max
   * @param t
   */
  @Deprecated
  public final void clamp(int min,int max,Vec2i t) {
    if(t.x>max) x=max;
    else if(t.x<min) x=min;
    else x=t.x;
    if(t.y>max) y=max;
    else if(t.y<min) y=min;
    else y=t.y;
  }
  public final void clampMin(int min,Vec2i t) {
    if(t.x<min) x=min;
    else x=t.x;
    if(t.y<min) y=min;
    else y=t.y;
  }
  public final void clampMax(int max,Vec2i t) {
    if(t.x>max) x=max;
    else x=t.x;
    if(t.y>max) y=max;
    else y=t.y;
  }
  public final void absolute(Vec2i t) {
    x=Math.abs(t.x);
    y=Math.abs(t.y);
  }
  public final void clamp(int min,int max) {
    if(x>max) x=max;
    else if(x<min) x=min;
    if(y>max) y=max;
    else if(y<min) y=min;
  }
  public final void clampX(int min,int max) {
    if(x>max) x=max;
    else if(x<min) x=min;
  }
  public final void clampY(int min,int max) {
    if(y>max) y=max;
    else if(y<min) y=min;
  }
  public final void clampMin(int min) {
    if(x<min) x=min;
    if(y<min) y=min;
  }
  public final void clampMax(int max) {
    if(x>max) x=max;
    if(y>max) y=max;
  }
  public final void absolute() {
    x=Math.abs(x);
    y=Math.abs(y);
  }
  public final void interpolate(Vec2i t1,Vec2i t2,int alpha) {
    x=(1-alpha)*t1.x+alpha*t2.x;
    y=(1-alpha)*t1.y+alpha*t2.y;
  }
  public final void interpolate(Vec2i t1,int alpha) {
    x=(1-alpha)*x+alpha*t1.x;
    y=(1-alpha)*y+alpha*t1.y;
  }
  public final int getX() {
    return x;
  }
  public final void setX(int x) {
    this.x=x;
  }
  public final int getY() {
    return y;
  }
  public final void setY(int y) {
    this.y=y;
  }
  public void add(int i,int j) {
    x+=i;
    y+=j;
  }
  @Override
  public void fromData(ByteBuffer in,int offset,int size) {
    x=in.getInt(offset);
    y=in.getInt(offset+=ByteBufferData.INT_SIZE);
  }
  @Override
  public ByteBuffer toData(ByteBuffer in,int offset) {
    in.putInt(offset,x);
    in.putInt(offset+=ByteBufferData.INT_SIZE,y);
    return in;
  }
  @Override
  public int bufferSize() {
    return ByteBufferData.INT_SIZE*2;
  }
}
