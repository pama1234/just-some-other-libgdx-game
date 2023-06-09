package pama1234.gdx.game.app.app0002;

import pama1234.gdx.game.util.input.InputData;
import pama1234.gdx.util.app.ScreenCore2D;

public class Screen0005 extends ScreenCore2D{
  public boolean paused;
  public InputData currentInput;
  @Override
  public void setup() {}
  @Override
  public void update() {}
  @Override
  public void display() {}
  @Override
  public void displayWithCam() {}
  @Override
  public void frameResized() {}
  public void doPause() {
    paused=!paused;
  }
}