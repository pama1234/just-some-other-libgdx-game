package pama1234.gdx.game.sandbox.platformer.entity.center;

import pama1234.gdx.game.app.Screen0011;
import pama1234.gdx.game.sandbox.platformer.entity.GamePointEntity;
import pama1234.gdx.game.sandbox.platformer.world.WorldBase2D;
import pama1234.gdx.util.wrapper.EntityCenter;
import pama1234.gdx.util.wrapper.MultiEntityCenter;

public abstract class MultiGameEntityCenter extends MultiEntityCenter<Screen0011,EntityCenter<Screen0011,? extends GamePointEntity<?>>>{
  public WorldBase2D<?> world;
  public MultiGameEntityCenter(Screen0011 p,WorldBase2D<?> world) {
    super(p);
    this.world=world;
  }
  public abstract void acceptAll(GamePointEntity<?>[] entities);
}
