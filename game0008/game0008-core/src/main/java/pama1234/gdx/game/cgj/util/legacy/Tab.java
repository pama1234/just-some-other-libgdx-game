package pama1234.gdx.game.cgj.util.legacy;

import pama1234.gdx.game.cgj.app.app0002.Screen0045;
import pama1234.gdx.util.entity.Entity;
import pama1234.gdx.util.wrapper.EntityWrapper;

public class Tab<T extends Screen0045,E extends Entity<?>>extends EntityWrapper<T,E>{
  public String name;
  public int id;
  public boolean update=true;
  public Tab(T p,String name,E e,int id) {
    super(p,e);
    this.name=name;
    this.id=id;
  }
}
