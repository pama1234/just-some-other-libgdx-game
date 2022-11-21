package pama1234.math;

public class UtilMath{
  static public final float radDeg=(float)(180./Math.PI);
  static public final float degRad=(float)(Math.PI/180.);
  public static int min(int a,int b) {
    return a<b?a:b;
  }
  public static int max(int a,int b) {
    return a>b?a:b;
  }
  public static float dist(float x1,float y1,float x2,float y2) {
    return mag(x1-x2,y1-y2);
  }
  public static float dist(float x1,float y1,float z1,float x2,float y2,float z2) {
    return mag(x1-x2,y1-y2,z1-z2);
  }
  public static float mag(float a,float b) {
    return sqrt(a*a+b*b);
  }
  public static float mag(float a,float b,float c) {
    return sqrt(a*a+b*b+c*c);
  }
  public static float sqrt(float in) {
    return (float)Math.sqrt(in);
  }
  public static float sin(float frameCount) {
    return (float)Math.sin(frameCount);
  }
  public static float sinDeg(float frameCount) {
    return sin(deg(frameCount));
  }
  public static float cos(float frameCount) {
    return (float)Math.cos(frameCount);
  }
  public static float cosDeg(float frameCount) {
    return cos(deg(frameCount));
  }
  public static int dist(int a,int b) {
    return abs(a-b);
  }
  public static float dist(float a,float b) {
    return abs(a-b);
  }
  public static float abs(float in) {
    if(in<0) return -in;
    return in;
  }
  public static int abs(int in) {
    if(in<0) return -in;
    return in;
  }
  public static float asin(float in) {
    return (float)Math.asin(in);
  }
  public static float acos(float in) {
    return (float)Math.acos(in);
  }
  public static float asinDeg(float in) {
    return deg((float)Math.asin(in));
  }
  public static float acosDeg(float in) {
    return deg((float)Math.acos(in));
  }
  public static float deg(float in) {
    return radDeg*in;
  }
  public static float rad(float in) {
    return degRad*in;
  }
  public static float avg(float a,float b) {
    return (a+b)/2;
  }
  public static int round(float in) {
    return Math.round(in);
  }
  public static int floor(float in) {
    return (int)Math.floor(in);
  }
  public static float lerp(float a,float b,float f) {
    return (b-a)*f+a;
  }
  public static int constrain(int in,int min,int max) {
    return (in<min)?min:((in>max)?max:in);
  }
  public static float constrain(float in,float min,float max) {
    return (in<min)?min:((in>max)?max:in);
  }
  public static float map(float in,float s1,float e1,float s2,float e2) {
    return s2+(e2-s2)*((in-s1)/(e1-s1));
  }
  public static float sq(float a) {
    return a*a;
  }
  public static int sq(int a) {
    return a*a;
  }
  public static float pow(float in,float n) {
    return (float)Math.pow(in,n);
  }
  public static int pow(int in,int n) {
    return (int)Math.pow(in,n);
  }
}