package pama1234.gdx.game.state.state0003.game;

import pama1234.game.app.server.server0002.game.ServerWorld;
import pama1234.gdx.game.app.app0004.Screen0016;
import pama1234.gdx.util.wrapper.EntityCenter;

public class World extends EntityCenter<Screen0016,Player>{
  public ServerWorld data;
  public MainPlayer yourself;
  public World(Screen0016 p) {
    super(p);
    yourself=new MainPlayer(p,0,0);
    add.add(yourself);
  }
}