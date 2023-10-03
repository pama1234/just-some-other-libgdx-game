package hhs.game.diffjourney.entities;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.dongbat.jbump.Item;
import com.dongbat.jbump.Response;
import com.dongbat.jbump.World;
import hhs.game.diffjourney.map.Collision;
import hhs.game.diffjourney.util.JsonAnimationLoader;
import hhs.gdx.hsgame.entityUi.EasyLabel;
import hhs.gdx.hsgame.tools.ImpactBox;
import hhs.gdx.hsgame.tools.Resource;
import hhs.gdx.hsgame.ui.Controller.Controlable;
import hhs.gdx.hsgame.util.Rect;
import squidpony.squidgrid.FOV;
import squidpony.squidgrid.Radius;
import squidpony.squidgrid.mapping.DungeonUtility;

public class Protagonist extends Character<Character.State,Protagonist> implements Controlable{
  int speed=200;
  boolean direct=true;
  Collision curr=null;
  public Item<Rect> info;
  Vector2 direction=new Vector2(),temp=new Vector2();
  ImpactBox tmp=new ImpactBox();
  boolean xcost=false;
  char map[][];
  static boolean flag=false;
  public double fovmap[][],res[][];
  FOV fov;
  float time=0;
  public Protagonist() {
    super(null,null);
    data.hp=100;
    data.maxHp=100;
    animation.put(JsonAnimationLoader.getAnimationSet("Mushroom.xml"));
    size.set(20*1.5f,30*1.5f);
    pos.set(0,0);
    animation.state(State.stop);
  }
  @Override
  public void control(float delta,Vector2 knob) {
    if(knob.x<0) {
      direct=false;
    }else {
      direct=true;
    }
    direction.set(knob.x*speed*delta,knob.y*speed*delta);
    if(animation.curr!=State.hurt) animation.state(State.walk);
    // pos.add(direction.x, direction.y);
    if(curr!=null) {
      World<Rect> world=curr.getCollisions();
      temp.set(pos).add(direction);
      Response.Result r=world.move(info,temp.x,temp.y,normal);
      pos.set(r.goalX,r.goalY);
    }
  }
  @Override
  public void notControl(float delta) {
    if(animation.curr!=State.hurt) animation.state(State.stop);
  }
  Vector2 tp=new Vector2();
  public void hit(Character c) {
    direct=tp.set(c.pos).scl(pos).x>0?false:true;
    animation.state(State.hurt);
  }
  @Override
  public void dispose() {}
  @Override
  public void update(float delta) {
    rect.set(pos.x,pos.y,size.x,size.y);
    machine.update(delta);
    animation.update(delta);
    TextureRegion tmp=animation.get();
    tr=tmp==null?tr:tmp;
    if(autoFilp) {
      if(tr.isFlipX()) {
        if(direct) {
          tr.flip(true,false);
        }
      }else {
        if(!direct) {
          tr.flip(true,false);
        }
      }
    }
    if(animation.curr==State.hurt&&animation.loop) {
      animation.state(State.stop);
    }
  }
  Vector2 quiver=new Vector2();
  @Override
  public void render(SpriteBatch batch) {
    if(tr!=null) batch.draw(tr,pos.x-2*size.x,pos.y-size.y,size.x*5,size.x*5);
  }
  public void newNumLabel(int num) {
    EasyLabel el=EasyLabel.pool.obtain().set(Resource.font.newFont(32,Color.RED),num+"");
    el.setPosition(pos).add(size.x/2,size.y/2);
    el.size.set(size.x/2,size.y/2);
    el.screen=screen;
    screen.addEntity(el);
  }
  public Collision getCurr() {
    return this.curr;
  }
  public void setCurr(Collision curr) {
    this.curr=curr;
    info=curr.getCollisions().add(new Item<Rect>(this),pos.x,pos.y,size.x,size.y);
  }
  public char[][] getMap() {
    return this.map;
  }
  public void setMap(char[][] map) {
    this.map=map;
    fov=new FOV(FOV.RIPPLE_TIGHT);
    res=DungeonUtility.generateResistances(map);
    fovmap=new double[map.length][map[0].length];
    FOV.reuseFOV(res,fovmap,(int)pos.x,(int)pos.y,9.0,Radius.CIRCLE);
    new Thread() {
      @Override
      public void run() {
        while(true) {
          if(flag) {
            flag=false;
            fovmap=fov.calculateFOV(res,(int)pos.x/50,(int)pos.y/50);
          }
        }
      }
    };
    int i,j;
    for(i=map.length/2;i<map.length;i++) {
      for(j=map[i].length/2;j<map[i].length;j++) {
        if(map[i][j]!='#') {
          pos.set(i*50,j*50);
          return;
        }
      }
    }
  }
}
