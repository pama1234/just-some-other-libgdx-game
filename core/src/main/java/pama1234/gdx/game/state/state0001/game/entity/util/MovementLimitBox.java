package pama1234.gdx.game.state.state0001.game.entity.util;

import pama1234.gdx.game.state.state0001.game.entity.LivingEntity;
import pama1234.gdx.game.state.state0001.game.region.block.Block;
import pama1234.gdx.game.util.RectF;

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
      ()->p.pw.blockWidth-p.type.w/2f,//right
      // ()->p.pw.blockWidth-p.type.w/2f-0.01f,//right
      ()->p.pw.blockHeight//floor
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
    // if(inAir&&p.point.vel.y>0) y2-=1;
    if(inAir&&p.point.vel.y<0&&h>1) h-=1;
    Block block;
    flagCache=false;
    //------------------------------------------ ceiling
    for(int i=0;i<=w;i++) {
      block=p.getBlock(x1+i,y1-1);
      if(!Block.isNotFullBlock(block)) {
        flagCache=true;
        break;
      }
    }
    if(flagCache) {
      ceiling=y1*p.pw.blockHeight+rectConst.y1();
      flagCache=false;
    }else ceiling=(y1-4)*p.pw.blockHeight;
    //------------------------------------------ floor
    for(int i=0;i<=w;i++) {
      block=p.getBlock(x1+i,y2+1);
      if(!Block.isNotFullBlock(block)) {
        flagCache=true;
        break;
      }
    }
    if(flagCache) {
      floor=y2*p.pw.blockHeight+rectConst.y2();
      flagCache=false;
    }else floor=(y2+4)*p.pw.blockHeight;
    //------------------------------------------ left
    // System.out.println(h);
    for(int i=0;i<=h;i++) {
      block=p.getBlock(x1-1,y1+i);
      if(!Block.isNotFullBlock(block)) {
        flagCache=true;
        break;
      }
    }
    if(flagCache) {
      leftWall=(x1)*p.pw.blockWidth+rectConst.x1();
      flagCache=false;
    }else leftWall=(x1-4)*p.pw.blockWidth;
    //------------------------------------------ right
    for(int i=0;i<=h;i++) {
      block=p.getBlock(x2+1,y1+i);
      if(!Block.isNotFullBlock(block)) {
        flagCache=true;
        break;
      }
    }
    if(flagCache) {
      rightWall=(x2)*p.pw.blockWidth+rectConst.x2();
      flagCache=false;
    }else rightWall=(x2+4)*p.pw.blockWidth;
  }
}
