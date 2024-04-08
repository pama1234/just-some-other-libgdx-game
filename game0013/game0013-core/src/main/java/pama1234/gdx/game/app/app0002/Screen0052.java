package pama1234.gdx.game.app.app0002;

import pama1234.gdx.game.love.state0052.State0052Util;
import pama1234.gdx.game.love.state0052.State0052Util.StateCenter0052;
import pama1234.gdx.game.love.state0052.State0052Util.StateEntity0052;
import pama1234.gdx.util.app.ScreenCoreState2D;

public class Screen0052 extends ScreenCoreState2D<StateCenter0052,StateEntity0052>{

  @Override
  public void setup() {
    stateCenter=new StateCenter0052(this);
    State0052Util.loadState0052(this,stateCenter);

    state(stateCenter.firstRun);
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
