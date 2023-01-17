package pama1234.gdx.game.state.state0001.game.player;

import pama1234.gdx.game.app.Screen0011;
import pama1234.gdx.game.state.state0001.game.item.Inventory;
import pama1234.gdx.game.state.state0001.game.world.World0001;
import pama1234.gdx.util.element.CameraController2D;
import pama1234.gdx.util.info.TouchInfo;

public class MainPlayer extends Player{
  public CameraController2D cam;
  public PlayerController ctrl;
  public Inventory inventory;
  public MainPlayer(Screen0011 p,World0001 pw,float x,float y) {
    super(p,pw,x,y,pw.metaEntitys.player);
    this.cam=p.cam2d;
    ctrl=new PlayerController(p,this);
    inventory=new Inventory(this,32,9);
    // gameMode=GameMode.creative;
    inventory.data[5].item=pw.metaItems.workbench.createItem(16);
    inventory.data[4].item=pw.metaItems.dirt.createItem(64);//TODO
    inventory.data[3].item=pw.metaItems.stone.createItem(64);
    inventory.data[2].item=pw.metaItems.log.createItem(64);
    inventory.data[1].item=pw.metaItems.branch.createItem(64);
    inventory.data[0].item=pw.metaItems.leaf.createItem(64);
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
  public void mouseWheel(float x,float y) {
    ctrl.mouseWheel(x,y);
  }
  @Override
  public void touchStarted(TouchInfo info) {
    ctrl.touchStarted(info);
  }
  @Override
  public void touchEnded(TouchInfo info) {
    ctrl.touchEnded(info);
  }
  @Override
  public void update() {
    for(TouchInfo e:p.touches) if(e.active) ctrl.touchUpdate(e);
    ctrl.updateOuterBox();
    //---
    ctrl.updateCtrlInfo();
    ctrl.doWalkAndJump();
    //---
    super.update();
    ctrl.constrain();
    ctrl.updatePickItem();
    // p.cam.point.des.set(cx(),Tools.mag(point.y(),ctrl.limitBox.floor)<48?ctrl.limitBox.floor+type.dy+type.h/2f:cy(),0);//TODO
    // p.println(cx(),cy());
    p.cam.point.des.set(cx(),cy());
    //---
    inventory.update();
  }
  @Override
  public void display() {
    super.display();
    ctrl.display();
    inventory.display();
    p.noTint();
  }
}