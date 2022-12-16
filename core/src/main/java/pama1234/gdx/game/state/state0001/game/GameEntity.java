package pama1234.gdx.game.state.state0001.game;

import pama1234.gdx.game.app.Screen0011;
import pama1234.gdx.game.state.state0001.Game;
import pama1234.gdx.util.entity.Entity;

public class GameEntity extends Entity<Screen0011>{
  public Game pg;
  public GameEntity(Screen0011 p,Game pg) {
    super(p);
    this.pg=pg;
  }
}
