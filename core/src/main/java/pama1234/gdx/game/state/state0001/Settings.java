package pama1234.gdx.game.state.state0001;

import pama1234.gdx.game.app.Screen0011;
import pama1234.gdx.game.state.state0001.StateGenerator0001.StateEntity0001;

public class Settings extends StateEntity0001{
  public Settings(Screen0011 p) {
    super(p);
  }
  @Override
  public void init() {
    p.backgroundColor(0);
  }
  @Override
  public void displayCam() {
    // p.text("FirstRun",0,0);
  }
}