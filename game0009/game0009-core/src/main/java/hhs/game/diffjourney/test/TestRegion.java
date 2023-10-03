package hhs.game.diffjourney.test;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import hhs.gdx.hsgame.entities.EntityCenter;

public class TestRegion extends EntityCenter<TestBlock>{
  public int id,mwidth=100,mheight=100;
  public char map[][];
  @Override
  public void dispose() {}
  @Override
  public void UpdateAndRender(SpriteBatch batch,float delta) {
    if(!remove.isEmpty()) {
      sons.removeAll(remove,true);
      remove.clear();
    }
    for(TestBlock be:sons) {
      be.update(delta);
      be.render(batch);
    }
  }
}
