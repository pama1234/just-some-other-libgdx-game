package pama1234.gdx.game.cgj.util.legacy;

import pama1234.gdx.game.cgj.app.app0002.RealGame0002;
import pama1234.gdx.util.entity.Entity;
import pama1234.gdx.util.wrapper.EntityWrapper;

public abstract class Page<T extends RealGame0002,E extends Entity<?>>extends EntityWrapper<T,E>{
  /**
   * use content
   */
  @Deprecated
  public final E e;
  public Page(T p,E e) {
    super(p,e);
    this.e=e;
  }
  public abstract void show();
  public abstract void hide();
}
