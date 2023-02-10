package pama1234.gdx.game.state.state0001.game.entity.util;

import pama1234.gdx.game.state.state0001.game.entity.LivingEntity;
import pama1234.gdx.game.state.state0001.game.region.block.Block;
import pama1234.gdx.game.util.RectF;

public class MovementLimitBox extends OuterBox{
  public boolean inAir;
  public float ceiling,floor,leftWall,rightWall;
  public RectF rectConst;
  public MovementLimitBox(LivingEntity p) {
    super(p);
    rectConst=new RectF(
      ()->p.type.w/2f,//left
      ()->p.type.h,//ceiling
      ()->p.pw.settings.blockWidth-p.type.w/2f,//right
      ()->p.pw.settings.blockHeight//floor
    );
  }
  public void preCtrlUpdate() {
    testInAir();
  }
  @Override
  public void prePointUpdate() {
    update();
    updateLimitBox();
  }
  @Override
  public void postPointUpdate() {
    testCornerStuck();
    constrain();
  }
  public void testInAir() {
    inAir=p.point.pos.y<floor;
  }
  public void updateLimitBox() {
    int bw=p.pw.settings.blockWidth,
      bh=p.pw.settings.blockHeight;
    if(testCeiling(0,w)) doCeiling(bh,y1);
    else clearCeiling(bh);
    //---
    if(testFloor(0,w)) doFloor(bh,y2);
    else clearFloor(bh);
    //---
    if(testLeft(0,h)) doLeft(bw,x1);
    else clearLeft(bw);
    //---
    if(testRight(0,h)) doRight(bw,x2);
    else clearRight(bw);
  }
  public void testCornerStuck() {
    int bw=p.pw.settings.blockWidth,
      bh=p.pw.settings.blockHeight;
    float tx1=p.blockX1()*bw-p.x1();
    float ty1=p.blockY1()*bh-p.y1();
    float tx2=(p.blockX2()+1)*bw-p.x2();
    float ty2=(p.blockY2()+1)*bh-p.y2();
    if(!Block.isNotFullBlock(p.getBlock(x1,y1))) {
      if(tx1>ty1) doLeft(bw,x1+1);
      else doCeiling(bh,y1+1);
    }
    if(!Block.isNotFullBlock(p.getBlock(x1,y2))) {
      if(tx1>ty2) doLeft(bw,x1+1);
      else doFloor(bh,y2-1);
    }
    if(!Block.isNotFullBlock(p.getBlock(x2,y1))) {
      if(tx2>ty1) doRight(bw,x2-1);
      else doCeiling(bh,y1+1);
    }
    if(!Block.isNotFullBlock(p.getBlock(x2,y2))) {
      if(tx2>ty2) doRight(bw,x2-1);
      else doFloor(bh,y2-1);
    }
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
  public void bugFix() {
    if(inAir&&p.point.vel.y<0&&h>1) {
      h-=1;
      y2-=1;
    }
  }
  public void clearCeiling(int bh) {
    ceiling=(y1-4)*bh;
  }
  public void clearFloor(int bh) {
    floor=(y2+4)*bh;
  }
  public void clearLeft(int bw) {
    leftWall=(x1-4)*bw;
  }
  public void clearRight(int bw) {
    rightWall=(x2+4)*bw;
  }
  public void doCeiling(int bh,int y1) {
    ceiling=y1*bh+rectConst.y1();
  }
  public void doFloor(int bh,int y2) {
    floor=y2*bh+rectConst.y2();
  }
  public void doLeft(int bw,int x1) {
    leftWall=x1*bw+rectConst.x1();
  }
  public void doRight(int bw,int x2) {
    rightWall=x2*bw+rectConst.x2();
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