package pama1234.gdx.game.state.state0001.game.entity.util;

import pama1234.gdx.game.state.state0001.game.entity.LivingEntity;
import pama1234.gdx.game.state.state0001.game.region.block.Block;

public class MovementLimitBox extends OuterBox{
  public boolean inAir;
  public float floor,leftWall,rightWall,ceiling;
  public MovementLimitBox(LivingEntity p) {
    super(p);
  }
  public void constrain() {
    if(p.point.pos.y>floor) {
      p.point.vel.y=0;
      p.point.pos.y=floor;
    }
    if(p.point.pos.y<ceiling) {
      if(p.point.vel.y<0) p.point.vel.y=0;
      p.point.pos.y=ceiling;
    }
    if(p.point.pos.x<leftWall) p.point.pos.x=leftWall;
    if(p.point.pos.x>rightWall) p.point.pos.x=rightWall;
  }
  public void updateInAir() {
    inAir=p.point.pos.y<floor;
  }
  public void updateLimit() {
    if(inAir&&p.point.vel.y>0) bh+=1;
    Block block;
    flagCache=false;
    //------------------------------------------ floor
    for(int i=0;i<=bw;i++) {
      block=p.getBlock(bx1+i,by2+1);
      if(!Block.isEmpty(block)) {
        flagCache=true;
        break;
      }
    }
    if(flagCache) {
      floor=(by2+1)*p.pw.blockHeight;
      flagCache=false;
    }else floor=(by2+4)*p.pw.blockHeight;
    //------------------------------------------ left
    // for(int i=inAir?-1:0;i<=bh;i++) {
    for(int i=0;i<=bh;i++) {
      block=p.getBlock(bx1-1,by1+i);
      if(!Block.isEmpty(block)) {
        flagCache=true;
        break;
      }
    }
    if(flagCache) {
      leftWall=(bx1+0.5f)*p.pw.blockWidth+1;
      flagCache=false;
    }else leftWall=(bx1-4)*p.pw.blockWidth;
    //------------------------------------------ right
    for(int i=0;i<=bh;i++) {
      block=p.getBlock(bx2+1,by1+i);
      if(!Block.isEmpty(block)) {
        flagCache=true;
        break;
      }
    }
    if(flagCache) {
      rightWall=(bx2+0.5f)*p.pw.blockWidth-1;
      flagCache=false;
    }else rightWall=(bx2+4)*p.pw.blockWidth;
    //------------------------------------------ ceiling
    for(int i=0;i<=bw;i++) {
      block=p.getBlock(bx1+i,by1-1);
      if(!Block.isEmpty(block)) {
        flagCache=true;
        break;
      }
    }
    if(flagCache) {
      ceiling=by1*p.pw.blockHeight+p.h;
      flagCache=false;
    }else ceiling=(by1-4)*p.pw.blockHeight;
  }
}
