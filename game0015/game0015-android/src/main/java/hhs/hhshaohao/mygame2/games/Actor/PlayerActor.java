package hhs.hhshaohao.mygame2.games.Actor;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.physics.box2d.World;

import hhs.hhshaohao.mygame2.games.Constant;
import hhs.hhshaohao.mygame2.games.Tool;

public class PlayerActor extends BasicRoleActor{

  @Override
  public void start(PlayerActor pa) {}

  @Override
  public void end(PlayerActor pa) {}

  @Override
  public boolean isWalk(Body body) {
    return false;
  }

  @Override
  public void find(Body body,float delta) {}

  @Override
  public void walk(Body body,float delta) {}

  public Vector2 pos;
  public float size=10;
  public float ohp,hp;

  Texture t;

  public PlayerActor(World world,Vector2 pos,Texture t,Shape a) {
    super(world,t,pos,a,Constant.player,Constant.wall|Constant.role);
    setFdefData(this);

    this.t=t;
    this.pos=pos;

    ohp=hp=Integer.MAX_VALUE;
  }

  public PlayerActor(World world,Vector2 pos,Texture t,float w,float h) {
    this(world,pos,t,Tool.rectShape(w,h));
    setWidth(w);
    setHeight(h);
  }

  public PlayerActor(World world,Vector2 pos,Texture t,float r) {
    this(world,pos,t,Tool.circleShape(r));
    setSize(r*2,r*2);
  }

  @Override
  public void act(float delta) {
    super.act(delta);
  }

  @Override
  public void setPosition(float x,float y) {
    body.setTransform(x,y,body.getAngle());
  }

  @Override
  public void draw(Batch batch,float parentAlpha) {
    super.draw(batch,parentAlpha);
  }
}
