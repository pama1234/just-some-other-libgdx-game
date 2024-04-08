package hhs.game.airplane;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Pool;
import hhs.gdx.hsgame.entities.BasicEntity;
import hhs.gdx.hsgame.entities.EntityLayers;
import hhs.gdx.hsgame.tools.Resource;

public class EnemyPlane extends BasicEntity implements Pool.Poolable,EntityLayers.Stackable{

  public static Pool<EnemyPlane> pool=new Pool<EnemyPlane>() {
    @Override
    public EnemyPlane newObject() {
      return new EnemyPlane();
    }
  };
  static Animation<Texture> anim;
  static Texture texture;
  public boolean ishit=false;
  float speed=100;
  float time=0;

  static {
    Texture[] text=new Texture[4];
    for(int i=1;i<=4;i++) {
      text[i-1]=Resource.asset.get("di"+i+".png");
    }
    texture=text[0];
    anim=new Animation<Texture>(0.1f,text);
  }

  public EnemyPlane() {
    reset();
  }

  @Override
  public void dispose() {}

  @Override
  public void update(float delta) {
    super.update(delta);
    pos.y-=speed*delta;
    if(pos.y+size.y<0) {
      pool.free(this);
      screen.removeEntity(this);
    }
    if(ishit&&(time+=delta)>anim.getAnimationDuration()) {
      pool.free(this);
      screen.removeEntity(this);
    }
  }

  public void hit() {
    ishit=true;
  }

  @Override
  public void render(SpriteBatch batch) {
    if(!ishit) batch.draw(texture,pos.x,pos.y,size.x,size.y);
    else batch.draw(anim.getKeyFrame(time),pos.x,pos.y,size.x,size.y);
  }

  @Override
  public void reset() {
    ishit=false;
    time=0;
    speed=MathUtils.random(200,250);
    size.set(100,100);
    pos.set(MathUtils.random(0,Resource.width-size.x),Resource.height+size.y);
  }
}
