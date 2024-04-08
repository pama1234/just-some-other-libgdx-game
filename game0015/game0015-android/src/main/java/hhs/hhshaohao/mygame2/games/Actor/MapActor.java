package hhs.hhshaohao.mygame2.games.Actor;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.IsometricTiledMapRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class MapActor extends Actor{

  public IsometricTiledMapRenderer render;
  Camera cam;

  public MapActor(TiledMap map) {
    this(map,1);
  }

  public MapActor(TiledMap map,float scale) {
    render=new IsometricTiledMapRenderer(map,scale);
  }

  public TiledMap getMap() {
    return render.getMap();
  }

  @Override
  public void act(float delta) {
    render.setView((OrthographicCamera)getStage().getCamera());
  }

  @Override
  public void draw(Batch batch,float parentAlpha) {
    render.render();
  }
}
