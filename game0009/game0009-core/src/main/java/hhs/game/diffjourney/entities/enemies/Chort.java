package hhs.game.diffjourney.entities.enemies;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Pool;
import com.badlogic.gdx.utils.Pools;
import hhs.game.diffjourney.entities.Character;
import hhs.game.diffjourney.entities.Enemy1;
import hhs.gdx.hsgame.tools.AnimationSet;
import hhs.gdx.hsgame.tools.Resource;
import hhs.gdx.hsgame.tools.TextureTool;

public class Chort extends Enemy1{
  public static Pool<Chort> pool=new Pool<>() {
    @Override
    public Chort newObject() {
      return new Chort();
    }
  };
  public static AnimationSet<Character.State,TextureRegion> animData=new AnimationSet<>();
  static {
    Pools.set(Chort.class,pool);
    TextureAtlas atlas=Resource.asset.get("textures/character.atlas");

    TextureRegion idle[]=TextureTool.getLoop(atlas,"chort_idle_anim_f",4);
    TextureRegion run[]=TextureTool.getLoop(atlas,"chort_run_anim_f",4);
    // walk,idle,attack,hurt,death
    animData.set(Character.State.death,.15f,idle);
    animData.set(Character.State.walk,.15f,run);
    animData.set(Character.State.idle,.15f,idle);
    animData.set(Character.State.hurt,.15f,idle);
    animData.set(Character.State.attack,.15f,run);
    animData.setPlayMode(Animation.PlayMode.LOOP);
  }
  public static AnimationSet<Character.State,TextureRegion> init() {
    var anim=new AnimationSet<Character.State,TextureRegion>();
    anim.setData(animData);
    return anim;
  }
  public Chort() {
    super(init());
    size.set(32,48);
    speed=200;
    data.maxHp=50;
    data.hp=50;
  }
  @Override
  public void remove() {
    pool.free(this);
  }
}
