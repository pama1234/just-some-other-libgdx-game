package pama1234.gdx.game.state.state0001.game.player;

import pama1234.gdx.game.app.Screen0011;
import pama1234.gdx.game.state.state0001.game.Game;
import pama1234.gdx.game.state.state0001.game.item.IntItem;
import pama1234.gdx.game.state.state0001.game.item.Inventory;
import pama1234.gdx.game.state.state0001.game.world.World0001;
import pama1234.gdx.util.element.CameraController2D;
import pama1234.gdx.util.info.TouchInfo;
import pama1234.math.Tools;

public class MainPlayer2D extends Player2D{
  public CameraController2D cam;
  public PlayerController2D ctrl;
  public Inventory<IntItem> inventory;
  public MainPlayer2D(Screen0011 p,World0001 pw,float x,float y,Game pg) {
    super(p,pw,x,y,pg);
    this.cam=p.cam2d;
    ctrl=new PlayerController2D(p,this);
    inventory=new Inventory<>(this,32);
    // for(int i=0;i<inventory.data.length;i++) inventory.data[i].item=new IntItem(pw.itemC.dirt);
    inventory.data[0].item=pw.itemC.dirt.createItem();
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
  public void touchStarted(TouchInfo info) {
    ctrl.touchStarted(info);
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
    p.cam.point.des.set(cx(),Tools.mag(point.y(),ctrl.floor)<48?ctrl.floor+dy+h/2f:cy(),0);
    //---
    life.update();
  }
  @Override
  public void display() {
    super.display();
    inventory.displayHotSlot();
  }
}