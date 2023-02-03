package pama1234.gdx.game.state.state0001.game.player;

import com.badlogic.gdx.Input.Buttons;

import pama1234.gdx.game.state.state0001.game.item.Inventory.InventorySlot;
import pama1234.gdx.game.state.state0001.game.item.Inventory.InventorySlot.GetInventorySlot;
import pama1234.gdx.game.state.state0001.game.item.Item;
import pama1234.gdx.game.state.state0001.game.metainfo.MetaBlock;
import pama1234.gdx.game.state.state0001.game.metainfo.MetaItem;
import pama1234.gdx.game.state.state0001.game.region.block.Block;
import pama1234.gdx.game.state.state0001.game.world.World0001;

public class BlockPointer{
  public static final int idle=0,build=1,destroy=2,use=3;
  public World0001 pw;
  public GetInventorySlot slot;
  public Block block;
  public int x,y;
  public boolean active;
  public int task;
  public float progress;
  public BlockPointer(World0001 in) {
    pw=in;
  }
  public BlockPointer(World0001 in,GetInventorySlot slot) {
    pw=in;
    this.slot=slot;
  }
  public void pos(int xIn,int yIn) {
    x=xIn;
    y=yIn;
  }
  public void update(Block in,int x,int y) {
    if(task==use) return;
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
        if(block.type.workStation) startTask(use);
        else startTask(build);
      }
        break;
    }
  }
  public void updateTask() {
    progress+=getSpeed(slot.get().item,block);
    // System.out.println(getSpeed(slot.get().item,block));
    testTaskComplete();
  }
  public static float getSpeed(Item item,Block block) {
    if(item==null||block==null) return 1;
    MetaItem mi=item.type;
    MetaBlock mb=block.type;
    int itemType=mi.toolType;
    int blockType=mb.blockType;
    // System.out.println(itemType+" "+blockType);
    if(itemType==MetaItem.notTool) return 1;
    else if(itemType==MetaItem.allTool) return blockType==MetaBlock.noType?1:mi.speed;
    else if(itemType!=MetaItem.chisel) return itemType==blockType?mi.speed:1;
    else return 1;
  }
  public void testTaskComplete() {
    switch(task) {
      case build: {
        InventorySlot ts=slot();
        if(ts.item==null) break;
        MetaBlock tbt=ts.item.type.blockType;
        if(tbt==null||block==null||block.type==tbt) progress=0;
        else if(progress>=tbt.buildTime+block.type.destroyTime) {
          progress=0;
          pw.placeBlock(this,block,tbt,x,y);
          ts.item.count-=1;
          if(ts.item.count==0) ts.item=null;
        }
      }
        break;
      case destroy: {
        if(progress>=block.type.destroyTime) {
          pw.destroyBlock(this,block,x,y);
          progress=0;
        }
      }
        break;
    }
  }
  public void testStopTaskWithBlock(Block in) {
    if(in==block) stopTask();
  }
  public void stopTask() {
    if(task==use) return;
    task=idle;
    progress=0;
  }
}