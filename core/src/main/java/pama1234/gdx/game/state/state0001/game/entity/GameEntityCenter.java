package pama1234.gdx.game.state.state0001.game.entity;

import pama1234.gdx.game.app.Screen0011;
import pama1234.gdx.util.wrapper.EntityCenter;

public class GameEntityCenter extends EntityCenter<Screen0011,GamePointEntity<?>>{//TODO layerd
  public GameEntityCenter(Screen0011 p) {
    super(p);
  }
  public GameEntityCenter(Screen0011 p,GamePointEntity<?> in) {
    super(p,in);
  }
  public GameEntityCenter(Screen0011 p,GamePointEntity<?>[] in) {
    super(p,in);
  }
}
