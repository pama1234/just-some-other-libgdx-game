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
  public EntityPointer selectEntity;
  public ControllerBlockPointer selectBlock;
  // public BlockPointer workStationPointer;
  public float camScale=2;
  public float itemPickDist=18,itemPickMoveDist=72;
  public PlayerController(Screen0011 p,MainPlayer player) {
    super(p);
    this.player=player;
    if(p.isAndroid) {
      cullRects=new RectF[] {
        new RectF(()->p.bu*1.5f-p.pus,()->p.height-p.bu*1.5f-p.pus,()->p.bu*2.75f+p.pus*4,()->p.bu+p.pus),
        // new RectF(()->p.width-p.bu*4f,()->p.height-p.bu*2.5f-p.pus,()->p.pu*3.75f+p.pus,()->p.bu*2+p.pus),
        new RectF(()->p.width-p.bu*4f-p.pus,()->p.height-p.bu*1.5f-p.pus,()->p.bu*2.5f+p.pus*2,()->p.bu+p.pus),
        new RectF(()->p.width-p.bu*2.5f-p.pus,()->p.height-p.bu*2.5f-p.pus,()->p.bu+p.pus*2,()->p.bu+p.pus),
      };
    }else cullRects=new RectF[0];
    limitBox=new MovementLimitBox(player);
    selectEntity=new EntityPointer(player.pw,()->player.inventory.select().data);
    selectBlock=new ControllerBlockPointer(new BlockPointer(player.pw,()->player.inventory.select().data));
    // workStationPointer=new BlockPointer(player.pw);
    // player.outerBox=limitBox;//TODO
  }
  @Override
  public void display() {
    // p.beginBlend();
    if(selectEntity.entity!=null) drawSelectEntity();
    if(selectBlock.data.active) drawSelectBlock();
    // if(workStationPointer.active) drawSelectBlock();
    // p.endBlend();
  }
  public void drawSelectEntity() {
    LivingEntity entity=selectEntity.entity;
    float tl=UtilMath.mag(entity.type.w,entity.type.h)/2f+2;
    float tcx=entity.cx(),tcy=entity.cy();
    p.tint(255,127);
    p.image(ImageAsset.select,tcx-tl,tcy-tl,tl,tl);
    p.image(ImageAsset.select,tcx+tl,tcy-tl,-tl,tl);
    p.image(ImageAsset.select,tcx-tl,tcy+tl,tl,-tl);
    p.image(ImageAsset.select,tcx+tl,tcy+tl,-tl,-tl);
    p.noTint();
  }
  public void drawSelectBlock() {
    int tw=player.pw.settings.blockWidth,
      th=player.pw.settings.blockHeight;
    switch(selectBlock.data.task) {
      case BlockPointer.idle: {
        p.fill(0,127);
        float r=1;
        float tx1=selectBlock.data.x*tw-r;
        float ty1=selectBlock.data.y*th-r;
        float tx2=(selectBlock.data.x+1)*tw+r;
        float ty2=(selectBlock.data.y+1)*th+r;
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
        p.image(ImageAsset.tiles[20][(int)UtilMath.map(selectBlock.data.progress,0,selectBlock.data.block.type.destroyTime,0,7)],selectBlock.data.x*tw,selectBlock.data.y*th);
      }
        break;
      case BlockPointer.build: {
        Item ti=selectBlock.data.slot().item;
        if(ti!=null&&ti.type.blockType!=null) {
          p.tint(255,191);
          p.image(
            ImageAsset.tiles[21][(int)UtilMath.map(selectBlock.data.progress,
              0,ti.type.blockType.buildTime+selectBlock.data.block.type.destroyTime,0,7)],
            selectBlock.data.x*tw,selectBlock.data.y*th);
        }
      }
      case BlockPointer.use: {//TODO
      }
      default:
        break;
    }
    p.noTint();
  }
  public void updateCtrlInfo() {
    updateKeyInfo();
    boolean tb=left!=right;
    if(walking!=tb) {
      walking=tb;
      walkEvent();
    }
    if(!player.pw.p.isAndroid) updateMouseInfo();
    selectEntity.updateTask();//TODO
  }
  public void updateMouseInfo() {
    selectBlock.data.active=false;
    if(testPosInButtons(p.mouse.ox,p.mouse.oy)) return;
    if(testPosInInventorySlot(p.mouse.x,p.mouse.y)) return;
    int tx=player.xToBlockCord(p.mouse.x),
      ty=player.xToBlockCord(p.mouse.y);
    if(inPlayerOuterBox(tx,ty)) return;
    // if(selectBlock.data.task==BlockPointer.hide) selectBlock.data.task=BlockPointer.idle;
    selectBlock.data.active=true;
    Block block=player.getBlock(tx,ty);
    selectBlock.data.update(block,tx,ty);
  }
  public void updateKeyInfo() {
    left=p.isKeyPressed(29)||p.isKeyPressed(21);
    right=p.isKeyPressed(32)||p.isKeyPressed(22);
    jump=p.isKeyPressed(62);
  }
  @Override
  public void touchStarted(TouchInfo info) {
    if(info.state!=0) return;
    if(testPosInButtons(info.ox,info.oy)) return;
    //---
    int tx=player.xToBlockCord(info.x),
      ty=player.xToBlockCord(info.y);
    if(updateAndTestInventorySlot(info.x,info.y,info.button)) return;
    if(p.isAndroid&&inPlayerOuterBox(tx,ty)) {
      player.inventory.displayStateChange();
      return;
    }
    if(updateAndTestSelectEntity(tx,ty,info.button)) return;
    Block block=player.getBlock(tx,ty);
    selectBlock.data.active=true;
    selectBlock.data.update(block,tx,ty);
    selectBlock.info(info);
    selectBlock.data.startTaskButtonInfo(getTouchInfoButton(info.button));
  }
  public void touchUpdate(TouchInfo info) {
    if(!player.pw.p.isAndroid) return;
    if(info.state!=0) return;
    if(testPosInButtons(info.ox,info.oy)) return;
    if(testPosInInventorySlot(info.x,info.y)) return;
    //---
    int tx=player.xToBlockCord(info.x),
      ty=player.xToBlockCord(info.y);
    if(inPlayerOuterBox(tx,ty)) return;
    Block block=player.getBlock(tx,ty);
    selectBlock.data.update(block,tx,ty);
    if(player.gameMode!=GameMode.creative) return;
    if(testPosInOtherEntity(tx,ty)) return;
    // if(player.gameMode==GameMode.creative)
    creativeModeUpdateSelectBlock(info,tx,ty,block);
  }
  @Override
  public void touchEnded(TouchInfo info) {
    if(p.isAndroid) selectBlock.data.active=false;
    selectBlock.testStopTask(info);
  }
  public boolean updateAndTestSelectEntity(int tx,int ty,int button) {
    for(EntityCenter<Screen0011,? extends GamePointEntity<?>> l:player.pw.entities.list) {
      if(l==player.pw.entities.items) continue;
      for(GamePointEntity<?> e:l.list) {
        if(e instanceof LivingEntity live) {
          if(live.inOuterBox(tx,ty)) {
            if(selectEntity.entity!=live) selectEntity.entity=live;
            else selectEntity.startTaskButtonInfo(button);
            return true;
          }
        }
      }
    }
    selectEntity.entity=null;
    return false;
  }
  public boolean updateAndTestInventorySlot(float x,float y,int button) {
    if(player.inventory.displayState==Inventory.displayFullInventory) {
      DisplaySlot[] hotSlots=player.inventory.hotSlots;
      for(int i=0;i<hotSlots.length;i++) if(testAndSelectSlot(x,y,button,hotSlots,i)) return true;
      hotSlots=player.inventory.backpackSlots;
      for(DisplaySlot e:hotSlots) if(testSlot(x,y,button,e)) return true;
    }
    return false;
  }
  public boolean testSlot(float x,float y,int button,DisplaySlot e) {
    if(Tools.inBox(x,y,e.x1,e.y1,e.w1,e.h1)) {
      switch(getTouchInfoButton(button)) {
        case Buttons.LEFT: {
          // selectHotSlot(i);
        }
          break;
        case Buttons.RIGHT: {
          player.inventory.switchHold(e);
        }
          break;
      }
      return true;
    }
    return false;
  }
  public boolean testAndSelectSlot(float x,float y,int button,DisplaySlot[] slots,int i) {
    DisplaySlot e=slots[i];
    if(Tools.inBox(x,y,e.x1,e.y1,e.w1,e.h1)) {
      switch(getTouchInfoButton(button)) {
        case Buttons.LEFT: {
          selectHotSlot(i);
        }
          break;
        case Buttons.RIGHT: {
          player.inventory.switchHold(e);
        }
          break;
      }
      return true;
    }
    return false;
  }
  public void creativeModeUpdateSelectBlock(TouchInfo info,int tx,int ty,Block block) {
    if(block!=null) switch(getTouchInfoButton(info.button)) {
      case Buttons.LEFT: {
        if(block.type!=player.pw.metaBlocks.air) player.pw.destroyBlock(player,block,tx,ty);
      }
        break;
      case Buttons.RIGHT: {
        InventorySlot td=player.inventory.select().data;
        Item ti=td.item;
        if(ti!=null) {
          MetaBlock tb=ti.type.blockType;
          if(tb!=null&&block.type!=tb) {
            player.pw.placeBlock(player,block,tb,tx,ty);
          }
        }
      }
        break;
    }
  }
  public int getTouchInfoButton(int in) {
    return p.isAndroid?(player.pw.pg.androidRightMouseButton?Buttons.RIGHT:Buttons.LEFT):in;
  }
  public boolean testPosInInventorySlot(float x,float y) {
    if(player.inventory.displayState==Inventory.displayFullInventory) for(DisplaySlot[] i:player.inventory.displaySlots) for(DisplaySlot e:i) if(Tools.inBox(x,y,e.x1,e.y1,e.w1,e.h1)) return true;
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
    // for(RectF e:cullRects) p.println(Tools.inBox(x,y,e.x(),e.y(),e.w(),e.h()),x,y,e.x(),e.y(),e.w(),e.h());
    for(RectF e:cullRects) if(Tools.inBox(x,y,e.x(),e.y(),e.w(),e.h())) return true;
    return false;
  }
  public boolean inPlayerOuterBox(int tx,int ty) {
    return Tools.inBoxInclude(tx,ty,limitBox.x1,limitBox.y1,limitBox.w,limitBox.h);
  }
  @Override
  public void mouseWheel(float x,float y) {
    selectHotSlot(player.inventory.selectSlot+(int)y);
    player.inventory.testSelectSlot();
  }
  public void selectHotSlot(int in) {
    player.inventory.select(in);
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
    limitBox.doInAirTest();
    // if(jump&&jumpForceMult<1.5f&&player.point.vel.y<0) {
    //   jumpForceMult+=.1f;
    //   player.point.vel.y-=jumpForceMult;
    // }else {
    //   player.point.vel.y+=player.pw.settings.g;
    //   jumpForceMult=.7f;
    // }
    if(limitBox.inAir) {
      if(jump) player.point.vel.y+=player.pw.settings.g;
      else player.point.vel.y+=player.pw.settings.g*2;
    }else {
      if(player.point.pos.y!=limitBox.floor) {
        player.point.vel.y=0;
        player.point.pos.y=limitBox.floor;
      }
      if(jumpCool>0) jumpCool--;
      else if(jump) {
        player.point.vel.y=player.pw.settings.jumpForce*jumpForceMult;
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
  public static class ControllerBlockPointer{
    public TouchInfo info;
    public BlockPointer data;
    public ControllerBlockPointer(BlockPointer in) {
      data=in;
    }
    public void info(TouchInfo in) {
      info=in;
    }
    public void testStopTask(TouchInfo in) {
      if(info==in) data.stopTask();
    }
  }
}