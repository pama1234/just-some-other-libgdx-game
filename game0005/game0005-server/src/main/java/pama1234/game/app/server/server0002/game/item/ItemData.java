package pama1234.game.app.server.server0002.game.item;

import com.esotericsoftware.kryo.serializers.TaggedFieldSerializer.Tag;

public class ItemData{
  // public static final Kryo kryo=new Kryo();
  public MetaItemData type;
  @Tag(0)
  public int typeId;
  public int[] displayType;
  @Tag(1)
  public int count=1;
  @Deprecated
  public ItemData() {}//kryo
  public ItemData(MetaItemData type) {
    this.type=type;
    init(type);
    count=1;
  }
  public ItemData(MetaItemData type,int count) {
    this.type=type;
    init(type);
    this.count=count;
  }
  public void init(MetaItemData type) {
    typeId=type.id;
    if(type.displayTypeSize>0) {
      displayType=new int[type.displayTypeSize];
      displayType[0]=type.getDisplayType();
    }else displayType=null;
  }
  public static class ItemSlotData{
    public int referenceCount;//TODO
    @Tag(0)
    public ItemData item;
    public ItemSlotData() {}
    public ItemSlotData(ItemData item) {
      this.item=item;
    }
    @FunctionalInterface
    public interface GetItemSlot{
      public ItemSlotData get();
    }
  }
  public int displayType() {
    return displayType==null?0:displayType[0];
  }
}
