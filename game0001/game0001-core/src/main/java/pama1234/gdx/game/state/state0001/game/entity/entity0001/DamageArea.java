package pama1234.gdx.game.state.state0001.game.entity.entity0001;

import pama1234.gdx.game.app.Screen0011;
import pama1234.gdx.game.state.state0001.game.entity.GamePointEntity;
import pama1234.gdx.game.state.state0001.game.world.world0001.World0001;
import pama1234.math.physics.Point;

public class DamageArea<T extends Point>extends GamePointEntity<T>{
  public DamageArea(Screen0011 p,World0001 pw,T in) {
    super(p,pw,in);
  }
}