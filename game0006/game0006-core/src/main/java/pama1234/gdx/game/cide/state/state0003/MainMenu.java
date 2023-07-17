package pama1234.gdx.game.cide.state.state0003;

import pama1234.gdx.game.cide.State0003Util.StateEntity0003;
import pama1234.gdx.game.cide.util.app.ScreenCide2D;

public class MainMenu extends StateEntity0003{
  public MainMenu(ScreenCide2D p,int id) {
    super(p,id);
  }
  @Override
  public void from(StateEntity0003 in) {
    // System.out.println("MainMenu.from()");
    if(p.laodDebugState) p.state(p.stateCenter.debug.editorTest);
  }
  @Override
  public void to(StateEntity0003 in) {}
}
