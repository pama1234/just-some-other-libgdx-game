package hhs.game.diffjourney.entities;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Pool;
import com.badlogic.gdx.utils.Pools;
import hhs.game.diffjourney.util.XmlAnimationLoader;
import hhs.gdx.hsgame.tools.AnimationSet;

public class Mushroom extends Enemy1{
  public static Pool<Mushroom> pool=new Pool<>() {
    @Override
    public Mushroom newObject() {
      return new Mushroom();
    }
  };
  public static AnimationSet<Character.State,TextureRegion> animData=XmlAnimationLoader.getAnimationSet("Mushroom.xml");
  static {
    Pools.set(Mushroom.class,pool);
  }
  public static AnimationSet<Character.State,TextureRegion> init() {
    var anim=new AnimationSet<Character.State,TextureRegion>();
    anim.setData(animData);
    return anim;
  }
  public Mushroom() {
    super(init());
    size.set(20*1.5f,30*1.5f).scl(2);
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
