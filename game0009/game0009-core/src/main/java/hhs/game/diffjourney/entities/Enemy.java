package hhs.game.diffjourney.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import hhs.game.diffjourney.map.Collision;
import hhs.game.diffjourney.util.JsonAnimationLoader;
import hhs.gdx.hsgame.tools.EntityTool;
import squidpony.squidmath.AStarSearch;
import squidpony.squidmath.Coord;
import java.util.ArrayList;

public class Enemy extends Character<Enemy.EnemyState,Enemy>
  implements Character.Attachable,Character.CanBeHurt<Enemy>{
  Vector2 tmp=new Vector2();
  int speed=100;
  // 单位：秒
  float time=0,ftime=0,catchTime=0;
  boolean move=false;
  ArrayList<Coord> proPath=new ArrayList<>(1);
  AStarSearch search;
  Coord next;
  public Enemy(Collision c,Protagonist pro,AStarSearch map) {
    super(c,pro);
    search=map;
    animation=JsonAnimationLoader.getAnimationSet("Mushroom.xml");
    machine.set(EnemyState.find,(d,e,p)-> {
      victroy.set(0,0);
      ftime+=d;
      if(proPath.isEmpty()&&ftime>1) {
        ftime=0;
        proPath=search.path((int)pos.x/50,(int)pos.y/50,(int)p.pos.x/50,(int)p.pos.y/50);
        machine.state(EnemyState.attack);
      }
      // if (tmp.set(p.pos).sub(pos).len2() < 500 * 500)
    });
    machine.set(EnemyState.attack,(d,e,p)-> {
      hurt(p);
      if(tmp.set(p.pos).sub(pos).len2()>10000&&proPath.isEmpty()) machine.state(EnemyState.find);
      victroy.set(0,0);
    });
    // machine.setExit(EnemyState.attack, (e, p) -> victroy.set(0,0));
    machine.state(EnemyState.find);
    autoFilp=false;
  }
  @Override
  public void update(float delta) {
    super.update(delta);
    if(machine.currState==EnemyState.attack) {
      catchTime+=delta;
      if(catchTime>=0.5f&&!proPath.isEmpty()) {
        catchTime=0;
        next=proPath.remove(0);
      }
      if(state!=State.attack&&next!=null) moveTo(lerp(pos.x,next.getX()*50,catchTime/0.5f),
        lerp(pos.y,next.getY()*50,catchTime/0.5f));
    }
    time+=delta;
  }
  float lerp(float s,float e,float time) {
    return s+(e-s)*time;
  }
  void moveTo(float x,float y) {
    state=State.walk;
    if(x<pos.x) {
      direct=false;
    }else {
      direct=true;
    }
    pos.set(x,y);
  }
  @Override
  public void render(SpriteBatch batch) {
    if(EntityTool.testBoundInCamera(this,cam)) batch.draw(animation.get(),pos.x-2*size.x,pos.y-size.y,size.x*5,size.x*5);
  }
  public void hurt(Character c) {
    Protagonist pro=((Protagonist)c);
    if(time>animation.getAnimTime()) {
      time=0;
      if(rect.overlaps(Rectangle.tmp.set(pro.pos.x,pro.pos.y,pro.size.x,pro.size.y))) {
        state=State.attack;
        move=false;
        if(pro.autoFilp) pro.hit(this);
        pro.data.hp--;
        pro.newNumLabel(1);
      }else {
        move=true;
        if(state!=State.stop&&state!=State.walk) state=State.stop;
      }
    }
  }
  public Enemy getHurt() {
    animation.state(State.hurt);
    return this;
  }
  public static enum EnemyState{
    find,attack
  }
}
