package pama1234.gdx.game.state.state0001.game.metainfo;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

import pama1234.gdx.game.state.state0001.game.item.Item;

public abstract class MetaItem<T extends Item>extends MetaInfoBase{
  public MetaItemCenter pc;
  public String name;
  public int floatDivider=8;//TODO
  public float hardness,lightIntensity;//TODO
  public MetaBlock blockType;
  public TextureRegion[] tiles;
  public int displayTypeSize=1;
  public int defaultDisplayType;
  public MetaItem(MetaItemCenter pc,String name) {
    this.pc=pc;
    this.name=name;
  }
  // public MetaItem(MetaItemCenter pc,String name,TextureRegion[] tiles) {
  //   this.pc=pc;
  //   this.name=name;
  //   this.tiles=tiles;
  // }
  public abstract T createItem();
  @Override
  public void init() {}
  public int getDisplayType() {
    return defaultDisplayType;
  }
}
