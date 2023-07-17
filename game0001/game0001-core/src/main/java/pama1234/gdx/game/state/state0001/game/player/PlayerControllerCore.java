package pama1234.gdx.game.state.state0001.game.player;

import pama1234.gdx.game.app.Screen0011;
import pama1234.gdx.game.state.state0001.game.entity.entity0001.DroppedItem;
import pama1234.gdx.game.state.state0001.game.entity.util.MovementLimitBox;
import pama1234.gdx.util.entity.Entity;
import pama1234.math.Tools;
import pama1234.math.UtilMath;

public class PlayerControllerCore extends Entity<Screen0011>{
  public Player corePlayer;
  public MovementLimitBox limitBox;
  public float speed=1f,shiftSpeedMult=2f;
  public float slowDownSpeed=1/4f;
  public float jumpForceMult=1.5f,jumpHeight=0;
  public boolean walking,walkingStateChange;
  public boolean pInAir;
  public boolean left,right,jump,shift,jumpDown;
  public int walkCool,jumpCool;
  public float itemPickDist=18,itemPickMoveDist=72;
  public EntityPointer selectEntity;
  public BlockPointer coreSelectBlock;
  public PlayerControllerCore(Screen0011 p,Player corePlayer,boolean mainPlayer) {
    super(p);
    this.corePlayer=corePlayer;
    corePlayer.outerBox=limitBox=new MovementLimitBox(corePlayer);
    limitBox.usePlatform=true;
    selectEntity=new EntityPointer(corePlayer.pw,()->corePlayer.inventory.selectSlot().data);
    if(!mainPlayer) coreSelectBlock=new BlockPointer(corePlayer.pw,()->corePlayer.inventory.selectSlot().data);
  }
  public boolean testWalking() {
    boolean tb=left!=right;
    if(walking!=tb) {
      walking=tb;
      return true;
    }
    return false;
  }
  public void preUpdate() {
    walkingStateChange=testWalking();
    limitBox.preCtrlUpdate();
    doWalkAndJump();
    if(walkingStateChange) displayStateChange();
    if(pInAir!=limitBox.inAir) {
      pInAir=limitBox.inAir;
      displayStateChange();
    }
  }
  public void displayStateChange() {
    float speedMult=shift?shiftSpeedMult:1;
    if(limitBox.inAir) {
      corePlayer.timeStep=-1;
      corePlayer.frameTime=0;
      corePlayer.displayState=2;
    }else if(walking) {
      corePlayer.timeStep=-1;
      corePlayer.frameTime=0;
      corePlayer.displayState=1;
    }else {
      corePlayer.timeStep=(1/2f)/speedMult;
      corePlayer.frameTime=0;
      corePlayer.displayState=0;
    }
    corePlayer.testFrameTime();
  }
  public void postUpdate() {
    updatePickItem();
    float ty=corePlayer.point.vel.y;
    if(corePlayer.displayState==1) {
      corePlayer.timeStep=1/UtilMath.abs(corePlayer.point.vel.x);
    }else if(corePlayer.displayState==2) {
      if(ty<0) corePlayer.frameTime=0;
      else if(ty<8) corePlayer.frameTime=1;
      else corePlayer.frameTime=2;
    }
    coreSelectBlock.origin(corePlayer.xToBlockCordFloat(corePlayer.cx()),corePlayer.yToBlockCordFloat(corePlayer.cy()));
  }
  public void selectHotSlot(int in) {
    corePlayer.inventory.selectSlot(in);
  }
  public boolean inPlayerOuterBox(int tx,int ty) {
    return Tools.inBoxInclude(tx,ty,limitBox.x1,limitBox.y1,limitBox.w,limitBox.h);
  }
  public void shift(boolean in) {
    shift=in;
    float speedMult=shift?shiftSpeedMult:1;
    if(walking) corePlayer.timeStep=(1/8f)/speedMult;
    else corePlayer.timeStep=(1/2f)/speedMult;
  }
  public void updatePickItem() {
    for(DroppedItem e:corePlayer.pw.entities.items.list) {
      float td=UtilMath.dist(corePlayer.x(),corePlayer.y(),e.x(),e.y());
      if(e.owner==corePlayer) continue;
      if(td<itemPickDist) {
        corePlayer.inventory.accept(e.data);
        corePlayer.pw.entities.items.remove.add(e);
      }else if(td<itemPickMoveDist) {
        e.point.vel.set((corePlayer.x()-e.x())*p.random(0.1f,0.2f),(corePlayer.y()-e.y())*p.random(0.1f,0.2f));
      }
    }
  }
  public void doWalkAndJump() {
    if(walkCool>0) {
      walkCool--;
      walkSlowDown();
    }else if(walking) {
      float speedMult=shift?shiftSpeedMult:1;
      if(left) {
        corePlayer.point.vel.x-=speed*speedMult;
        corePlayer.flipX=true;
      }else {
        corePlayer.point.vel.x+=speed*speedMult;
        corePlayer.flipX=false;
      }
    }else walkSlowDown();
    // limitBox.testInAir();
    limitBox.usePlatform=!jumpDown;
    if(limitBox.inAir) {
      if(jump&&jumpHeight<-corePlayer.pw.settings.jumpForce*jumpForceMult) {
        corePlayer.point.vel.y-=corePlayer.pw.settings.g*2;
        jumpHeight+=corePlayer.pw.settings.g*2;
      }else corePlayer.point.vel.y+=corePlayer.pw.settings.g;
    }else {
      if(corePlayer.point.pos.y!=limitBox.floor) {
        corePlayer.point.vel.y=0;
        corePlayer.point.pos.y=limitBox.floor;
      }
      if(jumpCool>0) jumpCool--;
      else {
        if(jump) {
          corePlayer.point.vel.y=corePlayer.pw.settings.jumpForce*jumpForceMult*.5f;
          jumpCool=2;
          jumpHeight=-corePlayer.point.vel.y;
        }
      }
    }
  }
  public void walkSlowDown() {
    corePlayer.point.vel.x*=slowDownSpeed;
  }
}