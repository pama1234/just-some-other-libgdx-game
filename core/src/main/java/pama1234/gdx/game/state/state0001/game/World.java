package pama1234.gdx.game.state.state0001.game;

import pama1234.gdx.util.app.UtilScreen;
import pama1234.gdx.util.wrapper.LayeredEntityCenter;

public abstract class World<T extends UtilScreen>extends LayeredEntityCenter<T>{
  public World(T p,int size) {
    super(p,size);
  }
}