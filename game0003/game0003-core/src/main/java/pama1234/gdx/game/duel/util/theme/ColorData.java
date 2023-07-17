package pama1234.gdx.game.duel.util.theme;

import java.util.LinkedHashMap;

import com.badlogic.gdx.graphics.Color;

public class ColorData{
  public float a,r,g,b;
  public ColorData() {}
  @Override
  public String toString() {
    return "0x"+Integer.toHexString(Color.rgba8888(r,g,b,a));
  }
  public void load(LinkedHashMap<String,Object> in) {
    a=Float.parseFloat(in.get("a").toString());
    r=Float.parseFloat(in.get("r").toString());
    g=Float.parseFloat(in.get("g").toString());
    b=Float.parseFloat(in.get("b").toString());
  }
}