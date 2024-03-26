package hhs.game.diffjourney.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Pool;
import com.dongbat.jbump.Item;
import com.dongbat.jbump.Response;
import com.dongbat.jbump.World;
import hhs.game.diffjourney.attacks.BasicAttack;
import hhs.game.diffjourney.item.MedicalKit;
import hhs.game.diffjourney.map.Collision;
import hhs.game.diffjourney.shaders.FlashShader;
import hhs.game.diffjourney.util.XmlAnimationLoader;
import hhs.gdx.hsgame.entityUi.PercentageDisplay;
import hhs.gdx.hsgame.tools.AnimationSet;
import hhs.gdx.hsgame.tools.EntityTool;
import hhs.gdx.hsgame.util.Rect;
import squidpony.squidmath.Coord;

public class Enemy1 extends Character<Enemy1.Enemy1State,Enemy1>
  implements Character.Attachable,Character.CanBeHurt<Enemy1>,Pool.Poolable{
  PercentageDisplay hpViewer=new PercentageDisplay(()->(data.hp/(float)data.maxHp));
  public static FlashShader fshader=new FlashShader();
  public Character.Transformer transformer=Character.defaultTransformer;
  Vector2 tmp=new Vector2();
  Rectangle rtmp=new Rectangle();
  Coord next;
  World<Rect> world;
  Item<Rect> info;
  public int speed=150;
  // 单位：秒
  float time=0,ftime=0;
  boolean move=true,first=true;

  public Enemy1(FileHandle xmlAnimFile) {
    this(XmlAnimationLoader.getAnimationSet(xmlAnimFile));
  }

  public Enemy1(AnimationSet anim) {
    data.hp=100;
    data.maxHp=100;
    hpViewer.size.set(size.x,size.y/10);
    animation=anim;
    animation.getAnimation(State.death).setPlayMode(Animation.PlayMode.NORMAL);
  }

  public Enemy1(AnimationSet anim,Collision c,Protagonist pro) {
    this(anim);
    set(c,pro);
    state=State.idle;
  }

  public void reset() {
    data.hp=100;
  }

  @Override
  public void set(Collision c,Protagonist pro) {
    super.set(c,pro);
    state=State.idle;
    world=c.getCollisions();
    if(!first) {
      machine.state(Enemy1State.find);
      return;
    }
    machine.set(
      Enemy1State.find,
      (d,e,p)-> {
        victroy.set(0,0);
        state=State.idle;
        if(tmp.set(p.pos).sub(pos).len2()<500000) {
          machine.state(Enemy1State.attack);
        }
        // if (tmp.set(p.pos).sub(pos).len2() < 500 * 500)
      });
    machine.set(
      Enemy1State.attack,
      (d,e,p)-> {
        hurt(p);
        if(tmp.set(p.pos).sub(pos).len2()>500000) machine.state(Enemy1State.find);
        if(move&&info!=null) {
          victroy.set(tmp.set(pro.pos).sub(pos).nor().scl(speed*d));
          tmp.set(pos).add(victroy);
          Response.Result result=world.move(info,tmp.x,tmp.y,normal);
          pos.set(result.goalX,result.goalY);
          if(state==State.idle) state=State.walk;
        }else {
          victroy.set(0,0);
        }
      });
    first=false;
    machine.state(Enemy1State.find);
    // machine.setExit(Enemy1State.attack,a(e, p) -> victroy.set(0,0));
  }

  public void setInfo() {
    if(info==null) info=world.add(new Item<>(this),pos.x,pos.y,40,40);
  }

  @Override
  public void update(float delta) {
    rect.set(pos.x,pos.y,size.x,size.y);
    machine.update(delta);
    animation.state(state);
    animation.update(delta);
    hpViewer.update(delta);
    hpViewer.pos.set(pos).sub(0,size.y/10);
    hpViewer.size.set(size.x,size.y/10);
    TextureRegion tmp=animation.get();
    tr=tmp;
    if(tr.isFlipX()) {
      if(direct) {
        tr.flip(true,false);
      }
    }else {
      if(!direct) {
        tr.flip(true,false);
      }
    }
    if(victroy.x<0) {
      direct=false;
    }else {
      direct=true;
    }
    time+=delta;
    if(state==State.hurt&&animation.loop) {
      state=State.idle;
    }
    if(state==State.death&&animation.loop) {
      MedicalKit m=MedicalKit.pool.obtain();
      m.set(pro,null);
      m.setPosition(pos.x,pos.y);
      m.setSize(25,25);
      screen.addEntity(m);
      remove();
      // Pools.get(Enemy1.class).free(this);
      screen.removeEntity(this);
    }
  }

  @Override
  public void render(SpriteBatch batch) {
    if(EntityTool.testBoundInCamera(this,cam)) {
      if(state==State.hurt) {

      }
      fshader.setTime(ftime+=Gdx.graphics.getDeltaTime());
      batch.setShader(fshader.program);
      dpos.set(pos);
      dsize.set(size);
      transformer.transform(dpos,dsize);
      if(tr!=null) batch.draw(tr,dpos.x,dpos.y,dsize.x,dsize.y);
      batch.setShader(null);
      if(state!=State.death) hpViewer.render(batch);
    }
  }

  public void hurt(Character c) {
    if(state==State.death) return;
    Protagonist pro=((Protagonist)c);
    if(time>0.6f) {
      time=0;
      if(rect.overlaps(Rectangle.tmp.set(pro.pos.x,pro.pos.y,pro.size.x,pro.size.y))) {
        state=State.attack;
        move=false;
        pro.getHurt(1,this);
      }else {
        move=true;
        if(state!=State.hurt&&state!=State.death) state=State.idle;
      }
    }
  }

  public Enemy1 getHurt(float damage,Attachable attack) {
    if(state==State.death) return this;
    state=State.hurt;
    ftime=0;
    data.hp-=damage;
    if(data.hp<=0) {
      move=false;
      world.remove(info);
      info=null;
      state=State.death;
    }else if(attack instanceof BasicAttack ba) {
      pos.add(tmp.set(pos).sub(ba.pos).nor().scl(10));
    }
    return this;
  }

  @Override
  public void UpdateAndRender(SpriteBatch batch,float delta) {
    if(EntityTool.testBoundInCamera(this,cam)) super.UpdateAndRender(batch,delta);
  }

  public static enum Enemy1State{
    find,
    attack
  }

  public Transformer getTransformer() {
    return this.transformer;
  }

  public void setTransformer(Transformer transformer) {
    this.transformer=transformer;
  }

  @Override
  public void setSize(float w,float h) {
    if(world==null) {
      size.set(w,h);
    }else {
      world.remove(info);
      info=null;
      setInfo();
    }
  }
}
