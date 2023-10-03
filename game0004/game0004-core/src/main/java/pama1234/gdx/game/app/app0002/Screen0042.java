package pama1234.gdx.game.app.app0002;

import pama1234.gdx.util.cam.CameraController2D;
import pama1234.gdx.util.launcher.ScreenMenu;
import pama1234.util.Annotations.ScreenDescription;

@ScreenDescription("Screen菜单测试")
public class Screen0042 extends ScreenMenu{
  {
    // rootPath=Gdx.files.local("data/misc/");
  }
  @Override
  public void setup() {
    cam2d.pixelPerfect=CameraController2D.SMOOTH;
    noStroke();
    backgroundColor(243);
    drawCursorWhenGrab=true;
    
    createAndAddMenuButtons();
  }
  @Override
  public void update() {}
  @Override
  public void display() {}
  @Override
  public void displayWithCam() {
    textColor(0);
    text("小程序菜单：");
  }
  @Override
  public void frameResized() {}
}
