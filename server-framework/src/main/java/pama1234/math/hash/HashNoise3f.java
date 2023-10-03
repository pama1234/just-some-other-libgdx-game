package pama1234.math.hash;

@Deprecated
public class HashNoise3f extends HashFunction3f{
  public HashNoise3f(float seed) {
    super(seed);
  }
  @Override
  public float get(float x,float y,float z) {
    x*=1.13f;
    y*=1.13f;
    z*=1.13f;
    float w=seed*1.13f;
    x%=1;
    y%=1;
    z%=1;
    float temp=dot(x,y,z,w+3.333f,y+3.333f,x+3.333f);
    x+=temp;
    y+=temp;
    z+=temp;
    return ((x+y+z)*w)%1;
  }
  private static float dot(float a,float b,float c,float x,float y,float z) {
    return a*x+b*y+c*z;
  }
}
