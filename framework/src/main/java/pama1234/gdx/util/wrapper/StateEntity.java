package pama1234.gdx.util.wrapper;

import pama1234.gdx.util.listener.EntityListener;
import pama1234.gdx.util.listener.StateEntityListener;

public abstract class StateEntity<T extends StateEntityListener<T>> implements EntityListener{
  public T entity;
  public EntityListener displayCam;
}
