package pama1234.gdx.util.wrapper;

import pama1234.gdx.util.app.UtilScreen;
import pama1234.gdx.util.entity.Entity;

@Deprecated
public class EntityHashMap<T extends UtilScreen,E extends Entity<T>>extends EntityCenter<T,E>{//TODO
  public EntityHashMap(T p) {
    super(p);
  }
}
