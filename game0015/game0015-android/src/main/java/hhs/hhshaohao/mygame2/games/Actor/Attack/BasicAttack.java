package hhs.hhshaohao.mygame2.games.Actor.Attack;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;
import com.badlogic.gdx.utils.Pool;
import hhs.hhshaohao.mygame2.games.Actor.BasicEnemyActor;
import hhs.hhshaohao.mygame2.games.Actor.PlayerActor;
import hhs.hhshaohao.mygame2.games.Actor.ToolActor.Text;

public abstract class BasicAttack extends Actor{

  public TextureRegion texture;
  public PlayerActor pa;
  public float ow,oh;
  public Polygon polygon;

  public float hurt=0;
  public float wait=0.2f;
  public float repel=100;

  Pool<Text> textPool=new Pool<>(600) {

    @Override
    protected Text newObject() {
      return new Text(textPool,20+"",0,0);
    }
  };

  public BasicAttack(PlayerActor pa,Texture t) {
    this(pa,t,t.getWidth(),t.getHeight());
  }

  public BasicAttack(PlayerActor pa,Texture t,float w,float h) {
    texture=new TextureRegion(t);
    this.pa=pa;

    ow=w;
    oh=h;

    polygon=new Polygon(
      new float[] {0,0,getWidth(),0,getWidth(),getHeight(),0,getHeight()});
    polygon.setPosition(getX(),getY());

    setSize(w,h);
    setOrigin(w/2,h/2);
    setRotation(0);
  }

  public abstract void touch(Touchpad pad,float delta);

  public abstract void up(float delta);

  public void hurt(BasicEnemyActor bea,Stage ws) {
    bea.hp-=hurt;
    Text t=textPool.obtain();
    t.setText(hurt+"");
    t.setPosition(
      bea.getX()+MathUtils.random(0,bea.getWidth()),
      bea.getY()+MathUtils.random(0,bea.getHeight()));
    ws.addActor(t);
    bea.body.applyForceToCenter(
      bea.body.getPosition().sub(polygon.getX(),polygon.getY()).nor().scl(repel),true);
  }

  public abstract boolean cheakHurt(Polygon polygon);

  @Override
  public void act(float delta) {
    super.act(delta);
    polygon.setPosition(getX(),getY());
  }

  @Override
  public void draw(Batch batch,float parentAlpha) {
    batch.draw(
      texture,
      getX(),
      getY(),
      getOriginX(),
      getOriginY(),
      getWidth(),
      getHeight(),
      getScaleX(),
      getScaleY(),
      getRotation());
  }

  @Override
  protected void sizeChanged() {
    super.sizeChanged();
    polygon.setVertices(
      new float[] {0,0,getWidth(),0,getWidth(),getHeight(),0,getHeight()});
  }

  @Override
  public void setOrigin(float originX,float originY) {
    super.setOrigin(originX,originY);
    polygon.setOrigin(originX,originY);
  }

  @Override
  public void setRotation(float degrees) {
    super.setRotation(degrees);
    polygon.setRotation(degrees);
  }
}
