package pama1234.gdx.game.state.state0001.game.player;

import com.badlogic.gdx.Input.Buttons;

import pama1234.gdx.game.state.state0001.game.item.Item;
import pama1234.gdx.game.state.state0001.game.item.Item.ItemSlot;
import pama1234.gdx.game.state.state0001.game.item.Item.ItemSlot.GetItemSlot;
import pama1234.gdx.game.state.state0001.game.metainfo.MetaBlock;
import pama1234.gdx.game.state.state0001.game.metainfo.MetaItem;
import pama1234.gdx.game.state.state0001.game.region.block.Block;
import pama1234.gdx.game.state.state0001.game.world.WorldBase2D;
import pama1234.gdx.game.state.state0001.game.world.world0001.WorldType0001Base;
import pama1234.math.UtilMath;

public class BlockPointer extends PointerBase{
  public static final int idle=0,build=1,destroy=2,use=3;
  public Block block;
  public float ox,oy;
  public float maxDist=6;
  public int x,y;
  public float progress;
  public BlockPointer(WorldBase2D<? extends WorldType0001Base<?>> in) {
    super(in);
  }
  public BlockPointer(WorldBase2D<? extends WorldType0001Base<?>> in,GetItemSlot slot) {
    super(in,slot);
  }
  public boolean isInRange(int xIn,int yIn) {
    return dist(xIn,yIn)<maxDist;
  }
  public float dist(int xIn,int yIn) {
    return UtilMath.dist(ox,oy,xIn+0.5f,yIn+0.5f);
  }
  public void origin(float xIn,float yIn) {
    ox=xIn;
    oy=yIn;
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
  @Override
  public void updateTask() {
    progress+=getSpeed(slot.get().item,block);
    // System.out.println(getSpeed(slot.get().item,block));
    testTaskComplete();
  }
  public static float getSpeed(Item item,Block block) {
    if(item==null||block==null) return 1;
    MetaItem mi=item.type;
    MetaBlock<?,?> mb=block.type;
    int itemType=mi.toolType;
    int blockType=mb.blockType;
    // System.out.println(itemType+" "+blockType);
    if(itemType==MetaItem.notTool) return 1;
    else if(itemType==MetaItem.allTool) return blockType==MetaBlock.noType?1:mi.speed;
    else if(itemType!=MetaItem.chisel) return itemType==blockType?mi.speed:1;
    else return 1;
  }
  @Override
  public void testTaskComplete() {
    switch(task) {
      case build: {
        ItemSlot ts=slot();
        if(ts.item==null) break;
        MetaBlock<?,?> tbt=ts.item.type.blockType;
        if(tbt==null||block==null||block.type==tbt) taskComplete();
        else if(progress>=tbt.buildTime+block.type.destroyTime) {
          pw.r.placeBlock(this,block,tbt,x,y);
          ts.item.count-=1;
          if(ts.item.count==0) ts.item=null;
          taskComplete();
        }
      }
        break;
      case destroy: {
        if(progress>=block.type.destroyTime) {
          if(!block.type.empty) pw.r.destroyBlock(this,block,x,y);
          taskComplete();
        }
      }
        break;
    }
  }
  public void taskComplete() {
    progress=0;
  }
  public void testStopTaskWithBlock(Block in) {
    if(in==block) stopTask();
  }
  @Override
  public void stopTask() {
    if(task==use) return;
    task=idle;
    progress=0;
  }
}