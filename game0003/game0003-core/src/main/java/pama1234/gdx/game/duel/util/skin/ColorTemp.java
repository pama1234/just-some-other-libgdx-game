package pama1234.gdx.game.duel.util.skin;

import com.badlogic.gdx.graphics.Color;

public class ColorTemp{
  public float a,r,g,b;
  public ColorTemp() {}
  @Override
  public String toString() {
    return "0x"+Color.argb8888(a,r,g,b);
  }
}