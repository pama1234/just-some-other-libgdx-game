package pama1234.gdx.game.util.legacy;

import pama1234.gdx.game.app.app0002.RealGame;
import pama1234.gdx.util.entity.Entity;
import pama1234.gdx.util.wrapper.EntityWrapper;

public class Page<T extends RealGame,E extends Entity<?>>extends EntityWrapper<T,E>{
  public String name;
  public Page(T p,String name,E e) {
    super(p,e);
    this.name=name;
  }
}
