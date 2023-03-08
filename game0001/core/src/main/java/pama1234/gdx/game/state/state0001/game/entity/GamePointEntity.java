package pama1234.gdx.game.state.state0001.game.entity;

import pama1234.gdx.game.app.Screen0011;
import pama1234.gdx.game.state.state0001.game.world.world0001.World0001;
import pama1234.gdx.util.entity.PointEntity;
import pama1234.math.physics.Point;

public class GamePointEntity<T extends Point>extends PointEntity<Screen0011,T>{
  public World0001 pw;
  public GamePointEntity(Screen0011 p,World0001 pw,T in) {
    super(p,in);
    this.pw=pw;
  }
}