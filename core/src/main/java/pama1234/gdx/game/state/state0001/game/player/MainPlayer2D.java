package pama1234.gdx.game.state.state0001.game.player;

import pama1234.gdx.game.app.Screen0011;
import pama1234.gdx.game.state.state0001.game.Game;
import pama1234.gdx.game.state.state0001.game.region.Block;
import pama1234.gdx.game.state.state0001.game.world.World0001;
import pama1234.gdx.util.element.CameraController2D;
import pama1234.math.Tools;
import pama1234.math.UtilMath;
import pama1234.math.physics.PathVar;

public class MainPlayer2D extends Player2D{
  public CameraController2D cam;
  public boolean left,right,jump,inAir;
  public int walkCool,jumpCool;
  public float speed=1f;
  public float groundLevel=0;
  //---
  public float maxLife=32;
  public PathVar life=new PathVar(maxLife);
  public MainPlayer2D(Screen0011 p,World0001 pw,float x,float y,Game pg) {
    super(p,pw,x,y,pg);
    this.cam=p.cam2d;
  }
  @Override
  public void update() {
    int bx=blockX();
    int by=blockY();
    if(Tools.moveInRange(x(),0,pw.blockWidth)>w/2f) {
      Block blockLeft=getBlock(bx,by),
        blockRight=getBlock(bx+1,by);
      if(isEmpty(blockLeft)&&isEmpty(blockRight)) groundLevel=(by+4)*pw.blockHeight;
      else groundLevel=by*pw.blockHeight;
    }else if(Tools.moveInRange(x(),0,pw.blockWidth)<pw.blockWidth/2f) {
      Block blockLeft=getBlock(bx-1,by),
        blockRight=getBlock(bx,by);
      if(isEmpty(blockLeft)&&isEmpty(blockRight)) groundLevel=(by+4)*pw.blockHeight;
      else groundLevel=by*pw.blockHeight;
    }else {
      Block block=getBlock(bx,by),
        blockLeft=getBlock(bx-1,by),
        blockRight=getBlock(bx+1,by);
      if(isEmpty(block)&&isEmpty(blockLeft)&&isEmpty(blockRight)) groundLevel=(by+4)*pw.blockHeight;
      else groundLevel=by*pw.blockHeight;
    }
    left=p.isKeyPressed(29)||p.isKeyPressed(21);
    right=p.isKeyPressed(32)||p.isKeyPressed(22);
    jump=p.isKeyPressed(62);
    if(walkCool>0) walkCool--;
    else if(!(left==right)) {
      float speedMult=p.shift?2:1;
      if(left) {
        point.pos.x-=speed*speedMult;
        dir=true;
      }else {
        point.pos.x+=speed*speedMult;
        dir=false;
      }
    }
    inAir=point.pos.y<groundLevel;
    if(inAir) {
      point.vel.y+=0.3f;
    }else {
      if(point.pos.y!=groundLevel) {
        point.vel.y=0;
        point.pos.y=groundLevel;
      }
      if(jumpCool>0) jumpCool--;
      else if(jump) {
        point.vel.y=-pw.blockHeight/2f;
        jumpCool=2;
      }
    }
    super.update();
    if(point.pos.y>groundLevel) {
      point.vel.y=0;
      point.pos.y=groundLevel;
    }
    //---
    // pointer=(p.frameCount/10)%slides.length;
    //---
    // p.cam.point.des.set(point.x()+12.5f,Tools.mag(point.y(),groundLevel)<48?groundLevel+12.5f:point.y()+12.5f,0);
    p.cam.point.des.set(point.x(),Tools.mag(point.y(),groundLevel)<48?groundLevel+12.5f:point.y()+12.5f,0);
    //---
    life.update();
  }
  public boolean isEmpty(Block block) {
    return block==null||block.type.empty;
  }
  public Block getBlock(int xIn,int yIn) {
    return pw.regions.getBlock(xIn,yIn);
    // return pw.regions.getBlock(blockX(),blockY());
  }
  public int blockX() {
    return UtilMath.floor((float)xInt()/pw.blockHeight);
  }
  public int blockY() {
    return UtilMath.floor((float)yInt()/pw.blockWidth);
  }
}