package pama1234.gdx.game.love.state0055;

import pama1234.gdx.game.app.app0002.Screen0055;
import pama1234.gdx.game.love.state0055.State0055Util.StateEntity0055;

public class FirstRun extends StateEntity0055{
  public FirstRun(Screen0055 p) {
    super(p);
  }

  @Override
  public void update() {
    p.state(p.stateCenter.loading);
  }
}
