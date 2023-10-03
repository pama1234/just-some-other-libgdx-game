package pama1234.gdx.game.app;

import pama1234.gdx.game.sandbox.platformer.misc.Earth;
import pama1234.gdx.util.app.ScreenCore3D;

public class Screen0048 extends ScreenCore3D{
  public Earth earth;
  @Override
  public void setup() {
    earth=new Earth(this);
    centerCamAddAll(earth);
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
