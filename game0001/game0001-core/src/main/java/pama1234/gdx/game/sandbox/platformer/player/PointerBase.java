package pama1234.gdx.game.sandbox.platformer.player;

import pama1234.gdx.game.sandbox.platformer.item.Item.ItemSlot;
import pama1234.gdx.game.sandbox.platformer.item.Item.ItemSlot.GetItemSlot;
import pama1234.gdx.game.sandbox.platformer.world.WorldBase2D;
import pama1234.gdx.game.sandbox.platformer.world.world0001.WorldType0001Base;

public abstract class PointerBase{
  public WorldBase2D<? extends WorldType0001Base<?>> pw;
  public GetItemSlot slot;
  public int task;
  public boolean active;
  public PointerBase(WorldBase2D<? extends WorldType0001Base<?>> pw) {
    this.pw=pw;
  }
  public PointerBase(WorldBase2D<? extends WorldType0001Base<?>> pw,GetItemSlot slot) {
    this.pw=pw;
    this.slot=slot;
  }
  public ItemSlot slot() {
    return slot.get();
  }
  public abstract void updateTask();
  public abstract void testTaskComplete();
  public abstract void stopTask();
}