package pama1234.math;

import pama1234.math.geometry.RectF;
import pama1234.math.geometry.RectI;
import pama1234.math.geometry.Triangle2f;
import pama1234.math.vec.Vec2f;

public class MathTools{
  public static float msq(float in) {
    if(in<0) return -(in*in);
    return in*in;
  }
  public static float sq(float a) {
    return a*a;
  }
  /**
   * use {@link UtilMath#clamp} instead
   * 
   * @param in
   * @param a
   * @param b
   * @return
   */
  @Deprecated
  public static float setInRange(float in,float a,float b) {
    return UtilMath.clamp(in,a,b);
  }
  public static int moveInRange(int in,int a,int b) {
    b-=a;
    in-=a;
    in-=(int)Math.floor((float)in/b)*b;
    in+=a;
    return in;
  }
  public static float moveInRange(float in,float a,float b) {
    b-=a;
    in-=a;
    in-=(int)Math.floor(in/b)*b;
    in+=a;
    return in;
  }
  public static float moveInRange(float in,float a,float b,float c) {
    b-=a;
    in-=a;
    in-=(int)Math.floor(in/c)*c;
    in+=a;
    return in;
  }
  public static float sigmoid(float in) {
    return (float)(2/(1+Math.pow(Math.E,-in))-1);
  }
  public static final int color(int r,int g,int b,int a) {
    r&=0xff;
    g&=0xff;
    b&=0xff;
    a&=0xff;
    return (a<<24)+(r<<16)+(g<<8)+b;
  }
  static public final float map(float value,float start1,float stop1,float start2,float stop2) {
    return start2+(stop2-start2)*((value-start1)/(stop1-start1));
  }
  public static boolean inRange(float a,float min,float max) {
    return a>min&&a<max;
  }
  public static boolean inRangeAdapt(float a,float min,float max) {
    return min<max?inRange(a,min,max):inRange(a,max,min);
  }
  public static boolean inBox(float a,float x,float w) {
    return a>x&&a<x+w;
  }
  public static boolean inBox(float a,float b,RectI rect) {
    return inBox(a,b,rect.x(),rect.y(),rect.w(),rect.h());
  }
  public static boolean inBox(float a,float b,float x,float y,float w,float h) {
    return a>x&&a<x+w&&b>y&&b<y+h;
  }
  public static boolean inBox(int a,int b,int x,int y,int w,int h) {
    return a>x&&a<x+w&&b>y&&b<y+h;
  }
  public static boolean inRange(float a,float b,float x1,float y1,float x2,float y2) {
    return a>x1&&a<x2&&b>y1&&b<y2;
  }
  public static boolean notInRange(float a,float b,float x1,float y1,float x2,float y2) {
    return a<x1||a>x2||b<y1||b>y2;
  }
  public static boolean inBoxInclude(int a,int b,int x,int y,int w,int h) {
    return a>=x&&a<=x+w&&b>=y&&b<=y+h;
  }
  public static boolean inRangeInclude(int a,int x1,int x2) {
    return a>=x1&&a<=x2;
  }
  public static boolean inBoxCenter(float a,float b,float x,float y,float w,float h) {
    w/=2;
    h/=2;
    return a>x-w&&a<x+w&&b>y-h&&b<y+h;
  }
  public static float lineBoxSeg(float a,float b,float x,float y,float w,float h) {
    return 0;
  }
  //TODO
  @Deprecated
  public static Vec2f lineSegIntersects(Vec2f out,float xs1,float ys1,float xe1,float ye1,float xs2,float ys2,float xe2,float ye2) {
    return out;
  }
  @Deprecated
  public static Vec2f lineSegIntersects(Vec2f out,Vec2f s1,Vec2f e1,Vec2f s2,Vec2f e2) {
    return lineSegIntersects(out,s1.x,s1.y,e1.x,e1.y,s2.x,s2.y,e2.x,e2.y);
  }
  public Vec2f lineIntersectsWithSlope(Vec2f out,float m1,float b1,float m2,float b2) {
    if(m1==m2) return null;
    float x=(b2-b1)/(m1-m2);
    float y=m1*x+b1;
    out.set(x,y);
    return out;
  }
  public static float lineSlope(float x1,float y1,float x2,float y2) {
    if(x2-x1!=0) return (y2-y1)/(x2-x1);
    return Integer.MAX_VALUE;
  }
  public static Vec2f lineIntersects(Vec2f out,Vec2f s1,Vec2f e1,Vec2f s2,Vec2f e2) {
    return lineIntersects(out,s1.x,s1.y,e1.x,e1.y,s2.x,s2.y,e2.x,e2.y);
  }
  public static Vec2f lineIntersects(Vec2f out,float xs1,float ys1,float xe1,float ye1,float xs2,float ys2,float xe2,float ye2) {
    float a1=ye1-ys1;
    float b1=xs1-xe1;
    float c1=a1*xs1+b1*ys1;
    float a2=ye2-ys2;
    float b2=xs2-xe2;
    float c2=a2*xs2+b2*ys2;
    float delta=a1*b2-a2*b1;
    out.set(((b2*c1-b1*c2)/delta),((a1*c2-a2*c1)/delta));
    return out;
  }
  public static boolean inBoxCenter(int a,int b,int x,int y,int w,int h) {
    float w2=w/=2;
    float h2=h/=2;
    return a>x-w2&&a<x+w2&&b>y-h2&&b<y+h2;
  }
  public static boolean inBox(float xIn,float yIn,RectF rect) {
    return inBox(xIn,yIn,rect.x.get(),rect.y.get(),rect.w.get(),rect.h.get());
  }
  public static boolean inRect(float a,float b,float x1,float y1,float x2,float y2) {
    return a>x1&&a<x2&&b>y1&&b<y2;
  }
  public static boolean intersects(float x1,float y1,float w1,float h1,float x2,float y2,float w2,float h2) {
    if(w2<=0||h2<=0||w1<=0||h1<=0) return false;
    w2+=x2;
    h2+=y2;
    w1+=x1;
    h1+=y1;
    return ((w2<x2||w2>x1)&&
      (h2<y2||h2>y1)&&
      (w1<x1||w1>x2)&&
      (h1<y1||h1>y2));
  }
  public static boolean intersects(int x1,int y1,int w1,int h1,int x2,int y2,int w2,int h2) {
    if(w2<=0||h2<=0||w1<=0||h1<=0) return false;
    w2+=x2;
    h2+=y2;
    w1+=x1;
    h1+=y1;
    return ((w2<x2||w2>x1)&&
      (h2<y2||h2>y1)&&
      (w1<x1||w1>x2)&&
      (h1<y1||h1>y2));
  }
  public static float cutToLastDigit(float in) {
    return (int)(in*10)/10f;
  }
  public static String cutToLastDigitString(float in) {
    return String.valueOf((int)(in*10)/10f);
  }
  public static String stringZero(int in,String string) {
    return String.format("%"+in+"s",string);
  }
  public static String intHexString(int in) {
    return String.format("%08X",in);
  }
  @Deprecated
  public static String int32Base(int in) {
    return String.format("%4s",Integer.toString(in,32));
    // return Integer.toString(in,32)+" "+intHexString(in);
  }
  public static float fractionalPart(float in) {
    return in-UtilMath.floor(in);
  }
  public static float dist(float a,float b) {
    return a>b?a-b:b-a;
  }
  /**
   * 此方法为AI生成，已验证为有错，需要修改
   * 
   * @param v 向量
   * @param t 三角形
   * @return 判断向量是否在三角形内
   */
  @Deprecated
  public static boolean inTriangle(Vec2f v,Triangle2f t) {
    // 计算三角形的三个顶点
    Vec2f a=t.getVertex(0);
    Vec2f b=t.getVertex(1);
    Vec2f c=t.getVertex(2);
    // 计算三角形的边向量
    Vec2f ab=b.subNew(a);
    Vec2f bc=c.subNew(b);
    Vec2f ca=a.subNew(c);
    // 计算二维矢量与三角形三个顶点的向量差
    // Vec2f av=v.subNew(a);
    // Vec2f bv=v.subNew(b);
    // Vec2f cv=v.subNew(c);
    // 判断二维矢量是否在三角形的内部
    boolean isInTriangle=isPointInTriangle(a,b,c,v);
    if(isInTriangle) return true;
    // 判断二维矢量是否与三角形的任意一条边相交
    boolean isIntersectEdgeAB=isIntersectEdge(v,a,ab);
    boolean isIntersectEdgeBC=isIntersectEdge(v,b,bc);
    boolean isIntersectEdgeCA=isIntersectEdge(v,c,ca);
    // 如果二维矢量在三角形内部或与三角形的任意一条边相交，则认为二维矢量与三角形相交
    // return isInTriangle||isIntersectEdgeAB||isIntersectEdgeBC||isIntersectEdgeCA;
    return isIntersectEdgeAB||isIntersectEdgeBC||isIntersectEdgeCA;
  }
  public static boolean isPointInTriangle(Vec2f a,Vec2f b,Vec2f c,Vec2f v) {
    // 计算三角形的面积
    float areaABC=0.5f*UtilMath.abs(a.crossProduct(b)+b.crossProduct(c)+c.crossProduct(a));
    // 计算三角形的边向量
    Vec2f ab=b.subNew(a);
    Vec2f bc=c.subNew(b);
    Vec2f ca=a.subNew(c);
    // 计算二维矢量与三角形三个顶点的向量差
    Vec2f av=v.subNew(a);
    Vec2f bv=v.subNew(b);
    Vec2f cv=v.subNew(c);
    // 计算二维矢量到三角形三个顶点的距离
    float distAB=av.dot(ab)/ab.length();
    float distBC=bv.dot(bc)/bc.length();
    float distCA=cv.dot(ca)/ca.length();
    // 判断二维矢量是否在三角形的内部
    float sumDist=distAB+distBC+distCA;
    float diffArea=UtilMath.abs(areaABC-0.5f*UtilMath.abs(av.crossProduct(ab)+bv.crossProduct(bc)+cv.crossProduct(ca)));
    return Math.abs(sumDist-3)<1e-6f&&diffArea<1e-6f;
  }
  public static boolean isIntersectEdge(Vec2f in,Vec2f a,Vec2f b) {
    // 计算二维矢量与边的交点
    float t=b.dot(in.subNew(a))/b.dot(b);
    Vec2f intersection=a.addNew(b.scalarMultiply(t));
    // 判断交点是否在边的内部
    float lenPToI=in.dist(intersection);
    float lenIToE=intersection.dist(a.addNew(b));
    float lenPToE=b.length();
    return lenPToI+lenIToE<lenPToE+1e-6;
  }
  public static void main(String[] args) {
    Vec2f a=new Vec2f(0,0);
    // Triangle2f b=new Triangle2f(-1,-1,1,-1,-1,4);
    Triangle2f c=new Triangle2f(-1,-1,0,-1,-1,4);
    // System.out.println(inTriangle(a,b));
    System.out.println(inTriangle(a,c));
  }
  /**
   * 
   * 此方法为AI生成，未验证
   * 
   * @param xIn
   * @param yIn
   * @param cx
   * @param cy
   * @param angle
   * @param r
   * @return
   */
  @Deprecated
  public static boolean inSector(float xIn,float yIn,float cx,float cy,float angle,float r) {
    // calculate vector angle relative to x-axis
    float vectorAngle=UtilMath.atan2(yIn,xIn)*180/UtilMath.PI;
    // calculate angle between sector center and vector
    float angleBetween=Math.abs(angle-vectorAngle);
    if(angleBetween>180) angleBetween=360-angleBetween;
    // calculate vector length
    float vectorLength=UtilMath.sqrt(xIn*xIn+yIn*yIn);
    // check if vector is within sector angle and radius
    return angleBetween<=angle/2&&vectorLength<=r;
  }
}
