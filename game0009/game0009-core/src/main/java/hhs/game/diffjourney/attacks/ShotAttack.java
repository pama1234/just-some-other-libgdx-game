package hhs.game.diffjourney.attacks;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Pool;
import com.dongbat.jbump.CollisionFilter;
import com.dongbat.jbump.Item;
import com.dongbat.jbump.Response;
import com.dongbat.jbump.World;
import hhs.game.diffjourney.entities.Character;
import hhs.game.diffjourney.entities.Protagonist;
import hhs.game.diffjourney.map.Block;
import hhs.gdx.hsgame.entities.BasicEntity;
import hhs.gdx.hsgame.entityUi.EasyLabel;
import hhs.gdx.hsgame.tools.AnimationBuilder;
import hhs.gdx.hsgame.tools.EntityTool;
import hhs.gdx.hsgame.tools.Resource;
import hhs.gdx.hsgame.tools.Updater;
import hhs.gdx.hsgame.util.Rect;
import java.util.HashSet;

public class ShotAttack extends BasicAttack{
  public Pool<Bullet> pool=new Pool<>() {
    public Bullet newObject() {
      return new Bullet();
    }
  };
  public CollisionFilter filter=new CollisionFilter() {
    @Override
    public Response filter(Item item,Item other) {
      Bullet b=((Bullet)item.userData);
      if(other.userData instanceof Protagonist) {
        return null;
      }
      if(other.userData instanceof Character c) {
        hurt(c);
        EasyLabel el=EasyLabel.newNumLabel(5,c.pos);
        el.pos.add(c.size.x/2,c.size.y/2);
        screen.addEntity(el);
        b.collided=true;
        b.time=0;
      }
      if(other.userData instanceof Block) {
        com.dongbat.jbump.Rect r=world.getRect(other);
        if(tmp.set(r.x,r.y).sub(pos).len2()<2500) return null;
        b.collided=true;
        b.time=0;
      }
      return null;
    }
  };
  public Animation<TextureRegion> blow=AnimationBuilder.createAnim(Resource.asset.get("praticle/praticle1.png"),0.01f,6,6,31);
  public Updater<Vector2> posUpdate;
  HashSet<Bullet> list=new HashSet<>();
  int index=0,i=0;
  float time;
  World<Rect> world;
  public ShotAttack(World<Rect> world) {
    this.world=world;
  }
  @Override
  public void control(float delta,Vector2 knob) {
    super.control(delta,knob);
    if(direction.isZero()) return;
    if(time<0.1f) {
      time+=delta;
      return;
    }
    time=0;
    pos.set(posUpdate.update(delta));
    direction.setLength2(1);
    Bullet b=pool.obtain();
    b.set();
    list.add(b);
  }
  @Override
  public void notControl(float delta) {}
  Vector2 tmp=new Vector2();
  @Override
  public void hurt(Character entity) {
    if(entity instanceof Character.CanBeHurt h) {
      Character c=h.getHurt(5,this);
    }
  }
  @Override
  public void dispose() {
    pool.clear();
  }
  @Override
  public void render(SpriteBatch batch) {}
  @Override
  public void UpdateAndRender(SpriteBatch batch,float delta) {
    pos.set(posUpdate.update(delta));
    for(Bullet b:list) {
      if(b==null||b.flag) continue;
      b.update(delta);
      b.render(batch);
    }
  }
  @Override
  public void debugDraw(ShapeRenderer sr) {
    super.debugDraw(sr);
    for(Bullet b:list) {
      b.debugDraw(sr);
    }
    // TODO: Implement this method
  }

  class Bullet extends BasicEntity implements Character.Attachable,Pool.Poolable{
    public Sprite texture=new Sprite(Resource.asset.get("bullet.png",Texture.class));
    float time=0;
    boolean flag=false,inWorld=false;
    Vector2 direction=new Vector2(),tmp=new Vector2();
    int speed=500;
    Item<Rect> item;
    boolean collided=false;
    public Bullet() {
      size.set(18,18);
      set();
    }
    public void set() {
      flag=false;
      collided=false;
      if(item==null) item=world.add(new Item<>(this),pos.x,pos.y,6,16);
      pos.set(ShotAttack.this.pos).add(10,10);
      direction.set(ShotAttack.this.direction);
      world.update(item,pos.x,pos.y,6,6);
    }
    @Override
    public void reset() {
      flag=true;
      time=0;
    }
    @Override
    public void dispose() {}
    public void fbt(float delta) {
      if(time<2) time+=delta;
      else {
        time=0;
        ShotAttack.this.pool.free(this);
      }
    }
    @Override
    public void update(float delta) {
      //fbt(delta);
      if(collided) {
        time+=delta;
        return;
      }
      tmp.set(direction).scl(speed*delta);
      Response.Result r;
      if(item!=null&&world.getRect(item)!=null) {
        r=world.move(item,pos.x+tmp.x,pos.y+tmp.y,filter);
      }else return;
      pos.set(r.goalX,r.goalY);
      EntityTool.setSpriteBound(texture,this);
      texture.setOrigin(size.x/2,size.y/2);
      texture.setPosition(pos.x,pos.y);
    }
    @Override
    public void render(SpriteBatch batch) {
      //if(flag) texture.setColor(Color.BLACK);
      if(!collided) {
        texture.draw(batch);
      }else {
        batch.draw(blow.getKeyFrame(time),pos.x,pos.y,50,50);
        if(time>blow.getAnimationDuration()) pool.free(this);
      }
    }
    @Override
    public void hurt(Character entity) {}
  }
  public Updater<Vector2> getPosUpdate() {
    return this.posUpdate;
  }
  public void setPosUpdate(Updater<Vector2> posUpdate) {
    this.posUpdate=posUpdate;
  }
}