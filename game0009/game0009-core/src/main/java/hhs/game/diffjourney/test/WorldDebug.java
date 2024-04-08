package hhs.game.diffjourney.test;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.dongbat.jbump.World;
import hhs.gdx.hsgame.entities.BasicEntity;

public class WorldDebug extends BasicEntity{

  World<?> world;

  public WorldDebug(World<?> world) {
    this.world=world;
  }

  @Override
  public void dispose() {}

  @Override
  public void render(SpriteBatch batch) {}

  @Override
  public void debugDraw(ShapeRenderer sr) {
    for(var rect:world.getRects()) {
      sr.rect(rect.x,rect.y,rect.w,rect.h);
    }
  }
}
