package pama1234.gdx.game.util.legacy;

import pama1234.gdx.game.app.app0002.RealGame;
import pama1234.gdx.util.entity.Entity;
import pama1234.gdx.util.wrapper.EntityWrapper;

public class Tab<T extends RealGame,E extends Entity<?>>extends EntityWrapper<T,E>{
  public String name;
  public boolean update=true;
  public Tab(T p,String name,E e) {
    super(p,e);
    this.name=name;
  }
}
