package pama1234.math.hash;

import pama1234.math.UtilMath;

public class PerlinNoise2f extends HashFunction2f{
  public HashFunction2f son;
  public PerlinNoise2f(HashFunction2f son) {
    super(0);
    this.son=son;
  }
  @Override
  public float get(float x,float y) {
    int ix=UtilMath.floor(x),iy=UtilMath.floor(y);
    x-=ix;
    y-=iy;
    float a=son.get(ix,iy),
      b=son.get(ix+1,iy),
      c=son.get(ix,iy+1),
      d=son.get(ix+1,iy+1);
    float ux=x*x*(3-2*x),uy=y*y*(3-2*y);
    return UtilMath.lerp(a,b,ux)+(c-a)*uy*(1-ux)+(d-b)*ux*uy;
  }
}
