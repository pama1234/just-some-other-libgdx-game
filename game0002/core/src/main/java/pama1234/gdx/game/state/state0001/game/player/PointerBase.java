package pama1234.gdx.game.state.state0001.game.player;

import pama1234.gdx.game.state.state0001.game.item.Item.ItemSlot;
import pama1234.gdx.game.state.state0001.game.item.Item.ItemSlot.GetItemSlot;
import pama1234.gdx.game.state.state0001.game.world.World0001;

public abstract class PointerBase{
  public World0001 pw;
  public GetItemSlot slot;
  public int task;
  public boolean active;
  public PointerBase(World0001 pw) {
    this.pw=pw;
  }
  public PointerBase(World0001 pw,GetItemSlot slot) {
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