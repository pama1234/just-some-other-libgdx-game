package pama1234.gdx.game.state.state0001.game.entity;

import pama1234.gdx.game.app.Screen0011;
import pama1234.gdx.game.state.state0001.Game;
import pama1234.gdx.util.entity.PointEntity;
import pama1234.math.physics.Point;

public class GamePointEntity<T extends Point>extends PointEntity<Screen0011,T>{
  public Game pg;
  public GamePointEntity(Screen0011 p,T in,Game pg) {
    super(p,in);
    this.pg=pg;
  }
}
