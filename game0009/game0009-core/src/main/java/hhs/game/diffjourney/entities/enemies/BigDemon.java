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

public class BigDemon extends Enemy1{
  public static Pool<BigDemon> pool=new Pool<>() {
    @Override
    public BigDemon newObject() {
      return new BigDemon();
    }
  };
  public static AnimationSet<Character.State,TextureRegion> animData=new AnimationSet<>();
  static {
    Pools.set(BigDemon.class,pool);
    TextureAtlas atlas=Resource.asset.get("textures/character.atlas");

    TextureRegion idle[]=TextureTool.getLoop(atlas,"big_demon_idle_anim_f",4);
    TextureRegion run[]=TextureTool.getLoop(atlas,"big_demon_run_anim_f",4);
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
  public BigDemon() {
    super(init());
    size.set(64,72);
    data.maxHp=300;
    data.hp=300;
    speed=100;
  }
  @Override
  public void remove() {
    pool.free(this);
  }
}
