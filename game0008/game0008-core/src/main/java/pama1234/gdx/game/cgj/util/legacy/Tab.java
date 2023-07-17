package pama1234.gdx.game.cgj.util.legacy;

import pama1234.gdx.game.cgj.app.app0002.RealGame0002;
import pama1234.gdx.util.entity.Entity;
import pama1234.gdx.util.wrapper.EntityWrapper;

public class Tab<T extends RealGame0002,E extends Entity<?>>extends EntityWrapper<T,E>{
  public String name;
  public boolean update=true;
  public Tab(T p,String name,E e) {
    super(p,e);
    this.name=name;
  }
}
