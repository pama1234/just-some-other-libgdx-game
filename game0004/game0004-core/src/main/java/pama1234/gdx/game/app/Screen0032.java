package pama1234.gdx.game.app;

import pama1234.gdx.util.app.ScreenCore3D;

public class Screen0032 extends ScreenCore3D{
  {
    isAndroid=true;
  }
  @Override
  public void setup() {
    noStroke();
    if(isAndroid) addAndroidCam3DButtons();
  }
  @Override
  public void update() {}
  @Override
  public void display() {}
  @Override
  public void displayWithCam() {}
  @Override
  public void frameResized() {}
}
