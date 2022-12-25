package pama1234.gdx.util.wrapper;

import pama1234.gdx.util.app.UtilScreen;

public class MultiEntityCenter<T extends UtilScreen,E extends EntityCenter<T,?>>extends EntityCenter<T,E>{
  public MultiEntityCenter(T p) {
    super(p);
  }
}