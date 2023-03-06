package pama1234.gdx.game.state.state0001.game.world.background;

import pama1234.gdx.game.app.Screen0011;
import pama1234.gdx.game.state.state0001.game.world.World0001;
import pama1234.gdx.util.wrapper.EntityCenter;

public class BackgroundCenter extends EntityCenter<Screen0011,BackgroundList>{
  public World0001 pw;
  public BackgroundList background0001;
  public BackgroundCenter(Screen0011 p,World0001 pw) {
    super(p);
    this.pw=pw;
  }
  public BackgroundCenter(Screen0011 p,BackgroundList in,World0001 pw) {
    super(p,in);
    this.pw=pw;
  }
  public BackgroundCenter(Screen0011 p,BackgroundList[] in,World0001 pw) {
    super(p,in);
    this.pw=pw;
  }
}
