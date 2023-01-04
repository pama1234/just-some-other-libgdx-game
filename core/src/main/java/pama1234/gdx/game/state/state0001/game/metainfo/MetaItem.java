package pama1234.gdx.game.state.state0001.game.metainfo;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

import pama1234.gdx.game.state.state0001.game.item.Item;
import pama1234.gdx.game.state.state0001.game.metainfo.info0001.center.MetaItemCenter0001;

public abstract class MetaItem<T extends Item>extends MetaInfoBase{
  public MetaItemCenter0001 pc;
  // public String name;
  public ItemCountType countType=ItemCountType.INT;
  public int maxCount=1;
  public int floatDivider=8;//TODO
  public float hardness,lightIntensity;//TODO
  public MetaBlock blockType;
  public TextureRegion[] tiles;
  public int displayTypeSize=1;
  public int defaultDisplayType;
  public MetaItem(MetaItemCenter0001 pc,String name,int id) {
    super(name,id);
    this.name=name;
  }
  public abstract T createItem();
  public abstract T createItem(int count);
  @Override
  public void init() {}
  public int getDisplayType() {
    return defaultDisplayType;
  }
  public enum ItemCountType{
    UNCOUNTABLE,INT;
  }
}