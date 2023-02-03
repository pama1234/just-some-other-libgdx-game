package pama1234.gdx.game.state.state0001.game.item;

import com.esotericsoftware.kryo.serializers.TaggedFieldSerializer.Tag;

import pama1234.gdx.game.state.state0001.game.metainfo.MetaItem;

public class Item{
  public MetaItem type;
  @Tag(0)
  public int typeId;
  public int[] displayType;
  @Tag(1)
  public int count=1;
  @Deprecated
  public Item() {}
  public Item(MetaItem type) {
    this.type=type;
    init(type);
    count=1;
  }
  public Item(MetaItem type,int count) {
    this.type=type;
    init(type);
    this.count=count;
  }
  public void init(MetaItem type) {
    if(type.displayTypeSize>0) {
      displayType=new int[type.displayTypeSize];
      displayType[0]=type.getDisplayType();
    }else displayType=null;
  }
}