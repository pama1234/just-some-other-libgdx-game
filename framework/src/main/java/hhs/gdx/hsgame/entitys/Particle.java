package hhs.gdx.hsgame.entitys;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Pool;
import hhs.gdx.hsgame.tools.EntityTool;

public class Particle extends BasicEntity implements Pool.Poolable{
  ParticleEntity.Action a;
  Sprite s;
  float time=0;
  Vector2 ran=new Vector2();
  Vector2 csize,cpos;
  float residence;
  Pool<Particle> pool;
  EntityCenter<Particle> ec;
  public Particle() {}
  public void set(
    EntityCenter<Particle> ec,
    Pool<Particle> pool,
    TextureRegion t,
    Vector2 cpos,
    Vector2 csize,
    float residence,
    ParticleEntity.Action ac) {
    this.cpos=cpos;
    this.ec=ec;
    this.pool=pool;
    a=ac;
    this.residence=residence;
    this.csize=csize;
    if(s==null) {
      s=new Sprite(t);
    }
    size.set(50,50);
    pos.set(cpos);
    ran.set(MathUtils.random(0,(int)csize.x),MathUtils.random(0,(int)csize.y));
  }
  public void reset() {
    time=0;
    ran.set(MathUtils.random(0,(int)csize.x),MathUtils.random(0,(int)csize.y));
  }
  @Override
  public void dispose() {}
  protected void free() {
    pool.free(this);
    ec.remove(this);
  }
  @Override
  public void update(float delta) {
    if((time+=delta)>residence) {
      free();
    }
    pos.set(cpos).add(ran);
    a.action(this,delta);
    s.setAlpha(Math.abs(residence-time)/residence);
    EntityTool.setSpriteBound(s,this);
  }
  @Override
  public void render(SpriteBatch batch) {
    s.draw(batch);
  }
}
