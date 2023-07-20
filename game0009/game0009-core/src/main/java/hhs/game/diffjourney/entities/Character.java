package hhs.game.diffjourney.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import hhs.game.diffjourney.map.Block;
import hhs.game.diffjourney.map.Collision;
import hhs.gdx.hsgame.entitys.RectEntity;
import hhs.gdx.hsgame.tools.AnimationBuilder;
import hhs.gdx.hsgame.tools.AnimationSet;
import hhs.gdx.hsgame.tools.ImpactBox;
import hhs.gdx.hsgame.tools.Resourse;
import hhs.gdx.hsgame.ui.Controller;
import squidpony.squidgrid.FOV;
import squidpony.squidgrid.Radius;
import squidpony.squidgrid.mapping.DungeonUtility;

/**
 * 玩家
 */
public class Character extends RectEntity implements Controller.Controlable{
  int speed=200;
  boolean direct=true;
  AnimationSet<State,TextureRegion> anim;
  Collision curr=null;
  Vector2 direction=new Vector2();
  ImpactBox boxCache=new ImpactBox();
  boolean flagx,flagy;
  char map[][];
  static boolean flag=false;
  public double fovmap[][],res[][];
  FOV fov;
  public Character() {
    anim=new AnimationSet<>();
    anim.set(
      State.stop,
      AnimationBuilder.createAnim(Resourse.asset.get("Mushroom/Idle.png"),.4f,1,4));
    anim.set(
      State.run,AnimationBuilder.createAnim(Resourse.asset.get("Mushroom/Run.png"),.2f,1,8));
    anim.state(State.stop);
    size.set(20*1.5f,30*1.5f);
    pos.set(0,0);
  }
  @Override
  public void control(float delta,Vector2 knob) {
    if(knob.x<0) {
      direct=false;
    }else {
      direct=true;
    }
    direction.set(knob.x*speed*delta,knob.y*speed*delta);
    anim.state(State.run);
    // pos.add(direction.x, direction.y);
    if(curr!=null) {
      flag=true;
      boxCache.setSize(size.x,size.y);
      boxCache.setPosition(pos.x+direction.x,pos.y);
      Array<Block> testList=curr.getCollisions(this);
      for(Block b:testList) {
        if(boxCache.overlaps(b.rect)) {
          flagx=true;
        }
      }
      if(!flagx) {
        pos.add(direction.x,0);
      }else flagx=false;
      boxCache.setPosition(pos.x,pos.y+direction.y);
      testList=curr.getCollisions(boxCache);
      for(Block b:testList) {
        if(boxCache.overlaps(b.rect)) {
          // return;
          flagy=true;
        }
      }
      if(!flagy) {
        pos.add(0,direction.y);
      }else flagy=false;
    }
  }
  @Override
  public void dispose() {}
  @Override
  public void render(SpriteBatch batch) {
    anim.update(Gdx.graphics.getDeltaTime());
    TextureRegion tr=anim.get();
    if(tr.isFlipX()) {
      if(direct) {
        tr.flip(true,false);
      }
    }else {
      if(!direct) {
        tr.flip(true,false);
      }
    }
    // if(tr==null) return;
    batch.draw(tr,pos.x-2*size.x,pos.y-size.y,size.x*5,size.x*5);
    anim.state(State.stop);
  }
  enum State{
    run,
    walk,
    stop
  }
  @Override
  public void update(float delta) {
    super.update(delta);
  }
  public Collision getCurr() {
    return this.curr;
  }
  public void setCurr(Collision curr) {
    this.curr=curr;
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
            fovmap=fov.calculateFOV(res,(int)pos.x/Block.tileWidth,(int)pos.y/Block.tileHeight);
          }
        }
      }
    }.start();
    int i,j;
    for(i=map.length/2;i<map.length;i++) {
      for(j=map[i].length/2;j<map[i].length;j++) {
        if(map[i][j]!='#') {
          pos.set(i*Block.tileWidth,j*Block.tileHeight);
          return;
        }
      }
    }
  }
}
