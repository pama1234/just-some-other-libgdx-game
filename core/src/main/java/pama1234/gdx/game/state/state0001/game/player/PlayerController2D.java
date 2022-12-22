package pama1234.gdx.game.state.state0001.game.player;

import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.Input.Keys;

import pama1234.gdx.game.app.Screen0011;
import pama1234.gdx.game.state.state0001.game.region.block.Block;
import pama1234.gdx.game.util.RectF;
import pama1234.gdx.util.entity.Entity;
import pama1234.gdx.util.info.TouchInfo;
import pama1234.math.Tools;

public class PlayerController2D extends Entity<Screen0011>{
  public MainPlayer2D player;
  public boolean left,right,jump,shift;
  public boolean inAir;
  public int walkCool,jumpCool;
  public float speed=1f,shiftSpeedMult=2f;
  public float floor,leftWall,rightWall,ceiling;
  public int bx1,by1,bx2,by2,bw,bh;
  public boolean flagCache;
  public RectF[] cullRects;
  public PlayerController2D(Screen0011 p,MainPlayer2D player) {
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
  }
  @Override
  public void touchStarted(TouchInfo info) {
    if(info.state!=0) return;
    for(RectF e:cullRects) if(Tools.inBox(info.ox,info.oy,e.x(),e.y(),e.w(),e.h())) return;
    int tx=player.xToBlockCord(info.x),
      ty=player.xToBlockCord(info.y);
    if(Tools.inBox(tx,ty,bx1,by1,bw+1,bh+1)) player.inventory.displayHotSlot=!player.inventory.displayHotSlot;
  }
  public void touchUpdate(TouchInfo info) {
    if(info.state!=0) return;
    for(RectF e:cullRects) if(Tools.inBox(info.ox,info.oy,e.x(),e.y(),e.w(),e.h())) return;
    int tx=player.xToBlockCord(info.x),
      ty=player.xToBlockCord(info.y);
    // p.println(tx,ty,bx1,by1,bw,bh,Tools.inBox(tx,ty,bx1,by1,bw,bh));
    if(Tools.inBox(tx,ty,bx1,by1,bw+1,bh+1)) return;
    Block block=player.getBlock(tx,ty);
    if(block!=null) switch(p.isAndroid?(player.pg.androidRightMouseButton?Buttons.RIGHT:Buttons.LEFT):info.button) {
      case Buttons.LEFT: {
        block.type(player.pw.blockC.air);
      }
        break;
      case Buttons.RIGHT: {
        block.type(player.pw.blockC.dirt);
      }
        break;
    }
  }
  @Override
  public void keyPressed(char key,int keyCode) {
    if(keyCode==Keys.SHIFT_LEFT||keyCode==Keys.SHIFT_RIGHT) shift=true;
  }
  @Override
  public void keyReleased(char key,int keyCode) {
    if(keyCode==Keys.SHIFT_LEFT||keyCode==Keys.SHIFT_RIGHT) shift=false;
  }
  public void doWalkAndJump() {
    if(walkCool>0) walkCool--;
    else if(!(left==right)) {
      float speedMult=shift?shiftSpeedMult:1;
      if(left) {
        player.point.pos.x-=speed*speedMult;
        player.dir=true;
      }else {
        player.point.pos.x+=speed*speedMult;
        player.dir=false;
      }
    }
    inAir=player.point.pos.y<floor;
    if(inAir) player.point.vel.y+=player.pw.g;
    else {
      if(player.point.pos.y!=floor) {
        player.point.vel.y=0;
        player.point.pos.y=floor;
      }
      if(jumpCool>0) jumpCool--;
      else if(jump) {
        player.point.vel.y=player.pw.jumpForce;
        jumpCool=2;
      }
    }
  }
  public void updateCtrlInfo() {
    left=p.isKeyPressed(29)||p.isKeyPressed(21);
    right=p.isKeyPressed(32)||p.isKeyPressed(22);
    jump=p.isKeyPressed(62);
  }
  public void constrain() {
    if(player.point.pos.y>floor) {
      player.point.vel.y=0;
      player.point.pos.y=floor;
    }
    if(player.point.pos.y<ceiling) {
      if(player.point.vel.y<0) player.point.vel.y=0;
      player.point.pos.y=ceiling;
    }
    if(player.point.pos.x<leftWall) player.point.pos.x=leftWall;
    if(player.point.pos.x>rightWall) player.point.pos.x=rightWall;
  }
  public void updateOuterBox() {
    bx1=player.blockX1();
    by1=player.blockY1();
    bx2=player.blockX2();
    by2=player.blockY2();
    bw=bx2-bx1;
    bh=by2-by1;
    // if(inAir) {
    //   by1-=1;
    //   bh+=1;
    // }
    // if(!inAir) bh-=1;
    if(inAir&&player.point.vel.y>0) bh+=1;
    Block block;
    flagCache=false;
    //------------------------------------------ floor
    for(int i=0;i<=bw;i++) {
      block=player.getBlock(bx1+i,by2+1);
      if(!Block.isEmpty(block)) {
        flagCache=true;
        break;
      }
    }
    if(flagCache) {
      floor=(by2+1)*player.pw.blockHeight;
      flagCache=false;
    }else floor=(by2+4)*player.pw.blockHeight;
    //------------------------------------------ left
    // for(int i=inAir?-1:0;i<=bh;i++) {
    for(int i=0;i<=bh;i++) {
      block=player.getBlock(bx1-1,by1+i);
      if(!Block.isEmpty(block)) {
        flagCache=true;
        break;
      }
    }
    if(flagCache) {
      leftWall=(bx1+0.5f)*player.pw.blockWidth+1;
      flagCache=false;
    }else leftWall=(bx1-4)*player.pw.blockWidth;
    //------------------------------------------ right
    for(int i=0;i<=bh;i++) {
      block=player.getBlock(bx2+1,by1+i);
      if(!Block.isEmpty(block)) {
        flagCache=true;
        break;
      }
    }
    if(flagCache) {
      rightWall=(bx2+0.5f)*player.pw.blockWidth-1;
      flagCache=false;
    }else rightWall=(bx2+4)*player.pw.blockWidth;
    //------------------------------------------ ceiling
    for(int i=0;i<=bw;i++) {
      block=player.getBlock(bx1+i,by1-1);
      if(!Block.isEmpty(block)) {
        flagCache=true;
        break;
      }
    }
    if(flagCache) {
      ceiling=by1*player.pw.blockHeight+player.h;
      flagCache=false;
    }else ceiling=(by1-4)*player.pw.blockHeight;
  }
}
