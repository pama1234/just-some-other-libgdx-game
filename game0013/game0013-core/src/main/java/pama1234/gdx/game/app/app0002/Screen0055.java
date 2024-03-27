package pama1234.gdx.game.app.app0002;

import pama1234.gdx.game.love.state0055.State0055Util;
import pama1234.gdx.game.love.state0055.State0055Util.StateCenter0055;
import pama1234.gdx.game.love.state0055.State0055Util.StateEntity0055;
import pama1234.gdx.game.util.ui.ColorUtil;
import pama1234.gdx.util.app.ScreenCoreState3D;

public class Screen0055 extends ScreenCoreState3D<StateCenter0055,StateEntity0055>{

  @Override
  public void setup() {
    stateCenter=new StateCenter0055(this);
    State0055Util.loadState0055(this,stateCenter);

    state(stateCenter.firstRun);

    backgroundColor=ColorUtil.background;

    cam3d.point.des.set(0,0,-320);
  }

  @Override
  public void update() {}

  @Override
  public void display() {}

  @Override
  public void displayWithCam() {}

  @Override
  public void frameResized() {}

  public int randomInt(int a) {
    return (int)random(a);
  }
}