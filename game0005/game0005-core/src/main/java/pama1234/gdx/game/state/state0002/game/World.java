package pama1234.gdx.game.state.state0002.game;

import pama1234.gdx.game.app.app0004.Screen0012;
import pama1234.gdx.util.wrapper.EntityCenter;

public class World extends EntityCenter<Screen0012,Player>{
  public MainPlayer yourself;
  public World(Screen0012 p) {
    super(p);
    yourself=new MainPlayer(p,0,0);
    add.add(yourself);
  }
}