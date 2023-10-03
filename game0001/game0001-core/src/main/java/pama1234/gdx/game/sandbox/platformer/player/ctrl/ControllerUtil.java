package pama1234.gdx.game.sandbox.platformer.player.ctrl;

import pama1234.gdx.game.sandbox.platformer.item.Item.ItemSlot.GetItemSlot;
import pama1234.gdx.game.sandbox.platformer.player.BlockPointer;
import pama1234.gdx.game.sandbox.platformer.world.WorldBase2D;
import pama1234.gdx.game.sandbox.platformer.world.world0001.WorldType0001Base;
import pama1234.gdx.util.info.TouchInfo;

public class ControllerUtil{
  public static class ControllerBlockPointer extends BlockPointer{
    public TouchInfo info;
    public ControllerBlockPointer(WorldBase2D<? extends WorldType0001Base<?>> in,GetItemSlot slot) {
      super(in,slot);
    }
    public void info(TouchInfo in) {
      info=in;
    }
    public void testStopTask(TouchInfo in) {
      if(info==in) stopTask();
    }
  }
}