package pama1234.gdx.game.cgj.util.legacy;

import pama1234.math.UtilMath;
import pama1234.math.geometry.RectD;

public class Box{
  public RectD rect;
  public int time;
  public float size;
  public int displayAlpha;
  public Box(RectD rect) {
    this.rect=rect;
    size=UtilMath.mag(rect.w(),rect.h())/2f;
  }
}
