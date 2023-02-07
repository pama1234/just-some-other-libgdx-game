package pama1234.gdx.game.state.state0001.game.entity.util;

import pama1234.gdx.game.state.state0001.game.entity.LivingEntity;
import pama1234.gdx.game.state.state0001.game.region.block.Block;
import pama1234.gdx.game.util.RectF;
import pama1234.math.UtilMath;

public class MovementLimitBox extends OuterBox{
  public boolean inAir;
  public float floor,leftWall,rightWall,ceiling;
  public RectF rectConst;
  public MovementLimitBox(LivingEntity p) {
    super(p);
    rectConst=new RectF(
      ()->p.type.w/2f,//left
      // ()->p.type.w/2f+0.01f,//left
      ()->p.type.h,//ceiling
      ()->p.pw.settings.blockWidth-p.type.w/2f,//right
      // ()->p.pw.blockWidth-p.type.w/2f-0.01f,//right
      ()->p.pw.settings.blockHeight//floor
    );
  }
  public void constrain() {
    if(p.point.pos.y>floor) {
      if(p.point.vel.y>0) p.point.vel.y=0;
      p.point.pos.y=floor;
    }else if(p.point.pos.y<ceiling) {
      if(p.point.vel.y<0) p.point.vel.y=0;
      p.point.pos.y=ceiling;
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
  public void updateLimit() {
    int blockWidth=p.pw.settings.blockWidth;
    int blockHeight=p.pw.settings.blockHeight;
    // bugFix();
    // if(p instanceof MainPlayer) System.out.println(w+" "+h);
    // if(p instanceof MainPlayer) {
    //   if(xFlag) System.out.println("xFlag "+x1+" "+desX);
    //   if(yFlag) System.out.println("yFlag "+y1+" "+desY);
    // }
    if(testCeiling()) doCeiling(blockHeight);
    else ceiling=(y1-4)*blockHeight;
    if(testFloor()) doFloor(blockHeight);
    else floor=(y2+4)*blockHeight;
    if(testLeft()) doLeft(blockWidth);
    else leftWall=(x1-4)*blockWidth;
    if(testRight()) doRight(blockWidth);
    else rightWall=(x2+4)*blockWidth;
    if(xFlag&&yFlag) {
      System.out.println("MovementLimitBox.updateLimit()");
      boolean flag=UtilMath.abs(p.point.vel.y)>UtilMath.abs(p.point.vel.x);
      if(p.point.vel.x>0) {//右
        if(p.point.vel.y>0) {//右下
          if(!Block.isNotFullBlock(p.getBlock(x2+1,y2+1))) {
            if(flag) doRight(blockWidth);
            else doFloor(blockHeight);
          }
        }else {//右上
          if(!Block.isNotFullBlock(p.getBlock(x2+1,y1-1))) {
            if(flag) doRight(blockWidth);
            else doCeiling(blockHeight);
          }
        }
      }else {//左
        if(p.point.vel.y>0) {//左下
          if(!Block.isNotFullBlock(p.getBlock(x1-1,y2+1))) {
            if(flag) doLeft(blockWidth);
            else doFloor(blockHeight);
          }
        }else {//左上
          if(!Block.isNotFullBlock(p.getBlock(x1-1,y1-1))) {
            if(flag) doLeft(blockWidth);
            else doCeiling(blockHeight);
          }
        }
      }
    }
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
  public boolean testRight() {
    for(int i=0;i<=h;i++) if(!Block.isNotFullBlock(p.getBlock(x2+1,y1+i))) return true;
    return false;
  }
  public boolean testLeft() {
    for(int i=0;i<=h;i++) if(!Block.isNotFullBlock(p.getBlock(x1-1,y1+i))) return true;
    return false;
  }
  public boolean testFloor() {
    for(int i=0;i<=w;i++) if(!Block.isNotFullBlock(p.getBlock(x1+i,y2+1))) return true;
    return false;
  }
  public boolean testCeiling() {
    for(int i=0;i<=w;i++) if(!Block.isNotFullBlock(p.getBlock(x1+i,y1-1))) return true;
    return false;
  }
}