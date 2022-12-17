package pama1234.gdx.util.entity;

import pama1234.gdx.util.app.UtilScreen;
import pama1234.math.physics.Point;

public abstract class PointEntity<T extends UtilScreen,P extends Point>extends Entity<T>{
  public final P point;
  public PointEntity(T p,P in) {
    super(p);
    point=in;
  }
  @Override
  public void update() {
    point.update();
    //    update2();
  }
  public float x() {
    return point.x();
  }
  public float y() {
    return point.y();
  }
  public int xInt() {
    return (int)Math.floor(point.pos.x);
  }
  public int yInt() {
    return (int)Math.floor(point.pos.y);
  }
  public float mouseX() {
    return p.mouse.x-x();
  }
  public float mouseY() {
    return p.mouse.y-y();
  }
  //  abstract public void update2();
  public String getName() {
    return getClass().getSimpleName();
  }
}
