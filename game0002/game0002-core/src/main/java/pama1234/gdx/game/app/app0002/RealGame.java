package pama1234.gdx.game.app.app0002;

import pama1234.gdx.util.app.ScreenCore2D;

/**
 * A REAL GAME that is not "app in Application category"
 */
public class RealGame extends ScreenCore2D{
  MainMenu mainMenu;
  public RealGame(MainMenu mainMenu) {
    this.mainMenu=mainMenu;
  }
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
}
