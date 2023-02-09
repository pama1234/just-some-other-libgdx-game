package pama1234.gdx.game.state.state0001.game.entity.util;

import pama1234.gdx.game.state.state0001.game.entity.LivingEntity;
import pama1234.gdx.game.state.state0001.game.region.block.Block;
import pama1234.gdx.game.util.RectF;

public class MovementLimitBox extends OuterBox{
  public boolean inAir;
  public float ceiling,floor,leftWall,rightWall;
  public RectF rectConst;
  //---
  // public boolean leftDownDesSlopFlag,leftUpDesSlopFlag,rightDownDesSlopFlag,rightUpDesSlopFlag;
  // public boolean leftDownPosSlopFlag,leftUpPosSlopFlag,rightDownPosSlopFlag,rightUpPosSlopFlag;
  // public boolean leftDownMoved,leftUpMoved,rightDownMoved,rightUpMoved;
  // public int desX1,desY1,desX2,desY2;
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
  // public void updateDes() {
  //   float tx1=p.x1(),
  //     ty1=p.y1(),
  //     tx2=p.x2(),
  //     ty2=p.y2();
  //   float dx=p.point.dx(),
  //     dy=p.point.dy();
  //   float tx1_a=tx1+dx,
  //     ty1_a=ty1+dy,
  //     tx2_a=tx2+dx,
  //     ty2_a=ty2+dy;
  //   desX1=p.xToBlockCord(tx1_a);
  //   desY1=p.xToBlockCord(ty1_a);
  //   desX2=p.xToBlockCord(tx2_a);
  //   desY2=p.xToBlockCord(ty2_a);
  //   leftUpMoved=x1!=desX1&&y1!=desY1;
  //   leftDownMoved=x1!=desX1&&y2!=desY2;
  //   rightUpMoved=x2!=desX2&&y1!=desY1;
  //   rightDownMoved=x2!=desX2&&y2!=desY2;
  //   int blockWidth=p.pw.settings.blockWidth,
  //     blockHeight=p.pw.settings.blockHeight;
  //   float tx1_b=(desX1+1)*blockWidth-tx1_a,
  //     ty1_b=(desY1+1)*blockHeight-ty1_a,
  //     tx2_b=tx2_a-desX2*blockWidth,
  //     ty2_b=ty2_a-desY2*blockHeight;
  //   tx1_b%=blockWidth;
  //   ty1_b%=blockHeight;
  //   tx2_b%=blockWidth;
  //   ty2_b%=blockHeight;
  //   // System.out.println(tx2_b+" "+ty2_b);
  //   leftUpDesSlopFlag=tx1_b>=ty1_b;
  //   leftDownDesSlopFlag=tx1_b>=ty2_b;
  //   rightUpDesSlopFlag=tx2_b>=ty1_b;
  //   rightDownDesSlopFlag=tx2_b>=ty2_b;
  //   float tx1_c=(x1+1)*blockWidth-tx1,
  //     ty1_c=(y1+1)*blockHeight-ty1,
  //     tx2_c=tx2-x2*blockWidth,
  //     ty2_c=ty2-y2*blockHeight;
  //   tx1_c%=blockWidth;
  //   ty1_c%=blockHeight;
  //   tx2_c%=blockWidth;
  //   ty2_c%=blockHeight;
  //   // System.out.println(tx2_b+" "+ty2_b+" "+tx2_c+" "+ty2_c);
  //   // System.out.println(tx1_b+" "+ty1_b+" "+tx1_c+" "+ty1_c);
  //   leftUpPosSlopFlag=tx1_b/ty1_b>=tx1_c/ty1_c;
  //   leftDownPosSlopFlag=tx1_b/ty2_b>=tx1_c/ty2_c;
  //   rightUpPosSlopFlag=tx2_b/ty1_b>=tx2_c/ty1_c;
  //   rightDownPosSlopFlag=tx2_b/ty2_b>=tx2_c/ty2_c;
  //   // System.out.println(leftUpPosSlopFlag);
  //   // leftUpDesSlopFlag=leftUpPosSlopFlag==leftUpDesSlopFlag;
  //   // leftDownPosSlopFlag=leftDownPosSlopFlag==leftDownDesSlopFlag;
  //   // rightUpPosSlopFlag=rightUpPosSlopFlag==rightUpDesSlopFlag;
  //   // rightDownPosSlopFlag=rightDownPosSlopFlag==rightDownDesSlopFlag;
  // }
  public void updateLimit() {
    int blockWidth=p.pw.settings.blockWidth,
      blockHeight=p.pw.settings.blockHeight;
    int a,b;
    a=0;
    b=w;
    // if(leftUpMoved&&leftUpPosSlopFlag&&leftUpDesSlopFlag) {
    //   a-=1;
    //   b+=1;
    // }
    // if(rightUpMoved&&rightUpPosSlopFlag&&rightUpDesSlopFlag) b+=1;
    if(testCeiling(a,b)) doCeiling(blockHeight);
    else ceiling=(y1-4)*blockHeight;
    // a=0;
    // b=w;
    // if(leftDownMoved&&leftDownPosSlopFlag&&leftDownDesSlopFlag) {
    //   a-=1;
    //   b+=1;
    // }
    // if(rightDownMoved&&rightDownPosSlopFlag&&rightDownDesSlopFlag) b+=1;
    if(testFloor(a,b)) doFloor(blockHeight);
    else floor=(y2+4)*blockHeight;
    //---
    a=0;
    b=h;
    // if(leftUpMoved&&!leftUpPosSlopFlag&&!leftUpDesSlopFlag) {
    //   a-=1;
    //   b+=1;
    // }
    // if(leftDownMoved&&!leftDownPosSlopFlag&&!leftDownDesSlopFlag) b+=1;
    if(testLeft(a,b)) doLeft(blockWidth);
    else leftWall=(x1-4)*blockWidth;
    a=0;
    b=h;
    // if(rightUpMoved&&!rightUpPosSlopFlag&&!rightUpDesSlopFlag) {
    //   a-=1;
    //   b+=1;
    // }
    // if(rightDownMoved&&!rightDownPosSlopFlag&&!rightDownDesSlopFlag) b+=1;
    if(testRight(a,b)) doRight(blockWidth);
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