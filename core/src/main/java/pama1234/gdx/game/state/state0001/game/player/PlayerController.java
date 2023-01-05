package pama1234.gdx.game.state.state0001.game.player;

import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.Input.Keys;

import pama1234.gdx.game.app.Screen0011;
import pama1234.gdx.game.asset.ImageAsset;
import pama1234.gdx.game.state.state0001.game.entity.GamePointEntity;
import pama1234.gdx.game.state.state0001.game.entity.LivingEntity;
import pama1234.gdx.game.state.state0001.game.entity.entity0001.DroppedItem;
import pama1234.gdx.game.state.state0001.game.entity.util.MovementLimitBox;
import pama1234.gdx.game.state.state0001.game.item.Item;
import pama1234.gdx.game.state.state0001.game.item.Inventory;
import pama1234.gdx.game.state.state0001.game.item.Inventory.DisplaySlot;
import pama1234.gdx.game.state.state0001.game.item.Inventory.InventorySlot;
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
  public LivingEntity selectEntity;
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
    // player.outerBox=limitBox;//TODO
  }
  @Override
  public void display() {
    if(selectEntity!=null) {
      p.beginBlend();
      float tl=UtilMath.mag(selectEntity.type.w,selectEntity.type.h)/2f+2;
      float tcx=selectEntity.cx(),tcy=selectEntity.cy();
      p.image(ImageAsset.select,tcx-tl,tcy-tl,tl,tl);
      p.image(ImageAsset.select,tcx+tl,tcy-tl,-tl,tl);
      p.image(ImageAsset.select,tcx-tl,tcy+tl,tl,-tl);
      p.image(ImageAsset.select,tcx+tl,tcy+tl,-tl,-tl);
      p.endBlend();
    }
  }
  @Override
  public void touchStarted(TouchInfo info) {
    if(info.state!=0) return;
    for(RectF e:cullRects) if(Tools.inBox(info.ox,info.oy,e.x(),e.y(),e.w(),e.h())) return;
    int tx=player.xToBlockCord(info.x),
      ty=player.xToBlockCord(info.y);
    //--------------------------------------------------------------------------------------------------------------------------------
    if(p.isAndroid&&inPlayerOuterBox(tx,ty)) player.inventory.displayStateChange();
    if(player.inventory.displayState==Inventory.displayFullInventory) for(int i=0;i<player.inventory.hotSlots.length;i++) {
      DisplaySlot e=player.inventory.hotSlots[i];
      if(Tools.inBox(info.x,info.y,e.x1,e.y1,e.w1,e.h1)) {
        switch(p.isAndroid?(player.pw.pg.androidRightMouseButton?Buttons.RIGHT:Buttons.LEFT):info.button) {
          case Buttons.LEFT: {
            // player.inventory.selectSlot=i;
            player.inventory.select(i);
          }
            break;
          case Buttons.RIGHT: {
            // player.inventory.select(i);
            player.inventory.switchHold(e);
          }
            break;
        }
        return;
      }
    }
    //--------------------------------------------------------------------------------------------------------------------------------
    for(EntityCenter<Screen0011,? extends GamePointEntity<?>> l:player.pw.entities.list) {
      if(l==player.pw.entities.items) continue;
      for(GamePointEntity<?> e:l.list) {
        if(e instanceof LivingEntity live) {
          if(live.inOuterBox(tx,ty)) {
            selectEntity=live;
            return;
          }
        }
      }
    }
    selectEntity=null;
  }
  public void touchUpdate(TouchInfo info) {
    if(info.state!=0) return;
    for(RectF e:cullRects) if(Tools.inBox(info.ox,info.oy,e.x(),e.y(),e.w(),e.h())) return;
    int tx=player.xToBlockCord(info.x),
      ty=player.xToBlockCord(info.y);
    // p.println(tx,ty,bx1,by1,bw,bh,Tools.inBox(tx,ty,bx1,by1,bw,bh));
    if(inPlayerOuterBox(tx,ty)) return;
    if(player.inventory.displayState==Inventory.displayFullInventory) for(DisplaySlot e:player.inventory.hotSlots) if(Tools.inBox(info.x,info.y,e.x1,e.y1,e.w1,e.h1)) return;
    Block block=player.getBlock(tx,ty);
    for(EntityCenter<Screen0011,? extends GamePointEntity<?>> l:player.pw.entities.list) {
      if(l==player.pw.entities.items) continue;
      for(GamePointEntity<?> e:l.list) if(e instanceof LivingEntity live) if(live.inOuterBox(tx,ty)) return;
    }
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
            if(player.gameMode!=GameMode.creative) {
              ti.count-=1;
              if(ti.count==0) td.item=null;
            }
          }
        }
      }
        break;
    }
  }
  public boolean inPlayerOuterBox(int tx,int ty) {
    return Tools.inBoxInclude(tx,ty,limitBox.x1,limitBox.y1,limitBox.w,limitBox.h);
    // return player.inOuterBox(tx,ty);
  }
  @Override
  public void mouseWheel(float x,float y) {
    // p.println(x,y);
    player.inventory.selectSlot+=y;
    player.inventory.testSelectSlot();
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
  public void updateCtrlInfo() {
    left=p.isKeyPressed(29)||p.isKeyPressed(21);
    right=p.isKeyPressed(32)||p.isKeyPressed(22);
    jump=p.isKeyPressed(62);
    boolean tb=left!=right;
    if(walking!=tb) {
      walking=tb;
      walkEvent();
    }
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
}