package hhs.gdx.hsgame.actions;

import hhs.gdx.hsgame.entitys.Entity;

public interface Action<T extends Entity>{
  public abstract void start(T entity);
  public abstract boolean update(T entity,float delta);
}
