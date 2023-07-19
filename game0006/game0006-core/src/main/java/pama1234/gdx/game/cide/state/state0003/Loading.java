package pama1234.gdx.game.cide.state.state0003;

import pama1234.gdx.game.cide.State0003Util.StateEntity0003;
import pama1234.gdx.game.cide.util.app.ScreenCide2D;

public class Loading extends StateEntity0003{
  public Loading(ScreenCide2D p,int id) {
    super(p,id);
  }
  @Override
  public void update() {
    p.state(p.stateCenter.firstRun);
  }
}
