package pama1234.math.vec;

import java.nio.ByteBuffer;

import javax.vecmath.Vector2f;

import pama1234.data.nio.ByteBufferData;
import pama1234.math.Tools;
import pama1234.math.UtilMath;

public class Vec2f extends Vector2f implements ByteBufferData{
  private static final long serialVersionUID=8654339263948261774L;
  public static final int buffer_size=FLOAT_SIZE*2;
  public Vec2f(int i,int j) {
    x=i;
    y=j;
  }
  public Vec2f() {}
  public Vec2f(float a,float b) {
    x=a;
    y=b;
  }
  public Vec2f(Vec2f in) {
    x=in.x;
    y=in.y;
  }
  public void add(float a,float b) {
    x+=a;
    y+=b;
  }
  public void sub(float a,float b) {
    x-=a;
    y-=b;
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
  public void moveInBox(float x1,float y1,float x2,float y2) {
    float w=x2-x1,h=y2-y1;
    while(x<x1) x+=w;
    while(x>x2) x-=w;
    while(y<y1) y+=h;
    while(y>y2) y-=h;
  }
  public void setInBox(float x1,float y1,float x2,float y2) {
    if(x<x1) x=x1;
    else if(x>x2) x=x2;
    if(y<y1) y=y1;
    else if(y>y2) y=y2;
  }
  public void rotate(float dir) {
    final float temp=x;
    x=x*UtilMath.cos(dir)-y*UtilMath.sin(dir);
    y=temp*UtilMath.sin(dir)+y*UtilMath.cos(dir);
  }
  @Override
  public String toString() {
    return "<"+Tools.cutToLastDigitString(x)+","+Tools.cutToLastDigitString(y)+">";
  }
  @Override
  public void fromData(ByteBuffer in,int offset,int size) {
    x=in.getFloat(offset);
    y=in.getFloat(offset+=FLOAT_SIZE);
  }
  @Override
  public ByteBuffer toData(ByteBuffer in,int offset) {
    in.putFloat(offset,x);
    in.putFloat(offset+=FLOAT_SIZE,y);
    return in;
  }
  @Override
  public int bufferSize() {
    return buffer_size;
  }
}
