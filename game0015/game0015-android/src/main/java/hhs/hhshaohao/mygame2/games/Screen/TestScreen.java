package hhs.hhshaohao.mygame2.games.Screen;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

import hhs.hhshaohao.mygame2.games.Actor.Enemy.RushA;
import hhs.hhshaohao.mygame2.games.MyGame;
import hhs.hhshaohao.mygame2.games.Stage.Controler;
import hhs.hhshaohao.mygame2.games.Stage.WorldStage;
import hhs.hhshaohao.mygame2.Tools.VerticesUtils;
import com.badlogic.gdx.utils.Pools;
import com.badlogic.gdx.utils.Pool;

public class TestScreen extends BasicPlayScreen{

  int num=0;
  float time=0;

  Pool<RushA> rush;

  @Override
  public void initControler() {
    control=new Controler(world,5000);
  }

  @Override
  public void initWorld() {
    world=new WorldStage(MyGame.am.get("tmx/Map01.tmx",TiledMap.class));
  }

  float scale=5;

  MyGame game;

  public TestScreen(final MyGame game) {
    this.game=game;

    world.setScale(1/scale);

    rush=new Pool<>() {

      @Override
      protected RushA newObject() {
        return new RushA(world,new Vector2(0,0),1000);
      }

    };
    Pools.set(RushA.class,rush);

    for(int i=0;i<100;i++) {
      RushA r=rush.obtain();
      r.pos.set(VerticesUtils.worldToVertices(new Vector2(MathUtils.random(0,36*45/2),MathUtils.random(0,18*30)).add(0,8)));
      r.body.setTransform(r.pos,r.body.getAngle());
      world.addActor(r);
    }
    input.addProcessor(control);
    input.addProcessor(new GestureDetector(new CameraControlGesturer((OrthographicCamera)world.getCamera())));
  }

  @Override
  public void show() {
    super.show();
    world.pa.hp=world.pa.ohp;
  }

  public void reStart() {

  }
  @Override
  public void render(float p1) {
    super.render(p1);
    // ws.world.clearForces();
    if(world.pa.hp<0) {
      game.dead(this);
    }
    if(world.getRoot().getChildren().size<50) {
      RushA r=rush.obtain();
      r.pos.set(VerticesUtils.worldToVertices(new Vector2(MathUtils.random(0,36*45/2),MathUtils.random(0,18*30)).add(0,8)));
      r.body.setActive(true);
      r.body.setTransform(r.pos,r.body.getAngle());
      world.addActor(r);
      time=0;
    }
  }

  @Override
  public void debugDraw() {}

  @Override
  public void resize(int p1,int p2) {}

  @Override
  public void pause() {}

  @Override
  public void resume() {}

  @Override
  public void hide() {}

  @Override
  public void dispose() {}
}
