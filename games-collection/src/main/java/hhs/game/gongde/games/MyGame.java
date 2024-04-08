package hhs.game.gongde.games;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGeneratorLoader;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.Preferences;

public class MyGame extends Game{

  public static AssetManager manager;
  public static Preferences local;

  @Override
  public void create() {
    local=Gdx.app.getPreferences(".local");
    manager=new AssetManager();

    manager.setLoader(FreeTypeFontGenerator.class,".ttf",new FreeTypeFontGeneratorLoader(new InternalFileHandleResolver()));
    manager.load("auto.ttf",FreeTypeFontGenerator.class);
    manager.load("mu.png",Texture.class);
    setScreen(new Loading(this));
  }

  @Override
  public void render() {
    Gdx.gl.glClearColor(0,0,0,0);
    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    super.render();
  }

  @Override
  public void dispose() {}

  @Override
  public void resize(int width,int height) {}

  @Override
  public void pause() {}

  @Override
  public void resume() {}

}
