package pama1234.math.hash;

public class HashNoise2f extends HashFunction2f{
  public HashNoise2f(float seed) {
    super(seed);
  }
  public float get(float x,float y) {
    x*=1.13f;
    y*=1.13f;
    float z=seed*1.13f;
    x%=1;
    y%=1;
    z%=1;
    float temp=dot(x,y,z,z+3.333f,y+3.333f,x+3.333f);
    x+=temp;
    y+=temp;
    z+=temp;
    return ((x+y)*z)%1;
  }
  private static float dot(float a,float b,float c,float x,float y,float z) {
    return a*x+b*y+c*z;
  }
}
