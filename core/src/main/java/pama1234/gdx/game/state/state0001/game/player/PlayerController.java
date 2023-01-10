package pama1234.gdx.game.state.state0001.game.player;

import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.Input.Keys;

import pama1234.gdx.game.app.Screen0011;
import pama1234.gdx.game.asset.ImageAsset;
import pama1234.gdx.game.state.state0001.game.entity.GamePointEntity;
import pama1234.gdx.game.state.state0001.game.entity.LivingEntity;
import pama1234.gdx.game.state.state0001.game.entity.entity0001.DroppedItem;
import pama1234.gdx.game.state.state0001.game.entity.util.MovementLimitBox;
import pama1234.gdx.game.state.state0001.game.item.Inventory;
import pama1234.gdx.game.state.state0001.game.item.Inventory.DisplaySlot;
import pama1234.gdx.game.state.state0001.game.item.Inventory.InventorySlot;
import pama1234.gdx.game.state.state0001.game.item.Item;
import pama1234.gdx.game.state.state0001.game.metainfo.MetaBlock;
import pama1234.gdx.game.state.state0001.game.region.block.Block;
import pama1234.gdx.game.state.state0001.game.world.World0001;
import pama1234.gdx.game.util.RectF;
import pama1234.gdx.util.entity.Entity;
import pama1234.gdx.util.info.TouchInfo;
import pama1234.gdx.util.wrapper.EntityCenter;
import pama1234.math.Tools;
import pama1234.math.UtilMath;

public class PlayerController extends Entity<Screen0011>{
  public MainPlayer player;
  public boolean walking;
  public boolean left,right,jump,shift;
  public int walkCool,jumpCool;
  public float speed=1f,shiftSpeedMult=2f;
  public float slowDownSpeed=1/4f;
  public float jumpForceMult=1.5f;
  public MovementLimitBox limitBox;
  public RectF[] cullRects;
  public LivingEntity selectEntity;
  public BlockPointer selectBlock;
  public float camScale=2;
  public float itemPickDist=18,itemPickMoveDist=72;
  public PlayerController(Screen0011 p,MainPlayer player) {
    super(p);
    this.player=player;
    if(p.isAndroid) {
      cullRects=new RectF[] {
        new RectF(()->p.bu*1.5f,()->p.height-p.bu*1.5f-p.pus,()->p.bu*2.75f+p.pus*3,()->p.bu+p.pus),
        // new RectF(()->p.width-p.bu*4f,()->p.height-p.bu*2.5f-p.pus,()->p.pu*3.75f+p.pus,()->p.bu*2+p.pus),
        new RectF(()->p.width-p.bu*4f,()->p.height-p.bu*1.5f-p.pus,()->p.bu*2.5f+p.pus,()->p.bu+p.pus),
        new RectF(()->p.width-p.bu*2.5f,()->p.height-p.bu*2.5f-p.pus,()->p.bu+p.pus,()->p.bu+p.pus),
      };
    }else {
      cullRects=new RectF[0];
    }
    limitBox=new MovementLimitBox(player);
    selectBlock=new BlockPointer(player.pw);
    selectBlock.slot=()->player.inventory.select().data;
    // player.outerBox=limitBox;//TODO
  }
  @Override
  public void display() {
    p.beginBlend();
    if(selectEntity!=null) drawSelectEntity();
    drawSelectBlock();
    p.endBlend();
  }
  public void drawSelectEntity() {
    // p.beginBlend();
    float tl=UtilMath.mag(selectEntity.type.w,selectEntity.type.h)/2f+2;
    float tcx=selectEntity.cx(),tcy=selectEntity.cy();
    p.tint(255,191);
    p.image(ImageAsset.select,tcx-tl,tcy-tl,tl,tl);
    p.image(ImageAsset.select,tcx+tl,tcy-tl,-tl,tl);
    p.image(ImageAsset.select,tcx-tl,tcy+tl,tl,-tl);
    p.image(ImageAsset.select,tcx+tl,tcy+tl,-tl,-tl);
    p.noTint();
    // p.endBlend();
  }
  public void drawSelectBlock() {
    // p.beginBlend();
    // p.fill(0,127);
    int tw=player.pw.blockWidth,
      th=player.pw.blockHeight;
    switch(selectBlock.task) {
      case BlockPointer.idle: {
        // p.fill(0,127);
        p.fill(0,127);
        // p.rect(selectBlock.x*tw,selectBlock.y*th,tw,th);
        float r=1;
        float tx1=selectBlock.x*tw-r;
        float ty1=selectBlock.y*th-r;
        float tx2=(selectBlock.x+1)*tw+r;
        float ty2=(selectBlock.y+1)*th+r;
        float tw1=tw+r*2;
        float th1=th+r*2;
        p.rect(tx1,ty1,1,th1);
        p.rect(tx1,ty1,tw1,1);
        p.rect(tx2-1,ty1,1,th1);
        p.rect(tx1,ty2-1,tw1,1);
      }
        break;
      case BlockPointer.destroy: {
        p.tint(255,191);
        // p.println(selectBlock.progress,0,selectBlock.block.type.destroyTime,0,7,UtilMath.map(selectBlock.progress,0,selectBlock.block.type.destroyTime,0,7));
        p.image(ImageAsset.tiles[20][(int)UtilMath.map(selectBlock.progress,0,selectBlock.block.type.destroyTime,0,7)],selectBlock.x*tw,selectBlock.y*th);
      }
        break;
      case BlockPointer.build: {
        Item ti=selectBlock.slot().item;
        if(ti!=null) {
          p.tint(255,191);
          // p.println(selectBlock.progress,0,selectBlock.slot().item.type.blockType.buildTime,0,7,UtilMath.map(selectBlock.progress,0,selectBlock.slot().item.type.blockType.buildTime,0,7));
          p.image(ImageAsset.tiles[21][(int)UtilMath.map(selectBlock.progress,0,ti.type.blockType.buildTime,0,7)],selectBlock.x*tw,selectBlock.y*th);
        }
      }
      default:
        break;
    }
    // if(selectBlock.task!=BlockPointer.idle)
    p.noTint();
    // p.endBlend();
  }
  public void updateCtrlInfo() {
    updateKeyInfo();
    boolean tb=left!=right;
    if(walking!=tb) {
      walking=tb;
      walkEvent();
    }
    if(!player.pw.p.isAndroid) {
      if(testPosInButtons(p.mouse.x,p.mouse.y)) return;
      if(testPosInInventorySlot(p.mouse.x,p.mouse.y)) return;
      int tx=player.xToBlockCord(p.mouse.x),
        ty=player.xToBlockCord(p.mouse.y);
      // if(inPlayerOuterBox(tx,ty)) {
      //   // if(Tools.inRangeInclude(tx,limitBox.x1,limitBox.x2))
      //   tx=p.mouse.x<player.cx()?limitBox.x1-1:limitBox.x2+1;
      //   // if(Tools.inRangeInclude(ty,limitBox.y1,limitBox.y2))
      //   ty=p.mouse.y<player.cy()?limitBox.y1-1:limitBox.y2+1;
      //   Block block=player.getBlock(tx,ty);
      //   selectBlock.update(block,tx,ty);
      //   // return;
      // }
      if(testInPlayerOuterBoxAndUpdateSelectBlock(p.mouse.x,p.mouse.y,tx,ty)) return;
      Block block=player.getBlock(tx,ty);
      selectBlock.update(block,tx,ty);
    }
  }
  public void updateKeyInfo() {
    left=p.isKeyPressed(29)||p.isKeyPressed(21);
    right=p.isKeyPressed(32)||p.isKeyPressed(22);
    jump=p.isKeyPressed(62);
  }
  @Override
  public void touchStarted(TouchInfo info) {
    if(info.state!=0) return;
    if(testPosInButtons(info.x,info.y)) return;
    //---
    int tx=player.xToBlockCord(info.x),
      ty=player.xToBlockCord(info.y);
    if(updateAndTestInventorySlot(info.x,info.y,info.button)) return;
    if(p.isAndroid&&inPlayerOuterBox(tx,ty)) {
      player.inventory.displayStateChange();
      return;
    }
    if(updateAndTestSelectEntity(tx,ty)) return;
    Block block=player.getBlock(tx,ty);
    selectBlock.update(block,tx,ty);
    selectBlock.startTaskButtonInfo(info.button);
  }
  public void touchUpdate(TouchInfo info) {
    if(!player.pw.p.isAndroid) return;
    if(info.state!=0) return;
    if(testPosInButtons(info.x,info.y)) return;
    if(testPosInInventorySlot(info.x,info.y)) return;
    //---
    int tx=player.xToBlockCord(info.x),
      ty=player.xToBlockCord(info.y);
    // if(inPlayerOuterBox(tx,ty)) {
    //   if(Tools.inRangeInclude(tx,limitBox.x1,limitBox.x2)) tx=p.mouse.x<player.cx()?limitBox.x1-1:limitBox.x2+1;
    //   if(Tools.inRangeInclude(ty,limitBox.y1,limitBox.y2)) ty=p.mouse.y<player.cy()?limitBox.y1-1:limitBox.y2+1;
    //   Block block=player.getBlock(tx,ty);
    //   selectBlock.update(block,tx,ty);
    //   return;
    // }
    if(testInPlayerOuterBoxAndUpdateSelectBlock(info.x,info.y,tx,ty)) return;
    Block block=player.getBlock(tx,ty);
    selectBlock.update(block,tx,ty);
    if(player.gameMode!=GameMode.creative) return;
    if(testPosInOtherEntity(tx,ty)) return;
    // if(player.gameMode==GameMode.creative)
    creativeModeUpdateSelectBlock(info,tx,ty,block);
  }
  @Override
  public void touchEnded(TouchInfo info) {
    // if(selectBlock.task)
    int tx=player.xToBlockCord(info.x),
      ty=player.xToBlockCord(info.y);
    if(inPlayerOuterBox(tx,ty)) {
      // if(Tools.inRangeInclude(tx,limitBox.x1,limitBox.x2))
      tx=info.x<player.cx()?limitBox.x1-1:limitBox.x2+1;
      // if(Tools.inRangeInclude(ty,limitBox.y1,limitBox.y2))
      ty=info.y<player.cy()?limitBox.y1-1:limitBox.y2+1;
      Block block=player.getBlock(tx,ty);
      selectBlock.testStopTaskWithBlock(block);
      return;
    }
    Block block=player.getBlock(tx,ty);
    // selectBlock.update(block,tx,ty);
    selectBlock.testStopTaskWithBlock(block);
  }
  public boolean testInPlayerOuterBoxAndUpdateSelectBlock(float x,float y,int tx,int ty) {
    if(inPlayerOuterBox(tx,ty)) {
      float tp1=UtilMath.abs(limitBox.w/(float)limitBox.h);
      float tp2=UtilMath.abs((x-player.cx())/(y-player.cy()));
      // p.println(limitBox.w,limitBox.h,tp1,x,y,tp2);
      // if(Tools.inRangeInclude(tx,limitBox.x1,limitBox.x2))
      if(tp2>=tp1) tx=x<player.cx()?limitBox.x1-1:limitBox.x2+1;
      // if(Tools.inRangeInclude(ty,limitBox.y1,limitBox.y2))
      else ty=y<player.cy()?limitBox.y1-1:limitBox.y2+1;
      Block block=player.getBlock(tx,ty);
      selectBlock.update(block,tx,ty);
      return true;
    }
    return false;
  }
  public boolean updateAndTestSelectEntity(int tx,int ty) {
    for(EntityCenter<Screen0011,? extends GamePointEntity<?>> l:player.pw.entities.list) {
      if(l==player.pw.entities.items) continue;
      for(GamePointEntity<?> e:l.list) {
        if(e instanceof LivingEntity live) {
          if(live.inOuterBox(tx,ty)) {
            selectEntity=live;
            return true;
          }
        }
      }
    }
    selectEntity=null;
    return false;
  }
  public boolean updateAndTestInventorySlot(float x,float y,int button) {
    if(player.inventory.displayState==Inventory.displayFullInventory) for(int i=0;i<player.inventory.hotSlots.length;i++) {
      DisplaySlot e=player.inventory.hotSlots[i];
      if(Tools.inBox(x,y,e.x1,e.y1,e.w1,e.h1)) {
        switch(p.isAndroid?(player.pw.pg.androidRightMouseButton?Buttons.RIGHT:Buttons.LEFT):button) {
          case Buttons.LEFT: {
            // player.inventory.selectSlot=i;
            selectSlot(i);
          }
            break;
          case Buttons.RIGHT: {
            // player.inventory.select(i);
            player.inventory.switchHold(e);
          }
            break;
        }
        return true;
      }
    }
    return false;
  }
  public void creativeModeUpdateSelectBlock(TouchInfo info,int tx,int ty,Block block) {
    if(block!=null) switch(p.isAndroid?(player.pw.pg.androidRightMouseButton?Buttons.RIGHT:Buttons.LEFT):info.button) {
      case Buttons.LEFT: {
        if(block.type!=player.pw.metaBlocks.air) player.pw.destroyBlock(player,block,tx,ty);
      }
        break;
      case Buttons.RIGHT: {
        InventorySlot td=player.inventory.select().data;
        Item ti=td.item;
        if(ti!=null) {
          MetaBlock tb=ti.type.blockType;
          // if(tm!=null&&block.type==player.pw.metaBlocks.air) {
          if(tb!=null&&block.type!=tb) {
            player.pw.placeBlock(player,block,tb,tx,ty);
            // if(player.gameMode!=GameMode.creative) {
            //   ti.count-=1;
            //   if(ti.count==0) td.item=null;
            // }
          }
        }
      }
        break;
    }
  }
  public boolean testPosInInventorySlot(float x,float y) {
    if(player.inventory.displayState==Inventory.displayFullInventory) for(DisplaySlot e:player.inventory.hotSlots) if(Tools.inBox(x,y,e.x1,e.y1,e.w1,e.h1)) return true;
    return false;
  }
  public boolean testPosInOtherEntity(int x,int y) {
    for(EntityCenter<Screen0011,? extends GamePointEntity<?>> l:player.pw.entities.list) {
      if(l==player.pw.entities.items) continue;
      for(GamePointEntity<?> e:l.list) if(e instanceof LivingEntity live) if(live.inOuterBox(x,y)) return true;
    }
    return false;
  }
  public boolean testPosInButtons(float x,float y) {
    for(RectF e:cullRects) if(Tools.inBox(x,y,e.x(),e.y(),e.w(),e.h())) return true;
    return false;
  }
  public boolean inPlayerOuterBox(int tx,int ty) {
    return Tools.inBoxInclude(tx,ty,limitBox.x1,limitBox.y1,limitBox.w,limitBox.h);
    // return player.inOuterBox(tx,ty);
  }
  @Override
  public void mouseWheel(float x,float y) {
    // p.println(x,y);
    selectSlot(player.inventory.selectSlot+(int)y);
    // player.inventory.selectSlot+=y;
    player.inventory.testSelectSlot();
  }
  public void selectSlot(int in) {
    player.inventory.select(in);
    // selectBlock.slot=player.inventory.select().data;
  }
  @Override
  public void keyPressed(char key,int keyCode) {
    if(keyCode==Keys.SHIFT_LEFT||keyCode==Keys.SHIFT_RIGHT) shift(true);
    else if(keyCode==Keys.E) player.inventory.displayStateChange();
    else if(keyCode==Keys.V) shift(!shift);
    else if(keyCode==Keys.EQUALS) camScale(0.5f);
    else if(keyCode==Keys.MINUS) camScale(-0.5f);
  }
  @Override
  public void keyReleased(char key,int keyCode) {
    if(keyCode==Keys.SHIFT_LEFT||keyCode==Keys.SHIFT_RIGHT) shift(false);
  }
  public void camScale(float in) {
    p.cam2d.scaleAdd(in);
    camScale=p.cam2d.scale.des;
  }
  public void shift(boolean in) {
    shift=in;
    float speedMult=shift?shiftSpeedMult:1;
    if(walking) player.timeStep=(1/8f)/speedMult;
    else player.timeStep=(1/2f)/speedMult;
  }
  public void doWalkAndJump() {
    if(walkCool>0) {
      walkCool--;
      walkSlowDown();
    }else if(walking) {
      float speedMult=shift?shiftSpeedMult:1;
      if(left) {
        player.point.vel.x-=speed*speedMult;
        player.dir=true;
      }else {
        player.point.vel.x+=speed*speedMult;
        player.dir=false;
      }
    }else walkSlowDown();
    // inAir=player.point.pos.y<floor;
    limitBox.doInAirTest();
    if(limitBox.inAir) player.point.vel.y+=player.pw.g;
    else {
      if(player.point.pos.y!=limitBox.floor) {
        player.point.vel.y=0;
        player.point.pos.y=limitBox.floor;
      }
      if(jumpCool>0) jumpCool--;
      else if(jump) {
        player.point.vel.y=player.pw.jumpForce*jumpForceMult;
        jumpCool=2;
      }
    }
  }
  public void walkSlowDown() {
    player.point.vel.x*=slowDownSpeed;
  }
  public void walkEvent() {
    float speedMult=shift?shiftSpeedMult:1;
    if(walking) {
      player.timeStep=(1/8f)/speedMult;
      player.frameTime=0;
      player.moveState=1;
    }else {
      player.timeStep=(1/2f)/speedMult;
      player.frameTime=0;
      player.moveState=0;
    }
    player.testFrameTime();
  }
  public void constrain() {
    limitBox.constrain();
  }
  public void updateOuterBox() {
    limitBox.update();
    limitBox.updateLimit();
  }
  public void updatePickItem() {
    for(DroppedItem e:player.pw.entities.items.list) {
      float td=UtilMath.dist(player.x(),player.y(),e.x(),e.y());
      if(td<itemPickDist) {
        player.inventory.accept(e.data);
        player.pw.entities.items.remove.add(e);
      }else if(td<itemPickMoveDist) {
        e.point.vel.set((player.x()-e.x())*p.random(0.1f,0.2f),(player.y()-e.y())*p.random(0.1f,0.2f));
      }
    }
  }
  @FunctionalInterface
  public interface GetInventorySlot{
    public InventorySlot get();
  }
  public static class BlockPointer{
    public static final int idle=0,build=1,destroy=2;
    public World0001 pw;
    // public InventorySlot slot;
    public GetInventorySlot slot;
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
          if(block.type==tbt) progress=0;
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
}