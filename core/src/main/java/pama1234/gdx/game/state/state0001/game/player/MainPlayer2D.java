package pama1234.gdx.game.state.state0001.game.player;

import pama1234.gdx.game.app.Screen0011;
import pama1234.gdx.game.state.state0001.game.Game;
import pama1234.gdx.game.state.state0001.game.item.Inventory;
import pama1234.gdx.game.state.state0001.game.region.Block;
import pama1234.gdx.game.state.state0001.game.world.World0001;
import pama1234.gdx.util.element.CameraController2D;
import pama1234.gdx.util.info.TouchInfo;
import pama1234.math.Tools;
import pama1234.math.UtilMath;

public class MainPlayer2D extends Player2D{
  public CameraController2D cam;
  public PlayerController2D ctrl;
  public Inventory inventory;
  public MainPlayer2D(Screen0011 p,World0001 pw,float x,float y,Game pg) {
    super(p,pw,x,y,pg);
    this.cam=p.cam2d;
    ctrl=new PlayerController2D(p,this);
  }
  @Override
  public void keyPressed(char key,int keyCode) {
    ctrl.keyPressed(key,keyCode);
  }
  @Override
  public void keyReleased(char key,int keyCode) {
    ctrl.keyReleased(key,keyCode);
  }
  @Override
  public void update() {
    // if(p.isKeyPressed(Keys.R)) life.des-=1;//TODO
    // if(p.isKeyPressed(Keys.T)) life.des+=1;
    for(TouchInfo e:p.touches) if(e.active) ctrl.touchUpdate(e);
    ctrl.updateOuterBox();
    //-------------------------------------------------------
    ctrl.updateCtrlInfo();
    ctrl.doWalkAndJump();
    super.update();
    ctrl.constrain();
    //---
    // pointer=(p.frameCount/10)%slides.length;
    //---
    // p.cam.point.des.set(point.x()+12.5f,Tools.mag(point.y(),groundLevel)<48?groundLevel+12.5f:point.y()+12.5f,0);
    p.cam.point.des.set(point.x()+dx+w/2f,Tools.mag(point.y(),ctrl.floor)<48?ctrl.floor+dy+h/2f:point.y()+dy+h/2f,0);
    //---
    life.update();
  }
  public Block getBlock(int xIn,int yIn) {
    return pw.regions.getBlock(xIn,yIn);
  }
  public Block getBlock(float xIn,float yIn) {
    return pw.regions.getBlock(xToBlockCord(xIn),yToBlockCord(yIn));
  }
  public int blockX() {
    return xToBlockCord(x());
  }
  public int blockY() {
    return yToBlockCord(y());
  }
  public int blockX1() {
    return xToBlockCord(x()+dx);
  }
  public int blockY1() {
    return yToBlockCord(y()+dy);
  }
  public int blockX2() {
    return xToBlockCord(x()+dx+w-0.01f);//TODO
  }
  public int blockY2() {
    return yToBlockCord(y()+dy+h-0.01f);//TODO
  }
  public int xToBlockCord(float in) {
    return UtilMath.floor(in/pw.blockWidth);
  }
  public int yToBlockCord(float in) {
    return UtilMath.floor(in/pw.blockHeight);
  }
}