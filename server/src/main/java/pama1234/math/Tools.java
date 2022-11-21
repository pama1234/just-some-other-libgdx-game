package pama1234.math;

import pama1234.math.vec.Vec2f;

// import com.badlogic.gdx.graphics.Color;
// import com.badlogic.gdx.math.MathUtils;
public class Tools{
  public static float msq(float in) {
    if(in<0) return -(in*in);
    return in*in;
  }
  public static float sq(float a) {
    return a*a;
  }
  public static int moveInRange(int in,int a,int b) {
    b-=a;
    in-=a;
    in-=(int)Math.floor((float)in/b)*b;
    in+=a;
    return in;
  }
  // public static void main(String[] args) {
  //   System.out.println(moveInRange(9,0,8));
  //   System.out.println(moveInRange(9,0,9));
  //   System.out.println(moveInRange(9,0,10));
  // }
  public static float moveInRange(float in,float a,float b) {
    b-=a;
    in-=a;
    in-=(int)Math.floor(in/b)*b;
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
  public static boolean inBox(float a,float b,float x,float y,float w,float h) {
    return a>x&&a<x+w&&b>y&&b<y+h;
  }
  public static boolean inBox(int a,int b,int x,int y,int w,int h) {
    return a>x&&a<x+w&&b>y&&b<y+h;
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
  // public static void main(String[] args) {
  //   // System.out.println(lineSegIntersects(new Vec2f(),0,0,1,1,2,1,3,0));
  //   System.out.println(lineSlope(0,0,10,10));
  //   System.out.println(lineSlope(0,0,10,10));
  // }
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
  public static int color(float r,float g,float b,float a) {
    return color((int)r,(int)g,(int)b,(int)a);
  }
  public static String colorToString(int color) {
    return Integer.toHexString(color);
  }
  public static int color(final int a) {
    return color(a,a,a,0xff);
  }
  public static int color(int r,int g,int b) {
    return color(r,g,b,0xff);
  }
  public static int color(float r,float g,float b) {
    return color((int)r,(int)g,(int)b,0xff);
  }
  public static float fractionalPart(float in) {
    return in-UtilMath.floor(in);
  }
  public static float mag(float a,float b) {
    return a>b?a-b:b-a;
  }
  public static void println(Object... in) {
    StringBuilder sb=new StringBuilder();
    for(Object o:in) {
      if(sb.length()!=0) sb.append(" ");
      if(o==null) sb.append("null");
      else sb.append(o.toString());
    }
    sb.append('\n');
    System.out.print(sb.toString());
    System.out.flush();
  }
  public static int hsbColor(int hue,int saturation,int brightness) {//TODO add more
    float h=hue/255f,
      s=saturation/255f,
      b=brightness/255f;
    // h%=1f;
    // if(h<0f) h++;
    // s=MathUtils.clamp(s,0.0f,1.0f);
    // b=MathUtils.clamp(b,0.0f,1.0f);
    float red=0.0f;
    float green=0.0f;
    float blue=0.0f;
    final float hf=(h-(int)h)*6.0f;
    final int ihf=(int)hf;
    final float f=hf-ihf;
    final float pv=b*(1.0f-s);
    final float qv=b*(1.0f-s*f);
    final float tv=b*(1.0f-s*(1.0f-f));
    switch(ihf) {
      case 0: // Red is the dominant color
        red=b;
        green=tv;
        blue=pv;
        break;
      case 1: // Green is the dominant color
        red=qv;
        green=b;
        blue=pv;
        break;
      case 2:
        red=pv;
        green=b;
        blue=tv;
        break;
      case 3: // Blue is the dominant color
        red=pv;
        green=qv;
        blue=b;
        break;
      case 4:
        red=tv;
        green=pv;
        blue=b;
        break;
      case 5: // Red is the dominant color
        red=b;
        green=pv;
        blue=qv;
        break;
    }
    return 0xff000000|((int)Math.min(red*256,255)<<16)|((int)Math.min(green*256,255)<<8)|(int)Math.min(blue*256,255);
  }
  public static int hsbColor(float a,float b,float c) {
    return hsbColor((int)a,(int)b,(int)c);
  }
}
