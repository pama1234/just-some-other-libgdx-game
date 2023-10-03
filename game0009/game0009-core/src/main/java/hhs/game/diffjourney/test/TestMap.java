package hhs.game.diffjourney.test;

import com.badlogic.gdx.utils.Pool;
import hhs.gdx.hsgame.entities.EntityCenter;

public class TestMap extends EntityCenter<TestBlock>{
  public static Pool<TestBlock> blockPool=new Pool<>() {
    public TestBlock newObject() {
      return new TestBlock();
    }
  };
}
