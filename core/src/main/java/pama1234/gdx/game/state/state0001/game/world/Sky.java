package pama1234.gdx.game.state.state0001.game.world;

import static pama1234.gdx.util.app.UtilScreen.color;
import static pama1234.gdx.util.app.UtilScreen.lerpColor;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;

import pama1234.gdx.game.asset.ImageAsset;
import pama1234.math.Tools;
import pama1234.math.UtilMath;

public class Sky{
  public World0001 pw;
  public Pixmap skyColorMap;
  public int skyColorPos,skyColorCount;
  public float daySkyGridSize;
  public Color backgroundColor,colorA,colorB;
  public float[] skyHsb=new float[3];
  public Sky(World0001 pw) {
    this.pw=pw;
    backgroundColor=color(0);
    colorA=color(0);
    colorB=color(0);
  }
  public void init() {
    ImageAsset.sky.getTexture().getTextureData().prepare();
    skyColorMap=ImageAsset.sky.getTexture().getTextureData().consumePixmap();
    skyColorCount=skyColorMap.getWidth();
    daySkyGridSize=(float)pw.settings.daySize/skyColorCount;
  }
  public void updateColor() {
    int tp=getSkyPos(pw.time);
    if(skyColorPos!=tp) {
      skyColorPos=tp;
      colorA.set(colorB);
      colorB.set(getSkyColor(tp));
    }
    lerpColor(colorA,colorB,backgroundColor,Tools.moveInRange(pw.time,0,daySkyGridSize)/daySkyGridSize);
    backgroundColor.toHsv(skyHsb);
    pw.p.backgroundColor(backgroundColor);//TODO
  }
  public int getSkyColor(int pos) {
    return skyColorMap.getPixel(pos,0);
  }
  public int getSkyPos(int in) {
    return UtilMath.floor(Tools.map(in%pw.settings.daySize,0,pw.settings.daySize,0,skyColorCount));
  }
}
