package hhs.gdx.hsgame.tools;

import com.badlogic.gdx.math.Rectangle;
import hhs.gdx.hsgame.util.Rect;

public class ImpactBox extends Rectangle implements Rect{
  // 检测碰撞y深度
  public float testCollisionHeight(Rectangle otherRect) {
    if(!(overlaps(otherRect))) return 0; // 无触碰情况
    if(y>otherRect.y)
      // this在上
      return ((otherRect.height+otherRect.y)-y);
    else
      // this在下
      return -((height+y)-otherRect.y);
  }
  // 检测碰撞x深度
  public float testCollisionWidth(Rectangle otherRect) {
    if(!(overlaps(otherRect))) return 0; // 无触碰情况
    if(x>otherRect.x)
      // this在右
      return ((otherRect.width+otherRect.x)-x);
    else
      // this在左
      return -((width+x)-otherRect.x);
  }
}
