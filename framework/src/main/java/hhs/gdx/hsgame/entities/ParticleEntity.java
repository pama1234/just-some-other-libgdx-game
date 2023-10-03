package hhs.gdx.hsgame.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Pool;
import com.badlogic.gdx.utils.Pools;

public class ParticleEntity extends EntityCenter<Particle>{
  TextureRegion texture;
  Action action; // 动作
  float time=0;
  float interval=.01f; // 每次生成后等待的时间
  float residence=1f; // 停留时间
  static boolean setPool=true;
  Pool<Particle> pool;
  public ParticleEntity(Texture t) {
    this(new TextureRegion(t));
  }
  public ParticleEntity(TextureRegion texture) {
    this.texture=texture;
    setPool();
  }
  public ParticleEntity(Texture t,float interval,float residence) {
    this(new TextureRegion(t),interval,residence);
  }
  public ParticleEntity(TextureRegion texture,float interval,float residence) {
    this.texture=texture;
    this.interval=interval;
    this.residence=residence;
    setPool();
  }
  void setPool() {
    // if(setPool){
    pool=Pools.get(Particle.class);
    //			setPool = false;
    //			return;
    //		}
    //		pool = Pools.get(Particle.class);
  }
  @Override
  public void dispose() {
    pool.clear();
  }
  @Override
  public void update(float delta) {
    if((time+=delta)>interval) {
      time=0;
      Particle p=pool.obtain();
      p.set(this,pool,texture,pos,size,residence,action);
      add(p);
    }
  }
  public static interface Action{
    public abstract void action(Particle p,float delta);
  }
  public Action getAction() {
    return this.action;
  }
  public void setAction(Action action) {
    this.action=action;
  }
  public float getInterval() {
    return this.interval;
  }
  public void setInterval(float interval) {
    this.interval=interval;
  }
  public float getResidence() {
    return this.residence;
  }
  public void setResidence(float residence) {
    this.residence=residence;
  }
  @Override
  public void UpdateAndRender(SpriteBatch batch,float delta) {
    update(delta);
    super.UpdateAndRender(batch,delta);
  }
}
