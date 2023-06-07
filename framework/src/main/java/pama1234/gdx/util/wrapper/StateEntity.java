package pama1234.gdx.util.wrapper;

import pama1234.gdx.util.app.UtilScreen;
import pama1234.gdx.util.entity.Entity;
import pama1234.gdx.util.listener.EntityListener;
import pama1234.gdx.util.listener.StateEntityListener;

public abstract class StateEntity<T extends UtilScreen,E extends StateEntityListener<?>>extends Entity<T>{
  public EntityListener displayCam;
  public StateEntity(T p) {
    super(p);
  }
}
