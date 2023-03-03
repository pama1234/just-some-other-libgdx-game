package pama1234.game.app.server.server0002.game.item;

import com.esotericsoftware.kryo.serializers.TaggedFieldSerializer.Tag;

import pama1234.game.app.server.server0002.game.item.ItemData.ItemSlotData;

public class InventoryData{
  @Tag(0)
  public ItemSlotData[] data;
  @Tag(1)
  int hotSlotSize;
  @Tag(2)
  public int selectSlot;
}