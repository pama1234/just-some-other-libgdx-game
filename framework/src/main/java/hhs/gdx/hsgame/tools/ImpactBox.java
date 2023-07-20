package hhs.gdx.hsgame.tools;

import com.badlogic.gdx.math.Rectangle;
import hhs.gdx.hsgame.util.Rect;

public class ImpactBox extends Rectangle implements Rect{
  // 检测碰撞y深度
  public float testCollisionHeight(Rectangle otherRect) {
    if(!(overlaps(otherRect))) return 0; // 无触碰情况
    if(y>otherRect.y) return ((otherRect.height+otherRect.y)-y); // this在上
    else return -((height+y)-otherRect.y);// this在下
  }
  // 检测碰撞x深度
  public float testCollisionWidth(Rectangle otherRect) {
    if(!(overlaps(otherRect))) return 0; // 无触碰情况
    if(x>otherRect.x) return ((otherRect.width+otherRect.x)-x); // this在右
    else return -((width+x)-otherRect.x);// this在左
  }
}
