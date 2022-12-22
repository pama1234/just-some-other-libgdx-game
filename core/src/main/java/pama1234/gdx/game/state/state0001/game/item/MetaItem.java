package pama1234.gdx.game.state.state0001.game.item;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class MetaItem<T extends Item>{
  public MetaItemCenter pc;
  public String name;
  public int floatDivider=8;//TODO
  public TextureRegion[] tiles;
  public float hardness,lightIntensity;//TODO
  public int displayTypeSize=1;
  public int defaultDisplayType;
  public MetaItem(MetaItemCenter pc,String name) {
    this.pc=pc;
    this.name=name;
  }
  public MetaItem(MetaItemCenter pc,String name,TextureRegion[] tiles) {
    this.pc=pc;
    this.name=name;
    this.tiles=tiles;
  }
  public void initTextureRegion() {}
  public int getDisplayType() {
    return defaultDisplayType;
  }
}
