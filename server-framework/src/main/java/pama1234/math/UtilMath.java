package pama1234.math;

public class UtilMath{
  public static final float radDeg=(float)(180./Math.PI);
  public static final float degRad=(float)(Math.PI/180.);
  public static final float PI=(float)Math.PI;
  public static final float PI2=PI*2;
  /**
   * 请替换为 {@link UtilMath#PI2 PI2}
   */
  @Deprecated
  public static final float TWO_PI=PI2;
  public static final float HALF_PI=PI/2;
  public static final float QUARTER_PI=PI/4;
  public static final float FLOAT_ROUNDING_ERROR=0.000001f; // 32 bits
  public static int min(int a,int b) {
    return a<b?a:b;
  }
  public static int max(int a,int b) {
    return a>b?a:b;
  }
  public static float min(float a,float b) {
    return a<b?a:b;
  }
  public static float min(float... in) {//TODO
    // if(in==null||in.length==0) return Integer.MIN_VALUE;
    float out=Integer.MAX_VALUE;
    for(float f:in) if(f<out) out=f;
    return out;
  }
  public static float max(float a,float b) {
    return a>b?a:b;
  }
  public static float dist(float[] a,float[] b) {
    float out=0;
    for(int i=0;i<a.length;i++) out+=sq(a[i]-b[i]);
    return sqrt(out);
  }
  public static float dist(float x1,float y1,float x2,float y2) {
    return mag(x1-x2,y1-y2);
  }
  public static float dist(float x1,float y1,float z1,float x2,float y2,float z2) {
    return mag(x1-x2,y1-y2,z1-z2);
  }
  public static float mag(float[] in) {
    float out=0;
    for(float e:in) out+=e*e;
    return sqrt(out);
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
  public static float sin(float in) {
    return (float)Math.sin(in);
  }
  public static float sinDeg(float in) {
    return sin(deg(in));
  }
  public static float cos(float in) {
    return (float)Math.cos(in);
  }
  public static float cosDeg(float in) {
    return cos(deg(in));
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
  public static int ceil(float in) {
    return (int)Math.ceil(in);
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
  public static float dot(float x,float y,float z,float x2,float y2,float z2) {
    return x*x2+y*y2+z*z2;
  }
  public static float atanUnchecked(double i) {
    // We use double precision internally, because some constants need double precision.
    double n=Math.abs(i);
    // c uses the "equally-good" formulation that permits n to be from 0 to almost infinity.
    double c=(n-1.0)/(n+1.0);
    // The approximation needs 6 odd powers of c.
    double c2=c*c;
    double c3=c*c2;
    double c5=c3*c2;
    double c7=c5*c2;
    double c9=c7*c2;
    double c11=c9*c2;
    return (float)Math.copySign((Math.PI*0.25)
      +(0.99997726*c-0.33262347*c3+0.19354346*c5-0.11643287*c7+0.05265332*c9-0.0117212*c11),i);
  }
  public static float atan2(float y,float x) {
    float n=y/x;
    if(n!=n) n=(y==x?1f:-1f); // if both y and x are infinite, n would be NaN
    else if(n-n!=n-n) x=0f; // if n is infinite, y is infinitely larger than x.
    if(x>0) return atanUnchecked(n);
    else if(x<0) {
      if(y>=0) return atanUnchecked(n)+PI;
      return atanUnchecked(n)-PI;
    }else if(y>0) return x+HALF_PI;
    else if(y<0) return x-HALF_PI;
    return x+y; // returns 0 for 0,0 or NaN if either y or x is NaN
  }
  public static float atan(float in) {
    return (float)Math.atan(in);
  }
  public static float log(float in) {
    return (float)Math.log(in);
  }
  public static float clamp(float value,float min,float max) {
    if(value<min) return min;
    if(value>max) return max;
    return value;
  }
  public static boolean nearEqual(float a,float b) {
    return Math.abs(a-b)<=FLOAT_ROUNDING_ERROR;
  }
  public static boolean nearZero(float in) {
    return Math.abs(in)<=FLOAT_ROUNDING_ERROR;
  }
  public static boolean nearEqual(float a,float b,float c) {
    return Math.abs(a-b)<=c;
  }
  public static boolean nearZero(float in,float c) {
    return Math.abs(in)<=c;
  }
  public static int floatToIntBits(float value) {
    return Float.floatToIntBits(value);
  }
  public static int floatToRawIntBits(float value) {
    return Float.floatToRawIntBits(value);
  }
  /**
   * Converts the color from a float ABGR encoding to an int ABGR encoding. The alpha is expanded
   * from 0-254 in the float encoding (see {@link #intToFloatColor(int)}) to 0-255, which means
   * converting from int to float and back to int can be lossy.
   */
  public static int floatToIntColor(float value) {
    int intBits=Float.floatToRawIntBits(value);
    intBits|=(int)((intBits>>>24)*(255f/254f))<<24;
    return intBits;
  }
  /**
   * Encodes the ABGR int color as a float. The alpha is compressed to use only even numbers
   * between 0-254 to avoid using bits in the NaN range (see {@link Float#intBitsToFloat(int)}
   * javadocs). Rendering which uses colors encoded as floats should expand the 0-254 back to
   * 0-255, else colors cannot be fully opaque.
   */
  public static float intToFloatColor(int value) {
    return Float.intBitsToFloat(value&0xfeffffff);
  }
  public static float intBitsToFloat(int value) {
    return Float.intBitsToFloat(value);
  }
  public static long doubleToLongBits(double value) {
    return Double.doubleToLongBits(value);
  }
  public static double longBitsToDouble(long value) {
    return Double.longBitsToDouble(value);
  }
  public static float tanh(float in) {
    return (float)Math.tanh(in);
  }
  public static float exp(float in) {
    return (float)Math.exp(in);
  }
}