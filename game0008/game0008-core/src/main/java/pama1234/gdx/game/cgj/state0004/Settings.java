package pama1234.gdx.game.cgj.state0004;

import pama1234.gdx.game.cgj.app.app0002.RealGame0002;
import pama1234.gdx.game.cgj.state0004.State0004Util.StateEntity0004;

public class Settings extends StateEntity0004{
  public Settings(RealGame0002 p,int id) {
    super(p,id);
  }
  @Override
  public void displayCam() {
    p.text("设置功能未实现",-64,-8);
  }
  @Override
  public void from(StateEntity0004 in) {
    p.centerScreenAddAll(p.returnButton);
    p.cam2d.point.des.set(0,0);
  }
  @Override
  public void to(StateEntity0004 in) {
    p.centerScreenRemoveAll(p.returnButton);
  }
}
