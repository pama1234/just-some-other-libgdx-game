package pama1234.gdx.game.state.state0001.game.entity.util;

import pama1234.gdx.game.state.state0001.game.entity.LivingEntity;
import pama1234.gdx.game.state.state0001.game.region.block.Block;
import pama1234.gdx.game.util.RectF;

public class MovementLimitBox extends OuterBox{
  public boolean inAir;
  public float ceiling,floor,leftWall,rightWall;
  public RectF rectConst;
  //---
  public boolean leftDownW,leftUpW,rightDownW,rightUpW;
  public boolean leftDown,leftUp,rightDown,rightUp;
  public int desX1,desY1,desX2,desY2;
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
  public void updateDes() {
    float tx_a1=p.x1()+p.point.dx(),
      ty_a1=p.y1()+p.point.dy();
    float tx_a2=tx_a1+p.type.w-0.01f,
      ty_a2=ty_a1+p.type.h-0.01f;
    desX1=p.xToBlockCord(tx_a1);
    desY1=p.xToBlockCord(ty_a1);
    desX2=p.xToBlockCord(tx_a2);
    desY2=p.xToBlockCord(ty_a2);
    leftUp=x1!=desX1&&y1!=desY1;
    leftDown=x1!=desX1&&y2!=desY2;
    rightUp=x2!=desX2&&y1!=desY1;
    rightDown=x2!=desX2&&y2!=desY2;
    // if(p instanceof Player&&(leftUp||leftDown||rightUp||rightDown)) p.p.println(leftUp,leftDown,rightUp,rightDown);
    int blockWidth=p.pw.settings.blockWidth,
      blockHeight=p.pw.settings.blockHeight;
    // float tx_b1=(x1+1)*blockWidth-p.x1();
    // float ty_b1=(y1+1)*blockHeight-p.y1();
    // float tx_b2=p.x2()-x2*blockWidth;
    // float ty_b2=p.y2()-y2*blockHeight;
    float tx_b1=(desX1+1)*blockWidth-tx_a1;
    float ty_b1=(desY1+1)*blockHeight-ty_a1;
    float tx_b2=tx_a2-desX2*blockWidth;
    float ty_b2=ty_a2-desY2*blockHeight;
    leftUpW=tx_b1<ty_b1;
    leftDownW=tx_b1<ty_b2;
    rightUpW=tx_b2<ty_b1;
    rightDownW=tx_b2<ty_b2;
    // if(p instanceof Player) {
    //   p.p.println(tx1,ty1,tx2,ty2);
    //   p.p.println(leftUpW,leftDownW,rightUpW,rightDownW);
    // }
  }
  @Override
  public void update() {
    super.update();
    // updateFlag();
  }
  // public void updateFlag() {}
  public void updateLimit() {
    int blockWidth=p.pw.settings.blockWidth,
      blockHeight=p.pw.settings.blockHeight;
    if(testCeiling(0,w)) doCeiling(blockHeight);
    else ceiling=(y1-4)*blockHeight;
    if(testFloor(0,w)) doFloor(blockHeight);
    else floor=(y2+4)*blockHeight;
    if(testLeft(0,h)) doLeft(blockWidth);
    else leftWall=(x1-4)*blockWidth;
    if(testRight(0,h)) doRight(blockWidth);
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