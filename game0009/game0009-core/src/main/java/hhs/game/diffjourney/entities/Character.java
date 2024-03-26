package hhs.game.diffjourney.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.dongbat.jbump.CollisionFilter;
import com.dongbat.jbump.Item;
import com.dongbat.jbump.Response;
import hhs.game.diffjourney.map.Block;
import hhs.game.diffjourney.map.Collision;
import hhs.gdx.hsgame.entities.BasicEntity;
import hhs.gdx.hsgame.entities.Entity;
import hhs.gdx.hsgame.entities.EntityLayers;
import hhs.gdx.hsgame.tools.AnimationSet;
import hhs.gdx.hsgame.tools.EntityTool;
import hhs.gdx.hsgame.util.StateMchine;

/*
 * Attack Attachable.hurt(Character)->Character CanBeHurt.getHurt(hurt)
 */
public class Character<S,T extends Entity>extends BasicEntity implements EntityLayers.Stackable{
  public static CollisionFilter normal=new CollisionFilter() {
    @Override
    public Response filter(Item item,Item other) {
      if(other.userData instanceof Block) {
        return Response.slide;
      }
      return Response.cross;
    }
  };
  public static Transformer defaultTransformer=(p,s)-> {};
  protected Vector2 dpos=new Vector2(),dsize=new Vector2();
  public CharacterInfo data=new CharacterInfo();
  public StateMchine<S,T,Protagonist> machine;
  public AnimationSet<State,TextureRegion> animation;
  public Vector2 victroy=new Vector2();
  public boolean direct=false,autoFilp=true;
  public Rectangle rect=new Rectangle();
  public TextureRegion tr;
  public State state=State.idle;
  Collision c;
  Protagonist pro;
  public Character() {
    this.animation=new AnimationSet<>();
  }
  public Character(Collision c,Protagonist pro) {
    this();
    this.c=c;
    this.pro=pro;
    machine=new StateMchine<>(((T)this),pro);
  }
  public void set(Collision c,Protagonist pro) {
    this.c=c;
    this.pro=pro;
    if(machine==null) machine=new StateMchine<>(((T)this),pro);
  }
  @Override
  public void dispose() {}
  @Override
  public void update(float delta) {
    super.update(delta);
    rect.set(pos.x,pos.y,size.x,size.y);
    machine.update(delta);
    animation.state(state);
    animation.update(delta);
    TextureRegion tmp=animation.get();
    tr=tmp==null?tr:tmp;
    if(autoFilp) {
      if(!victroy.isZero()) {
        state=State.walk;
      }else state=State.idle;
      if(victroy.x<0) {
        direct=false;
      }else {
        direct=true;
      }
    }
    if(tr.isFlipX()) {
      if(direct) {
        tr.flip(true,false);
      }
    }else {
      if(!direct) {
        tr.flip(true,false);
      }
    }
    pos.add(victroy);

    dpos.set(pos);
    dsize.set(size);
  }
  public void remove() {}
  @Override
  public void render(SpriteBatch batch) {
    if(EntityTool.testBoundInCamera(this,cam)) {
      defaultTransformer.transform(dpos,dsize);
      batch.draw(tr,dpos.x,dpos.y,dsize.x,dsize.y);
    }
  }
  public static enum State{
    walk,idle,attack,hurt,death
  }
  public interface Attachable{
    public void hurt(Character entity);
  }
  public interface CanBeHurt<E extends Character>{
    public E getHurt(float damage,Attachable attack);
  }
  public State getState() {
    return this.state;
  }
  public void setState(State state) {
    this.state=state;
  }
  public static interface Transformer{
    public void transform(Vector2 pos,Vector2 size);
  }
}
