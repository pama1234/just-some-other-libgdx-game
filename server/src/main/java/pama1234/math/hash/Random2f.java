package pama1234.math.hash;

import static pama1234.math.UtilMath.abs;
import static pama1234.math.UtilMath.floor;

import pama1234.math.UtilMath;

public class Random2f extends HashFunction2f{
  public Random2f(float seed) {
    super(seed);
  }
  @Override
  public float get(float x,float y) {
    float temp=UtilMath.sin(x*12.9898f+y*78.233f)*(43758.5453123f+seed);
    return abs(temp-floor(temp));
  }
}
