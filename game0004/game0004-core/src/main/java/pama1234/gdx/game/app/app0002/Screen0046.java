package pama1234.gdx.game.app.app0002;

import pama1234.gdx.util.app.ScreenCore2D;
import pama1234.gdx.util.cam.CameraController2D;
import pama1234.util.Annotations.ScreenDescription;

@ScreenDescription("Screen菜单测试")
public class Screen0046 extends ScreenCore2D{
  {
    // rootPath=Gdx.files.local("data/misc/");
  }
  @Override
  public void setup() {
    cam2d.pixelPerfect=CameraController2D.SMOOTH;
    noStroke();
    backgroundColor(243);
    drawCursorWhenGrab=true;

  }
  @Override
  public void update() {}
  @Override
  public void display() {}
  @Override
  public void displayWithCam() {
    textColor(0);
  }
  @Override
  public void frameResized() {}
}
