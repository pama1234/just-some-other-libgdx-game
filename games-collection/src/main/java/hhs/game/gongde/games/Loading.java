package hhs.game.gongde.games;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.Gdx;

/**
 * @Author hhshaohao
 * @Date 2023/03/11 22:19
 */
public class Loading implements Screen{

  MyGame g;
  ShapeRenderer shape;

  public Loading(MyGame game) {
    g=game;

    shape=new ShapeRenderer();
  }

  @Override
  public void dispose() {}

  @Override
  public void hide() {}

  @Override
  public void pause() {}

  @Override
  public void render(float p) {
    if(g.manager.update()) {
      StaticRes.finishLoading(g);
      g.setScreen(new Game(g));
    }
    shape.begin(ShapeRenderer.ShapeType.Filled);
    shape.rect(0,0,Gdx.graphics.getWidth()*g.manager.getProgress(),Gdx.graphics.getHeight()/10);
    shape.end();
  }

  @Override
  public void resize(int p,int p1) {}

  @Override
  public void resume() {}

  @Override
  public void show() {}

}
