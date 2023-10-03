package pama1234.gdx.util.entity;

import pama1234.gdx.util.app.UtilScreen;
import pama1234.math.physics.Point3D;

/**
 * 三维版本的{@link PointEntity}
 */
public class Point3DEntity<T extends Point3D>extends Entity<UtilScreen>{
  public final T point;
  public Point3DEntity(UtilScreen p,T in) {
    super(p);
    point=in;
  }
  @Override
  public void update() {
    point.update();
  }
  public float x() {
    return point.x();
  }
  public float y() {
    return point.y();
  }
  public float z() {
    return point.z();
  }
  public int xInt() {
    return (int)Math.floor(point.pos.x);
  }
  public int yInt() {
    return (int)Math.floor(point.pos.y);
  }
  public int zInt() {
    return (int)Math.floor(point.pos.z);
  }
  /**
   * @return 鼠标位置减去此坐标的值
   */
  public float mouseX() {
    return p.mouse.x-x();
  }
  /**
   * @return 鼠标位置减去此坐标的值
   */
  public float mouseY() {
    return p.mouse.y-y();
  }
}
