package hhs.hhshaohao.mygame2.games.Actor;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.physics.box2d.Transform;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

import hhs.hhshaohao.mygame2.games.Constant;
import hhs.hhshaohao.mygame2.games.Tool;
import hhs.hhshaohao.mygame2.games.ActiveObject.TouchAble;

public abstract class BasicRoleActor extends Actor implements TouchAble{

  public TextureRegionDrawable text;

  public Vector2 position;
  public Body body;
  public Transform transform;
  public float radius;

  public Shape shape;

  public Polygon polygon;

  public BasicRoleActor(World world,Texture text,Vector2 pos,Shape bodyShape) {
    this(world,text,pos,bodyShape,Constant.role,Constant.wall);
  }

  public BasicRoleActor(
    World world,
    Texture text,
    Vector2 pos,
    Shape bodyShape,
    int categoryBits,
    int maskBits) {
    this.text=Tool.ttd(text);

    position=pos;

    BodyDef bdef=new BodyDef();
    bdef.type=BodyDef.BodyType.DynamicBody;
    bdef.position.set(pos);
    bdef.fixedRotation=true;
    FixtureDef fdef=new FixtureDef();
    fdef.shape=bodyShape;
    fdef.filter.categoryBits=(short)categoryBits;
    fdef.filter.maskBits=(short)maskBits;

    body=world.createBody(bdef);
    body.createFixture(fdef).setUserData(this);
    shape=bodyShape;

    transform=body.getTransform();

    polygon=new Polygon(
      new float[] {0,0,getWidth(),0,getWidth(),getHeight(),0,getHeight()});
    polygon.setPosition(getX(),getY());
  }

  public void setFdefData(Object o) {
    body.getFixtureList().first().setUserData(o);
  }

  @Override
  public void act(float delta) {
    super.act(delta);

    Vector2 pos=body.getPosition();
    setX(pos.x-getWidth()/2);
    setY(pos.y-getHeight()/2);
    polygon.setPosition(getX(),getY());
  }

  public abstract void find(Body body,float delta);

  public abstract boolean isWalk(Body body);

  public abstract void walk(Body body,float delta);

  @Override
  public void draw(Batch batch,float parentAlpha) {
    text.draw(batch,getX(),getY(),getWidth(),getHeight());
  }

  @Override
  protected void sizeChanged() {
    super.sizeChanged();
    polygon.setVertices(
      new float[] {0,0,getWidth(),0,getWidth(),getHeight(),0,getHeight()});
  }
}
