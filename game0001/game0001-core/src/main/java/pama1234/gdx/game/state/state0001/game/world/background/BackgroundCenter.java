package pama1234.gdx.game.state.state0001.game.world.background;

import pama1234.gdx.game.app.Screen0011;
import pama1234.gdx.game.state.state0001.game.world.WorldBase2D;
import pama1234.gdx.util.wrapper.EntityCenter;

public class BackgroundCenter extends EntityCenter<Screen0011,BackgroundList>{
  public WorldBase2D<?> pw;
  public BackgroundList background0001;
  public BackgroundCenter(Screen0011 p,WorldBase2D<?> pw) {
    super(p);
    this.pw=pw;
  }
  public BackgroundCenter(Screen0011 p,BackgroundList in,WorldBase2D<?> pw) {
    super(p,in);
    this.pw=pw;
  }
  public BackgroundCenter(Screen0011 p,BackgroundList[] in,WorldBase2D<?> pw) {
    super(p,in);
    this.pw=pw;
  }
}
