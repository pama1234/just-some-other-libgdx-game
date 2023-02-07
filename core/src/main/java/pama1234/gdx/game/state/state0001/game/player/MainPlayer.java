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
    inventory=new Inventory(this,52,9);
    // gameMode=GameMode.creative;
    inventory.data[5].item=pw.metaItems.workbench.createItem(16);
  }
  @Override
  public void update() {
    for(TouchInfo e:p.touches) if(e.active) ctrl.touchUpdate(e);
    ctrl.limitBox.update();
    ctrl.updateCtrlInfo();
    // ctrl.limitBox.updateDes();
    ctrl.doWalkAndJump();
    // ctrl.constrain();
    ctrl.limitBox.updateDes();
    ctrl.limitBox.updateLimit();
    ctrl.limitBox.cornerFix();
    //---
    super.update();
    ctrl.limitBox.constrain();
    // ctrl.limitBox.updateDes();
    // ctrl.limitBox.cornerFix();
    // ctrl.limitBox.constrain();
    ctrl.updatePickItem();
    p.cam.point.des.set(cx(),cy());
    //---
    inventory.update();
    if(life.pos<=0) respawn();
  }
  public void respawn() {
    point.pos.set(0,0);
    life.des=type.maxLife;
  }
  @Override
  public void display() {
    super.display();
    ctrl.display();
    inventory.display();
    p.noTint();
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
}