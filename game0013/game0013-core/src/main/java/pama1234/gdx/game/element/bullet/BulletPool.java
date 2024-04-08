package pama1234.gdx.game.element.bullet;

import com.badlogic.gdx.utils.Pool;

import pama1234.gdx.game.app.app0002.Screen0055;
import pama1234.gdx.game.element.GameCenter;
import pama1234.math.physics.ReversedPathPoint3D;

public class BulletPool extends Pool<BulletEntity>{
  public Screen0055 p;
  public GameCenter pg;

  public BulletPool(Screen0055 p,GameCenter pg) {
    this.p=p;
    this.pg=pg;
  }

  @Override
  protected BulletEntity newObject() {
    return new BulletEntity(p,pg,new ReversedPathPoint3D(),"null");
  }

  @Override
  public BulletEntity obtain() {
    BulletEntity o=super.obtain();
    o.reset();
    return o;
  }
}