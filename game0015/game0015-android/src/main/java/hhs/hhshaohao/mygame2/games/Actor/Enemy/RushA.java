package hhs.hhshaohao.mygame2.games.Actor.Enemy;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Shape;

import hhs.hhshaohao.mygame2.games.Actor.BasicEnemyActor;
import hhs.hhshaohao.mygame2.games.Stage.WorldStage;
import hhs.hhshaohao.mygame2.games.Actor.PlayerActor;
import hhs.hhshaohao.mygame2.games.ActiveObject.TouchAble;
import com.badlogic.gdx.utils.Pools;
import hhs.hhshaohao.mygame2.games.MyGame;

public class RushA extends BasicEnemyActor implements TouchAble{

  @Override
  public void reset() {
    hp=ohp;
    body.setActive(false);
  }

  static final Texture my=new Texture("enemy1.png");

  @Override
  public void start(PlayerActor pa) {
    pa.body.applyForceToCenter(pa.body.getPosition().sub(body.getPosition()).nor().scl(100),true);
    pa.hp--;
  }

  @Override
  public void end(PlayerActor pa) {

  }

  static Shape sh() {
    CircleShape shape=new CircleShape();
    shape.setRadius(10);
    return shape;
  }

  public RushA(WorldStage world,Vector2 pos,float hp) {
    super(world,my,pos,hp,sh());
    setSize(20,20);
  }

  @Override
  public void find(Body body,float delta) {
    body.setLinearVelocity(
      body.getPosition()
        .sub(getWorldStage().pa.body.getPosition())
        .nor()
        .scl(-2000*delta));
  }

  @Override
  public void cheakHurt(float t) {
    if(ws.getAttack().cheakHurt(polygon)&&(time+=t)>ws.getAttack().wait) {
      ws.getAttack().hurt(this,getStage());
      time=0;
    }
    if(hp<=0) {
      MyGame.res.ngm.play();
      body.setActive(false);
      ((WorldStage)getStage()).kill+=1;
      Pools.get(RushA.class).free(this);
      remove();
    }
  }

  @Override
  public boolean isWalk(Body body) {
    if(getWorldStage().pa.body.getPosition().sub(getX(),getY()).len()<50) {
      return false;
    }
    return true;
  }

  @Override
  public void walk(Body body,float delta) {
    body.setLinearVelocity(
      body.getPosition()
        .sub(getWorldStage().pa.body.getPosition())
        .nor()
        .scl(-1000*delta));
  }
}
