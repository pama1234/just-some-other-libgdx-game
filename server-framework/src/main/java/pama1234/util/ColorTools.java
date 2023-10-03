package pama1234.util;

import pama1234.math.MathTools;

public class ColorTools extends MathTools{
  public static int color(float r,float g,float b,float a) {
    return color((int)r,(int)g,(int)b,(int)a);
  }
  public static String colorToString(int color) {
    return intHexString(color);
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
      case 0:
        // Red is the dominant color
        red=b;
        green=tv;
        blue=pv;
        break;
      case 1:
        // Green is the dominant color
        red=qv;
        green=b;
        blue=pv;
        break;
      case 2:
        red=pv;
        green=b;
        blue=tv;
        break;
      case 3:
        // Blue is the dominant color
        red=pv;
        green=qv;
        blue=b;
        break;
      case 4:
        red=tv;
        green=pv;
        blue=b;
        break;
      case 5:
        // Red is the dominant color
        red=b;
        green=pv;
        blue=qv;
        break;
    }
    return 0xff000000|((int)Math.min(red*256,255)<<16)|((int)Math.min(green*256,255)<<8)|(int)Math.min(blue*256,255);
  }
  public static int hsbColorInt(float a,float b,float c) {
    return hsbColor((int)a,(int)b,(int)c);
  }
  public static int lerpColor(int a,int b,float pos) {
    int out;
    if(pos==0) out=a;
    else if(pos==1) out=b;
    else {
      int ra=red(a);
      int ga=green(a);
      int ba=blue(a);
      int aa=alpha(a);
      float tr=red(b)-ra,
        tg=green(b)-ga,
        tb=blue(b)-ba,
        ta=alpha(b)-aa;
      out=color(ra+tr*pos,ga+tg*pos,ga+tb*pos,aa+ta*pos);
    }
    return out;
  }
  /**
   * red of RGBA8888
   * 
   * @param in
   * @return
   */
  public static int red(int in) {
    return (in>>>24)&0xff;
  }
  /**
   * green of RGBA8888
   * 
   * @param in
   * @return
   */
  public static int green(int in) {
    return (in>>>16)&0xff;
  }
  /**
   * blue of RGBA8888
   * 
   * @param in
   * @return
   */
  public static int blue(int in) {
    return (in>>>8)&0xff;
  }
  /**
   * alpah of RGBA8888
   * 
   * @param in
   * @return
   */
  public static int alpha(int in) {
    return in&0xff;
  }
}
