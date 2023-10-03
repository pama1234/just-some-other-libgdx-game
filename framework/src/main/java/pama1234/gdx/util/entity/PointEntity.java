package pama1234.gdx.util.entity;

import com.esotericsoftware.kryo.serializers.TaggedFieldSerializer.Tag;

import pama1234.gdx.util.app.UtilScreen;
import pama1234.math.physics.Point;

/**
 * 
 * @see pama1234.math.physics.Point Point 位置信息
 * @see pama1234.math.physics.MassPoint MassPoint 力学效果的实现
 * @see pama1234.math.physics.PathPoint PathPoint 缓动效果的实现
 */
public abstract class PointEntity<T extends UtilScreen,P extends Point>extends Entity<T>{
  @Tag(0)
  public final P point;
  public PointEntity(T p,P in) {
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
  public int xInt() {
    return (int)Math.floor(point.pos.x);
  }
  public int yInt() {
    return (int)Math.floor(point.pos.y);
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
