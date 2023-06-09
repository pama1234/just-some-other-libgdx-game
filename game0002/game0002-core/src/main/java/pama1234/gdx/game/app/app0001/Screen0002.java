package pama1234.gdx.game.app.app0001;

import pama1234.gdx.game.util.ControlBindUtil;
import pama1234.gdx.game.util.input.InputData;
import pama1234.gdx.game.world.World0002;
import pama1234.gdx.util.app.ScreenCore2D;
import pama1234.gdx.util.wrapper.DisplayEntity;

/**
 * 2D 粒子系统
 */
public class Screen0002 extends ScreenCore2D{
  public ControlBindUtil controlBind;
  public float zoomSpeed=0.0625f;
  public boolean zoomIn,zoomOut;
  public boolean paused;
  public InputData currentInput;
  //---
  public World0002 world0002;
  @Override
  public void setup() {
    cam.point.f=0.1f;//TODO
    cam2d.minScale=1/4f;
    noStroke();
    backgroundColor(0);
    controlBind=new ControlBindUtil();
    currentInput=new InputData();
    world0002=new World0002(this,controlBind);
    world0002.init();
    centerScreen.add.add(world0002);
    centerCam.add.add(new DisplayEntity(world0002::displayCam));
  }
  @Override
  public void update() {
    if(zoomIn!=zoomOut) {
      if(zoomIn) cam2d.scale.des+=zoomSpeed;
      else cam2d.scale.des-=zoomSpeed;
      cam2d.testScale();
    }
  }
  @Override
  public void displayWithCam() {}
  @Override
  public void display() {}
  @Override
  public void frameResized() {}
  @Override
  public void keyPressed(char key,int keyCode) {
    if(controlBind.isKey(ControlBindUtil.camZoomIn,keyCode)) zoomIn=true;
    else if(controlBind.isKey(ControlBindUtil.camZoomOut,keyCode)) zoomOut=true;
  }
  @Override
  public void keyReleased(char key,int keyCode) {
    if(controlBind.isKey(ControlBindUtil.camZoomIn,keyCode)) zoomIn=false;
    else if(controlBind.isKey(ControlBindUtil.camZoomOut,keyCode)) zoomOut=false;
  }
  public void doPause() {
    paused=!paused;
  }
}