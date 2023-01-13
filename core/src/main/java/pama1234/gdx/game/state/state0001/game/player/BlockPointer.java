package pama1234.gdx.game.state.state0001.game.player;

import com.badlogic.gdx.Input.Buttons;

import pama1234.gdx.game.state.state0001.game.item.Inventory.InventorySlot;
import pama1234.gdx.game.state.state0001.game.metainfo.MetaBlock;
import pama1234.gdx.game.state.state0001.game.region.block.Block;
import pama1234.gdx.game.state.state0001.game.world.World0001;

public class BlockPointer{
  @FunctionalInterface
  public interface GetInventorySlot{
    public InventorySlot get();
  }
  public static final int idle=0,build=1,destroy=2;
  public World0001 pw;
  // public InventorySlot slot;
  public BlockPointer.GetInventorySlot slot;
  public Block block;
  public int x,y;
  public int task;
  public int progress;
  public BlockPointer(World0001 in) {
    pw=in;
  }
  public BlockPointer(Block block,int x,int y) {
    this.block=block;
    this.x=x;
    this.y=y;
  }
  public void pos(int xIn,int yIn) {
    x=xIn;
    y=yIn;
  }
  public void update(Block in,int x,int y) {
    pos(x,y);
    if(in!=block) {
      block=in;
      progress=0;
    }else updateTask();
  }
  public InventorySlot slot() {
    return slot.get();
  }
  public void startTask(int type) {
    task=type;
    progress=0;
  }
  public void startTaskButtonInfo(int button) {
    switch(button) {
      case Buttons.LEFT: {
        startTask(destroy);
      }
        break;
      case Buttons.RIGHT: {
        startTask(build);
      }
        break;
      // default:
      //   break;
    }
  }
  public void updateTask() {
    progress++;
    testTaskComplete();
    // if(progress>=block.type.buildTime)
  }
  public void testTaskComplete() {
    switch(task) {
      case build: {
        // if(progress>=block.type.buildTime) {
        InventorySlot ts=slot();
        if(ts.item==null) break;
        MetaBlock tbt=ts.item.type.blockType;
        if(tbt==null||block==null||block.type==tbt) progress=0;
        else if(progress>=tbt.buildTime) {
          progress=0;
          // if(block.type!=tbt) {
          pw.placeBlock(this,block,tbt,x,y);
          ts.item.count-=1;
          if(ts.item.count==0) ts.item=null;
          // }
        }
      }
        break;
      case destroy:
        if(progress>=block.type.destroyTime) {
          // player.pw.destroyBlock(player,block,x,y);
          pw.destroyBlock(this,block,x,y);
          progress=0;
        }
        break;
      // default:
      //   break;
    }
  }
  public void testStopTaskWithBlock(Block in) {
    if(in==block) stopTask();
  }
  public void stopTask() {
    task=idle;
    progress=0;
  }
}