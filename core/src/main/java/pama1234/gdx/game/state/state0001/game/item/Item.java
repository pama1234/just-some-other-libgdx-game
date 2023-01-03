package pama1234.gdx.game.state.state0001.game.item;

import pama1234.gdx.game.state.state0001.game.metainfo.MetaItem;

public class Item{
  public MetaItem<?> type;
  public int[] displayType;
  public int count=1;
  public Item(MetaItem<?> type) {
    this.type=type;
    init(type);
  }
  public void init(MetaItem<?> type) {
    if(type.displayTypeSize>0) {
      displayType=new int[type.displayTypeSize];
      displayType[0]=type.getDisplayType();
    }else displayType=null;
  }
}