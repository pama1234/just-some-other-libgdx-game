package pama1234.math.vec;

import java.nio.ByteBuffer;

import javax.vecmath.Vector3f;

import pama1234.data.nio.ByteBufferData;
import pama1234.math.Tools;
import pama1234.math.UtilMath;

public class Vec3f extends Vector3f implements ByteBufferData{
  private static final long serialVersionUID=8654339263948261774L;
  public static final int buffer_size=FLOAT_SIZE*3;
  public Vec3f(int i,int j,int k) {
    x=i;
    y=j;
    z=k;
  }
  public Vec3f() {}
  public Vec3f(float a,float b,float c) {
    x=a;
    y=b;
    z=c;
  }
  public Vec3f(Vec3f in) {
    x=in.x;
    y=in.y;
    z=in.z;
  }
  @Deprecated
  public Vec3f(Vec2f in) {
    x=in.x;
    y=in.y;
  }
  public void add(float a,float b,float c) {
    x+=a;
    y+=b;
    z+=c;
  }
  public void sub(float a,float b,float c) {
    x-=a;
    y-=b;
    z-=c;
  }
  public float dist(float a,float b) {
    return (float)Math.sqrt(Tools.sq(x-a)+Tools.sq(y-b));
  }
  public boolean toNumber() {
    if(Float.isInfinite(x)||Float.isNaN(x)) {
      x=0;
      return true;
    }
    if(Float.isInfinite(y)||Float.isNaN(y)) {
      y=0;
      return true;
    }
    return false;
  }
  public void moveInBox(float x1,float y1,float z1,float x2,float y2,float z2) {
    float w=x2-x1,l=y2-y1,h=z2-z1;
    while(x<x1) x+=w;
    while(x>x2) x-=w;
    while(y<y1) y+=l;
    while(y>y2) y-=l;
    while(z<z1) z+=h;
    while(z>z2) z-=h;
  }
  public void setInBox(float x1,float y1,float z1,float x2,float y2,float z2) {
    if(x<x1) x=x1;
    else if(x>x2) x=x2;
    if(y<y1) y=y1;
    else if(y>y2) y=y2;
    if(z<z1) z=z1;
    else if(z>z2) z=z2;
  }
  public void rotate(float dir) {
    final float temp=x;
    x=x*UtilMath.cos(dir)-y*UtilMath.sin(dir);
    y=temp*UtilMath.sin(dir)+y*UtilMath.cos(dir);
  }
  //TODO 3
  @Deprecated
  public void rotateX(float dir) {
    final float temp=x;
    x=x*UtilMath.cos(dir)-y*UtilMath.sin(dir);
    y=temp*UtilMath.sin(dir)+y*UtilMath.cos(dir);
  }
  @Deprecated
  public void rotateY(float dir) {
    final float temp=x;
    x=x*UtilMath.cos(dir)-y*UtilMath.sin(dir);
    y=temp*UtilMath.sin(dir)+y*UtilMath.cos(dir);
  }
  @Deprecated
  public void rotateZ(float dir) {
    final float temp=x;
    x=x*UtilMath.cos(dir)-y*UtilMath.sin(dir);
    y=temp*UtilMath.sin(dir)+y*UtilMath.cos(dir);
  }
  @Override
  public String toString() {
    return "<"+Tools.cutToLastDigitString(x)+","+Tools.cutToLastDigitString(y)+","+Tools.cutToLastDigitString(z)+">";
  }
  @Override
  public void fromData(ByteBuffer in,int offset,int size) {
    x=in.getFloat(offset);
    y=in.getFloat(offset+=FLOAT_SIZE);
    z=in.getFloat(offset+=FLOAT_SIZE);
  }
  @Override
  public ByteBuffer toData(ByteBuffer in,int offset) {
    in.putFloat(offset,x);
    in.putFloat(offset+=FLOAT_SIZE,y);
    in.putFloat(offset+=FLOAT_SIZE,z);
    return in;
  }
  @Override
  public int bufferSize() {
    return buffer_size;
  }
}
