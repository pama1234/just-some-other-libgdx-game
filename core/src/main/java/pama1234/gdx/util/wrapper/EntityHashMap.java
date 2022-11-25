package pama1234.gdx.util.wrapper;

import pama1234.gdx.util.app.UtilScreen;
import pama1234.gdx.util.entity.Entity;

@Deprecated
public class EntityHashMap<T extends Entity<UtilScreen>>extends EntityCenter<T>{//TODO
  public EntityHashMap(UtilScreen p) {
    super(p);
  }
}
