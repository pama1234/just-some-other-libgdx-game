package pama1234.gdx.util.entity;

import pama1234.gdx.util.app.UtilScreen;
import pama1234.gdx.util.listener.EntityListener;

/**
 * {@link EntityListener}实体接口对于{@link UtilScreen}的实现
 * 
 * @see PointEntity
 */
public abstract class Entity<T extends UtilScreen> implements EntityListener{
  public T p;
  public Entity(T p) {
    this.p=p;
  }
}