package pama1234.gdx.game.state.state0001.game;

import pama1234.gdx.util.app.UtilScreen;
import pama1234.gdx.util.wrapper.StateCenter;

public class WorldCenter<T extends UtilScreen,E extends World>extends StateCenter<T,E>{
  public WorldCenter(T p,E in) {
    super(p,in);
  }
  public WorldCenter(T p,E[] in) {
    super(p,in);
  }
  public WorldCenter(T p) {
    super(p);
  }
}
