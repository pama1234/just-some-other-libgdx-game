package pama1234.gdx.game.state.state0001.game.item;

import com.esotericsoftware.kryo.serializers.TaggedFieldSerializer.Tag;

import pama1234.gdx.game.state.state0001.game.metainfo.MetaItem;

public class Item{
  // public static final Kryo kryo=new Kryo();
  public MetaItem type;
  @Tag(0)
  public int typeId;
  public int[] displayType;
  @Tag(1)
  public int count=1;
  @Deprecated
  public Item() {}//kryo
  public Item(MetaItem type) {
    this.type=type;
    typeId=type.id;
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
  public static class ItemSlot{
    public int referenceCount;//TODO
    @Tag(0)
    public Item item;
    public ItemSlot() {}
    public ItemSlot(Item item) {
      this.item=item;
    }
    @FunctionalInterface
    public interface GetItemSlot{
      public ItemSlot get();
    }
  }
}