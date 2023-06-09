package pama1234.gdx.game.util.legacy;

import pama1234.gdx.game.app.app0002.RealGame;
import pama1234.gdx.util.entity.Entity;
import pama1234.gdx.util.wrapper.EntityWrapper;

public abstract class Page<T extends RealGame,E extends Entity<?>>extends EntityWrapper<T,E>{
  public String name;
  @Deprecated
  public final E e;
  public Page(T p,String name,E e) {
    super(p,e);
    this.e=e;
    this.name=name;
  }
  public abstract void show();
  public abstract void hide();
}
