package pama1234.gdx.game.state.state0001.game.metainfo;

import pama1234.gdx.game.state.state0001.game.item.IntItem;
import pama1234.gdx.game.state.state0001.game.metainfo.info0001.center.MetaItemCenter0001;

public class MetaIntItem extends MetaItem<IntItem>{
  public MetaIntItem(MetaItemCenter0001 pc,String name) {
    super(pc,name);
    countType=ItemCountType.INT;
  }
  @Override
  public IntItem createItem() {
    return new IntItem(this);
  }
}