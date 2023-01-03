package pama1234.gdx.game.state.state0001.game.metainfo;

import pama1234.gdx.game.state.state0001.game.item.Item;
import pama1234.gdx.game.state.state0001.game.metainfo.info0001.center.MetaItemCenter0001;

public class MetaIntItem extends MetaItem<Item>{
  public MetaIntItem(MetaItemCenter0001 pc,String name,int id) {
    super(pc,name,id);
    countType=ItemCountType.INT;
  }
  @Override
  public Item createItem() {
    return new Item(this);
  }
}