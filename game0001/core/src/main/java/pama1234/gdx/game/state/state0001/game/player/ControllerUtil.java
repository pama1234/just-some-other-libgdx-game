package pama1234.gdx.game.state.state0001.game.player;

import pama1234.gdx.game.state.state0001.game.item.Item.ItemSlot.GetItemSlot;
import pama1234.gdx.game.state.state0001.game.world.World0001;
import pama1234.gdx.util.info.TouchInfo;

public class ControllerUtil{
  public static class ControllerBlockPointer extends BlockPointer{
    public TouchInfo info;
    // public float moveX,moveY;
    public ControllerBlockPointer(World0001 in,GetItemSlot slot) {
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