package hhs.game.diffjourney.map;

import com.badlogic.gdx.utils.Array;
import hhs.gdx.hsgame.util.Rect;
//给一个矩形，获取可能与之发生碰撞的实体
public interface Collision{
  public abstract Array<Block> getCollisions(Rect r);
}
