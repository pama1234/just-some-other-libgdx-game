package pama1234.gdx.game.state.state0003.game;

import pama1234.game.app.server.server0002.game.WorldData;
import pama1234.gdx.game.app.Screen0016;
import pama1234.gdx.util.wrapper.EntityCenter;

public class World extends EntityCenter<Screen0016,Player>{
  public WorldData data;
  public MainPlayer yourself;
  public World(Screen0016 p) {
    super(p);
    yourself=new MainPlayer(p,0,0);
    add.add(yourself);
  }
}