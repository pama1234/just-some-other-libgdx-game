package hhs.game.diffjourney.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Pool;
import com.badlogic.gdx.utils.Pools;

public class TestEnemy1 extends Enemy1{
  public static Pool<TestEnemy1> pool=new Pool<>() {
    @Override
    public TestEnemy1 newObject() {
      return new TestEnemy1();
    }
  };
  static {
    Pools.set(TestEnemy1.class,pool);
  }
  public TestEnemy1() {
    super(Gdx.files.internal("test.xml"));
    setSize(size.x*4,size.y*4);
    setTransformer((p,s)-> {
      p.set(pos.x-2*size.x,pos.y-size.y);
      s.set(size.x*5,size.x*5);
    });
  }
  @Override
  public void remove() {
    pool.free(this);
  }
}
