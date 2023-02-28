package pama1234.math.hash;

import pama1234.math.UtilMath;

public class FractalNoise2f extends HashFunction2f{
  public static final float[] rot=new float[] {
    UtilMath.cos(0.5f),UtilMath.sin(0.5f),
    -UtilMath.sin(0.5f),UtilMath.cos(0.5f)};
  public float iter;
  public HashFunction2f son;
  public FractalNoise2f(HashFunction2f son,float iter) {
    super(0);
    this.son=son;
    this.iter=iter;
  }
  @Override
  public float get(float x,float y) {
    float out=0;
    float a=0.5f;
    for(int i=0;i<iter;i++) {
      out+=a*son.get(x,y);
      float temp=x=rot[0]*x+rot[1]*y;
      y=(rot[2]*x+rot[3]*y)*2+100;
      x=(temp)*2+100;
      a*=0.5f;
    }
    return out;
  }
}
