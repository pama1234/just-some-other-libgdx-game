package pama1234.math.vec;

import java.nio.ByteBuffer;

import javax.vecmath.Vector2f;

import pama1234.Tools;
import pama1234.data.nio.ByteBufferData;
import pama1234.math.UtilMath;
import pama1234.math.geometry.RectI;

/**
 * 基于vecmath的矢量数据类，只包含两个浮点数
 * 
 * @see {@link javax.vecmath.Tuple2f#x x成员变量的位置}
 * @see {@link javax.vecmath.Tuple2f#y y成员变量的位置}
 */
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
  public Vec2f addNew(Vec2f in) {
    return new Vec2f(x+in.x,y+in.y);
  }
  public void sub(float a,float b) {
    x-=a;
    y-=b;
  }
  public Vec2f subNew(Vec2f in) {
    return new Vec2f(x-in.x,y-in.y);
  }
  public float crossProduct(Vec2f v) {
    return x*v.y-y*v.x;
  }
  public Vec2f scalarMultiply(float k) {
    return new Vec2f(x*k,y*k);
  }
  /**
   * 
   * @param a 输入的x值
   * @param b 输入的y值
   * @return 浮点数 输入的矢量和这个矢量的距离
   */
  public float dist(float a,float b) {
    return (float)Math.sqrt(Tools.sq(x-a)+Tools.sq(y-b));
  }
  public float dist(Vec2f in) {
    return dist(in.x,in.y);
  }
  /**
   * 测试其中的两个数值是否为“正无穷，负无穷，NaN”，如果是这三者，则将其赋值为0
   * 
   * @return 是否发生了转换
   */
  public boolean toNumber() {
    boolean flag=false;
    if(Float.isInfinite(x)||Float.isNaN(x)) {
      x=0;
      flag=true;
    }
    if(Float.isInfinite(y)||Float.isNaN(y)) {
      y=0;
      flag=true;
    }
    return flag;
  }
  /**
   * 将向量中的x和y移动到一个矩形的方框内，保留其剩余位置信息，类似于“%”取余
   * 
   * @param x1 左上角的x
   * @param y1 左上角的y
   * @param x2 右下角的x
   * @param y2 右下角的y
   */
  public void moveInBox(float x1,float y1,float x2,float y2) {
    // float w=x2-x1,h=y2-y1;
    // while(x<x1) x+=w;
    // while(x>x2) x-=w;
    // while(y<y1) y+=h;
    // while(y>y2) y-=h;
    x=Tools.moveInRange(x,x1,x2);
    y=Tools.moveInRange(y,y1,y2);
  }
  /**
   * 将向量中的x和y移动到一个矩形的方框内，不保留其剩余位置信息
   * 
   * @param x1 左上角的x
   * @param y1 左上角的y
   * @param x2 右下角的x
   * @param y2 右下角的y
   */
  public void setInBox(float x1,float y1,float x2,float y2) {
    if(x<x1) x=x1;
    else if(x>x2) x=x2;
    if(y<y1) y=y1;
    else if(y>y2) y=y2;
  }
  /**
   * 
   * @param dir 旋转的角度，PI制（也就是弧度制，而不是360制，或者说角度制）
   */
  public void rotate(float dir) {
    final float temp=x;
    x=x*UtilMath.cos(dir)-y*UtilMath.sin(dir);
    y=temp*UtilMath.sin(dir)+y*UtilMath.cos(dir);
  }
  /**
   * 输出示例：
   * </p>
   * "<0.0,0.0>"
   * </p>
   * "<-1.2,3.1>"
   */
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
  public boolean inBox(float xIn,float yIn,float w,float h) {
    return Tools.inBox(x,y,xIn,yIn,w,h);
  }
  public boolean inBox(RectI rect) {
    return Tools.inBox(x,y,rect.x(),rect.y(),rect.w(),rect.h());
  }
  public boolean inRange(float x1,float y1,float x2,float y2) {
    return Tools.inRange(x,y,x1,y1,x2,y2);
  }
}
