package pama1234.math.hash;

import pama1234.math.Tools;

public class DoubleFBM2f extends HashFunction2f{
  FractalNoise2f son;
  public DoubleFBM2f(float seed,FractalNoise2f son) {
    super(seed);
    this.son=son;
  }
  @Override
  public float get(float x,float y) {
    float a=seed*12.932f,b=seed*13.612f;
    float c=son.get(x+a,y+a),d=son.get(x+1+b,y+1+b);
    return Tools.mag(son.get(x+c+1.7f,y+d+9.2f),son.get(x+c+8.3f,y+d+2.8f));
  }
  //    vec2 q = vec2(fbm(st + length(vertColor.xy) * 12.932),
  //    fbm(st + vec2(1.0) + length(vertColor.zw) * 13.612));
  //
  //    vec2 r = vec2(fbm(st + q + vec2(1.7, 9.2) + (0.25 * time)),
  //    fbm(st + q + vec2(8.3, 2.8) + (0.25 * time)));
}
