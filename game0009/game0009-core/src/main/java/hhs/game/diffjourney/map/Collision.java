package hhs.game.diffjourney.map;

import com.badlogic.gdx.utils.Array;
import hhs.gdx.hsgame.util.Rect;

public interface Collision{
  public abstract Array<Block> getCollisions(Rect r);
}
