package pama1234.gdx.game.sandbox.platformer.player;

import pama1234.gdx.game.sandbox.platformer.item.Item.ItemSlot.GetItemSlot;
import pama1234.gdx.game.sandbox.platformer.world.world0001.World0001;

public class ItemUser extends PointerBase{
  public ItemUser(World0001 pw) {
    super(pw);
  }
  public ItemUser(World0001 pw,GetItemSlot slot) {
    super(pw,slot);
  }
  @Override
  public void updateTask() {}
  @Override
  public void testTaskComplete() {}
  @Override
  public void stopTask() {}
}