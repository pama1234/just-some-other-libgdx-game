package pama1234.gdx.game.state.state0001.game;

import pama1234.gdx.game.app.Screen0011;
import pama1234.gdx.game.state.state0001.Game;
import pama1234.gdx.util.element.CameraController2D;
import pama1234.math.Tools;
import pama1234.math.physics.PathVar;

public class MainPlayer2D extends Player2D{
  public CameraController2D cam;
  public boolean left,right,jump,inAir;
  public int walkCool,jumpCool;
  public float speed=1f;
  public float groundLevel=18;
  //---
  public float maxLife=32;
  public PathVar life=new PathVar(maxLife);
  public MainPlayer2D(Screen0011 p,float x,float y,Game pg) {
    super(p,x,y,pg);
    this.cam=p.cam2d;
  }
  @Override
  public void update() {
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
      point.vel.y+=0.5;
    }else {
      if(point.pos.y!=groundLevel) {
        point.vel.y=groundLevel;
        point.pos.y=groundLevel;
      }
      if(jumpCool>0) jumpCool--;
      else if(jump) {
        point.vel.y=-6;
        jumpCool=2;
      }
    }
    super.update();
    if(point.pos.y>groundLevel) {
      point.vel.y=groundLevel;
      point.pos.y=groundLevel;
    }
    //---
    // pointer=(p.frameCount/10)%slides.length;
    //---
    p.cam.point.des.set(point.x()+12.5f,Tools.mag(point.y(),groundLevel)<48?groundLevel+12.5f:point.y()+12.5f,0);
    //---
    life.update();
  }
}