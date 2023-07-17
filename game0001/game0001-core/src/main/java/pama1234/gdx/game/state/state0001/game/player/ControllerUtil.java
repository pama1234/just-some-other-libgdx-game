package pama1234.gdx.game.state.state0001.game.player;

import pama1234.gdx.game.state.state0001.game.item.Item.ItemSlot.GetItemSlot;
import pama1234.gdx.game.state.state0001.game.world.WorldBase2D;
import pama1234.gdx.game.state.state0001.game.world.world0001.WorldType0001Base;
import pama1234.gdx.util.info.TouchInfo;

public class ControllerUtil{
  public static class ControllerBlockPointer extends BlockPointer{
    public TouchInfo info;
    // public float moveX,moveY;
    public ControllerBlockPointer(WorldBase2D<? extends WorldType0001Base<?>> in,GetItemSlot slot) {
      super(in,slot);
    }
    public void info(TouchInfo in) {
      info=in;
    }
    public void testStopTask(TouchInfo in) {
      if(info==in) stopTask();
    }
    // @Override
    // public void taskComplete() {
    //   super.taskComplete();
    //   if(info!=null) info.x+=pw.blockWidth();
    // }
  }
}