package hhs.game.sort.games;

import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Color;

public class LoaderScreen extends ScreenAdapter{

  MyGame game;

  AssetManager assets;
  SpriteBatch batch;

  Texture t;

  public LoaderScreen(MyGame g) {
    game=g;
    assets=g.assets;
    batch=g.batch;

    t=g.ttool.createTexture(200,100,Color.BLUE);
  }

  @Override
  public void render(float delta) {
    if(assets.update()) {
      game.finish();
    }
    batch.begin();
    batch.draw(t,0,0,Constant.w*assets.getProgress(),Constant.h/8);
    batch.end();
  }

}
