package pama1234.gdx.game.cide.state.state0003.firstRun;

import pama1234.gdx.game.cide.State0003Util.StateEntity0003;
import pama1234.gdx.game.cide.State0003Util.StateEntityListener0003;
import pama1234.gdx.game.cide.state.state0003.FirstRun;
import pama1234.gdx.game.cide.util.app.ScreenCide2D;
import pama1234.gdx.util.entity.Entity;

public class FirstRunDisplayBase extends Entity<ScreenCide2D> implements StateEntityListener0003{
  public FirstRun pf;
  public FirstRunDisplayBase(ScreenCide2D p,FirstRun pf) {
    super(p);
    this.pf=pf;
  }
  @Override
  public void from(StateEntity0003 in) {
    p.centerCam.add.add(this);
  }
  @Override
  public void to(StateEntity0003 in) {
    p.centerCam.remove.add(this);
  }
}