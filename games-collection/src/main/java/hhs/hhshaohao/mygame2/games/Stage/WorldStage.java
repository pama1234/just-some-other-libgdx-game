package hhs.hhshaohao.mygame2.games.Stage;

import hhs.hhshaohao.mygame2.Tools.VerticesUtils;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;
import hhs.hhshaohao.mygame2.BuildConfig;
import hhs.hhshaohao.mygame2.games.ActiveObject.TouchAble;
import hhs.hhshaohao.mygame2.games.Actor.Attack.BasicAttack;
import hhs.hhshaohao.mygame2.games.Actor.MapActor;
import hhs.hhshaohao.mygame2.games.Actor.PlayerActor;
import hhs.hhshaohao.mygame2.games.Listener.BasicContactListener;
import hhs.hhshaohao.mygame2.games.Listener.Box2dFilder;
import hhs.hhshaohao.mygame2.games.MyGame;
import hhs.hhshaohao.mygame2.games.Tool;
import hhs.hhshaohao.mygame2.games.Actor.Attack.AttackLoader;
import com.badlogic.gdx.Gdx;

public class WorldStage extends Stage{

  public MapActor ma;
  public PlayerActor pa;

  public World world;

  BasicAttack att;

  public Controler control;

  public int prehurt=0;
  public int kill=0;

  Camera cam;

  public WorldStage(TiledMap map) {
    ma=new MapActor(map);

    cam=getCamera();

    world=new World(new Vector2(0,0),true);
    world.setContactListener(new BasicContactListener());
    world.setContactFilter(new Box2dFilder());
    Tool.creataOverride(map,world);

    MapLayer bios;
    if((bios=map.getLayers().get("biology"))!=null) {
      BodyDef bdef=new BodyDef();
      FixtureDef fdef=new FixtureDef();
      for(RectangleMapObject rmo:bios.getObjects().getByType(RectangleMapObject.class)) {
        Rectangle rect=rmo.getRectangle();
        bdef.type=BodyDef.BodyType.StaticBody;
        PolygonShape shape=new PolygonShape();
        float[] value=new float[] {
          0,0,
          rect.getWidth(),0,
          rect.getWidth(),rect.getHeight(),
          0,rect.getHeight(),
          0,0
        };
        shape.set(VerticesUtils.toOffsetVertices(value));
        bdef.position.set(VerticesUtils.worldToVertices(rect.getX(),rect.getY()).add(shape.getRadius(),shape.getRadius()));

        fdef.shape=shape;
        Tool.define(fdef,0,0);
        Fixture f=world.createBody(bdef).createFixture(fdef);
        f.setUserData(new TouchAble() {

          @Override
          public void start(PlayerActor pa) {
            control.pushText("来到深海");
          }

          @Override
          public void end(PlayerActor pa) {}
        });
      }
    }

    pa=new PlayerActor(
      world,
      new Vector2(50,0),
      MyGame.am.get("touchpad1.png",Texture.class),
      5);

    addActor(ma);
    addActor(pa);

    Array<Texture> t=new Array<>(11);
    for(int i=0;i<11;i++) {
      t.add(new Texture("anim/f1/"+i+".png"));
    }
    Animation a=new Animation<>(1/60f,t,Animation.PlayMode.LOOP);
    att=new AttackLoader().newAttack(Gdx.files.internal("xml/test.xml"),pa);
    //att = new AutoShotAttack(pa,MyGame.res.getTexture("attack.png"),10,10);
  }

  public BasicAttack getAttack() {
    return att;
  }

  public void setPos(float x,float y) {
    cam.position.x=x;
    cam.position.y=y;
  }

  public void setScale(float scale) {
    cam.viewportWidth*=scale;
    cam.viewportHeight*=scale;
  }

  @Override
  public void setDebugAll(boolean debugAll) {
    super.setDebugAll(debugAll);
  }

  @Override
  public void act(float delta) {
    super.act(delta);

    Tool.update(cam,pa.body.getPosition().x,pa.body.getPosition().y);

    world.step(delta,2,6);
  }

  public void showNumber(int a) {}

  @Override
  public void draw() {
    super.draw();
    if(BuildConfig.DEBUG) {
      MyGame.boxdebug.render(world,cam.combined);
    }
  }
}
