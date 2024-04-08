package hhs.game.diffjourney.map;

import com.dongbat.jbump.World;
import hhs.gdx.hsgame.util.Rect;

public interface Collision{
  public abstract World<Rect> getCollisions();
}
