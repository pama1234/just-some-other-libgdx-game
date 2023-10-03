package hhs.game.diffjourney.test;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import hhs.gdx.hsgame.tools.EntityTool;

public class MyRandomMap extends TestMap{
  OrthographicCamera cam;
  Thread addBlock;
  static boolean start=false;
  public MyRandomMap(OrthographicCamera cam) {
    this.cam=cam;
  }
  @Override
  public void UpdateAndRender(SpriteBatch batch,float delta) {
    if(!remove.isEmpty()) {
      sons.removeAll(remove,true);
      remove.clear();
    }
    for(TestBlock be:sons) {
      if(!EntityTool.testBoundInCamera(be,cam)) {
        blockPool.free(be);
        remove.add(be);
      }
      be.update(delta);
      be.render(batch);
    }
  }
}
