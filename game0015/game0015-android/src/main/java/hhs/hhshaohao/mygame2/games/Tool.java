package hhs.hhshaohao.mygame2.games;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

import hhs.hhshaohao.mygame2.games.ActiveObject.TouchAble;
import hhs.hhshaohao.mygame2.games.Actor.PlayerActor;

import static hhs.hhshaohao.mygame2.Tools.VerticesUtils.toOffsetVertices;

public class Tool{

  public static TextureRegionDrawable ttd(Texture t) {
    return new TextureRegionDrawable(new TextureRegion(t));
  }

  public static void creataOverride(TiledMap map,World world) {
    BodyDef bdef=new BodyDef();
    FixtureDef fdef=new FixtureDef();

    bdef.type=BodyDef.BodyType.StaticBody;

    int w=map.getProperties().get("width",10,Integer.class);
    int h=map.getProperties().get("height",10,Integer.class);

    float[] ve=new float[] {0,0,
      0,18*h,
      36*w/2,18*h,
      36*w/2,0,
      0,0};
    float[] turn=toOffsetVertices(ve);

    ChainShape shape=new ChainShape();

    shape.createChain(turn);

    bdef.position.set(-shape.getRadius(),-shape.getRadius());
    fdef.shape=shape;
    fdef.filter.categoryBits=Constant.wall;

    world.createBody(bdef).createFixture(fdef).setUserData(new TouchAble() {

      @Override
      public void start(PlayerActor pa) {}

      @Override
      public void end(PlayerActor pa) {}
    });
  }

  public static float[] VectorToList(Vector2[] v) {
    float[] n=new float[v.length*2];
    int index=0;
    for(int i=0;i<v.length;i++) {
      n[index++]=v[i].x;
      n[index++]=v[i].y;
    }
    return n;
  }

  public static void define(FixtureDef fdef,short type,short p) {
    fdef.filter.categoryBits=type;
    fdef.filter.maskBits=p;
  }
  public static void define(FixtureDef fdef,int type,int p) {
    define(fdef,(short)type,(short)p);
  }

  public static void update(Camera camera,float targetX,float targetY) {
    float dx=(targetX-camera.position.x)/16;
    float dy=(targetY-camera.position.y)/8;
    camera.position.add(dx,dy,0);
  }

  public static PolygonShape rectShape(float w,float h) {
    PolygonShape shape=new PolygonShape();
    shape.setAsBox(w/2,h/2);
    return shape;
  }

  public static CircleShape circleShape(float r) {
    CircleShape shape=new CircleShape();
    shape.setRadius(r);
    return shape;
  }

  public static void fromAtoB(Vector2 pos,Vector2 point,float delta) {
    pos.add(point.scl(delta));
  }
}
