package pama1234.gdx.util.entity;

import pama1234.gdx.util.app.UtilScreen;
import pama1234.gdx.util.listener.EntityListener;
import pama1234.util.Annotations.RedundantCache;

/**
 * {@link EntityListener}实体接口对于{@link UtilScreen}作为父实例类（这词我瞎编的）的实现
 * 
 * @see PointEntity
 */
public abstract class Entity<T extends UtilScreen> implements EntityListener{
  @RedundantCache
  public T p;
  public Entity(T p) {
    this.p=p;
  }
}