package pama1234.gdx.game.state.state0001.game.entity.util;

import pama1234.gdx.game.state.state0001.game.entity.LivingEntity;
import pama1234.gdx.game.state.state0001.game.player.Player;
import pama1234.gdx.game.state.state0001.game.region.block.Block;
import pama1234.gdx.game.util.RectF;

public class MovementLimitBox extends OuterBox{
  public boolean inAir;
  public float ceiling,floor,leftWall,rightWall;
  public RectF rectConst;
  //---
  public boolean leftDown,leftUp,rightDown,rightUp;
  public MovementLimitBox(LivingEntity p) {
    super(p);
    rectConst=new RectF(
      ()->p.type.w/2f,//left
      ()->p.type.h,//ceiling
      ()->p.pw.settings.blockWidth-p.type.w/2f,//right
      ()->p.pw.settings.blockHeight//floor
    );
  }
  public void constrain() {
    if(p.point.pos.y<ceiling) {
      if(p.point.vel.y<0) p.point.vel.y=0;
      p.point.pos.y=ceiling;
    }else if(p.point.pos.y>floor) {
      if(p.point.vel.y>0) p.point.vel.y=0;
      p.point.pos.y=floor;
    }
    if(p.point.pos.x<leftWall) {
      if(p.point.vel.x<0) p.point.vel.x=0;
      p.point.pos.x=leftWall;
    }else if(p.point.pos.x>rightWall) {
      if(p.point.vel.x>0) p.point.vel.x=0;
      p.point.pos.x=rightWall;
    }
  }
  public void doInAirTest() {
    inAir=p.point.pos.y<floor;
  }
  @Override
  public void update() {
    super.update();
    int blockWidth=p.pw.settings.blockWidth,
      blockHeight=p.pw.settings.blockHeight;
    float tx1=(x1+1)*blockWidth-p.x1();
    float ty1=(y1+1)*blockHeight-p.y1();
    float tx2=p.x2()-x2*blockWidth;
    float ty2=p.y2()-y2*blockHeight;
    leftUp=tx1<ty1;
    leftDown=tx1<ty2;
    rightUp=tx2<ty1;
    rightDown=tx2<ty2;
    if(p instanceof Player) {
      p.p.println(tx1,ty1,tx2,ty2);
      p.p.println(leftUp,leftDown,rightUp,rightDown);
    }
  }
  public void updateLimit() {
    int blockWidth=p.pw.settings.blockWidth,
      blockHeight=p.pw.settings.blockHeight;
    if(testCeiling(leftUp?-1:0,w+(leftUp?1:0)+(rightUp?1:0))) doCeiling(blockHeight);
    else ceiling=(y1-4)*blockHeight;
    if(testFloor(leftDown?-1:0,w+(leftDown?1:0)+(rightDown?1:0))) doFloor(blockHeight);
    else floor=(y2+4)*blockHeight;
    if(testLeft(!leftUp?-1:0,h+(!leftUp?1:0)+(!leftDown?1:0))) doLeft(blockWidth);
    else leftWall=(x1-4)*blockWidth;
    if(testRight(!rightUp?-1:0,h+(!rightUp?1:0)+(!rightDown?1:0))) doRight(blockWidth);
    else rightWall=(x2+4)*blockWidth;
  }
  public void doRight(int blockWidth) {
    rightWall=x2*blockWidth+rectConst.x2();
  }
  public void doLeft(int blockWidth) {
    leftWall=x1*blockWidth+rectConst.x1();
  }
  public void doFloor(int blockHeight) {
    floor=y2*blockHeight+rectConst.y2();
  }
  public void doCeiling(int blockHeight) {
    ceiling=y1*blockHeight+rectConst.y1();
  }
  public void bugFix() {
    if(inAir&&p.point.vel.y<0&&h>1) {
      h-=1;
      y2-=1;
    }
  }
  public boolean testRight(int a,int b) {
    for(int i=a;i<=b;i++) if(!Block.isNotFullBlock(p.getBlock(x2+1,y1+i))) return true;
    return false;
  }
  public boolean testLeft(int a,int b) {
    for(int i=a;i<=b;i++) if(!Block.isNotFullBlock(p.getBlock(x1-1,y1+i))) return true;
    return false;
  }
  public boolean testFloor(int a,int b) {
    for(int i=a;i<=b;i++) if(!Block.isNotFullBlock(p.getBlock(x1+i,y2+1))) return true;
    return false;
  }
  public boolean testCeiling(int a,int b) {
    for(int i=a;i<=b;i++) if(!Block.isNotFullBlock(p.getBlock(x1+i,y1-1))) return true;
    return false;
  }
}