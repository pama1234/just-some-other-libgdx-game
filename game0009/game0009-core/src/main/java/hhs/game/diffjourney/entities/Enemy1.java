package hhs.game.diffjourney.entities;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Pool;
import com.badlogic.gdx.utils.Pools;
import com.dongbat.jbump.Item;
import com.dongbat.jbump.Response;
import com.dongbat.jbump.World;
import hhs.game.diffjourney.map.Collision;
import hhs.game.diffjourney.util.JsonAnimationLoader;
import hhs.gdx.hsgame.entityUi.PercentageDisplay;
import hhs.gdx.hsgame.tools.EntityTool;
import hhs.gdx.hsgame.util.Rect;
import squidpony.squidmath.Coord;

public class Enemy1 extends Character<Enemy1.Enemy1State,Enemy1>
  implements Character.Attachable,Character.CanBeHurt<Enemy1>,Pool.Poolable{
  PercentageDisplay hpViewer=new PercentageDisplay(()->(data.hp/(float)data.maxHp));
  Vector2 tmp=new Vector2();
  Rectangle rtmp=new Rectangle();
  Coord next;
  World<Rect> world;
  Item<Rect> info;
  int speed=150;
  // 单位：秒
  float time=0;
  boolean move=true,first=true;
  public Enemy1() {
    data.hp=100;
    data.maxHp=100;
    hpViewer.size.set(size.x,size.y/10);
    animation=JsonAnimationLoader.getAnimationSet("Mushroom.xml");
    animation.getAnimation(State.death).setPlayMode(Animation.PlayMode.NORMAL);
  }
  public void reset() {
    data.hp=100;
  }
  public Enemy1(Collision c,Protagonist pro) {
    this();
    set(c,pro);
    state=State.stop;
  }
  @Override
  public void set(Collision c,Protagonist pro) {
    super.set(c,pro);
    state=State.stop;
    world=c.getCollisions();
    if(!first) {
      machine.state(Enemy1State.find);
      return;
    }
    machine.set(Enemy1State.find,(d,e,p)-> {
      victroy.set(0,0);
      if(tmp.set(p.pos).sub(pos).len2()<500000) {
        machine.state(Enemy1State.attack);
      }
      // if (tmp.set(p.pos).sub(pos).len2() < 500 * 500)
    });
    machine.set(Enemy1State.attack,(d,e,p)-> {
      hurt(p);
      if(tmp.set(p.pos).sub(pos).len2()>500000) machine.state(Enemy1State.find);
      if(move&&info!=null) {
        World<Rect> b=c.getCollisions();
        victroy.set(tmp.set(pro.pos).sub(pos).nor().scl(speed*d));
        tmp.set(pos).add(victroy);
        Response.Result result=world.move(info,tmp.x,tmp.y,normal);
        pos.set(result.goalX,result.goalY);
        if(state==State.stop) state=State.walk;
      }else {
        victroy.set(0,0);
      }
    });
    first=false;
    machine.state(Enemy1State.find);
    // machine.setExit(Enemy1State.attack,a(e, p) -> victroy.set(0,0));
  }
  public void setInfo() {
    info=world.add(new Item<Rect>(this),pos.x,pos.y,40,40);
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
    tr=tmp==null?tr:tmp;
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
      state=State.stop;
    }
    if(state==State.death&&animation.loop) {
      Pools.get(Enemy1.class).free(this);
      screen.removeEntity(this);
    }
  }
  @Override
  public void render(SpriteBatch batch) {
    if(EntityTool.testBoundInCamera(this,cam)) {
      if(tr!=null) batch.draw(tr,pos.x-2*size.x,pos.y-size.y,size.x*5,size.x*5);
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
        if(pro.autoFilp) pro.hit(this);
        pro.data.hp--;
        pro.newNumLabel(1);
      }else {
        move=true;
        if(state!=State.hurt&&state!=State.death) state=State.stop;
      }
    }
  }
  public Enemy1 getHurt() {
    if(state==State.death) return this;
    state=State.hurt;
    data.hp-=5;
    if(data.hp<=0) {
      move=false;
      world.remove(info);
      info=null;
      state=State.death;
    }
    return this;
  }
  public static enum Enemy1State{
    find,attack
  }
}
