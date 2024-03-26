package hhs.game.sort.games;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGeneratorLoader;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

import hhs.game.sort.LazyBitmapFont;

public class MyGame extends Game{

  public SpriteBatch batch;
  public AssetManager assets;
  public TextureTools ttool;

  public ChooseScreen cs;

  public TextButton.TextButtonStyle text;
  Stage ui;
  ScrollPane scroll;
  Table table;

  @Override
  public void create() {
    assets=new AssetManager();
    batch=new SpriteBatch();
    ttool=new TextureTools();
    Texture.setAssetManager(assets);

    assets.setLoader(FreeTypeFontGenerator.class,".ttf",new FreeTypeFontGeneratorLoader(new InternalFileHandleResolver()));
    assets.load("auto.ttf",FreeTypeFontGenerator.class);
    setScreen(new LoaderScreen(this));

  }

  public void finish() {
    text=new TextButton.TextButtonStyle(ttd(ttool.createTexture(200,100,Color.BLUE)),ttd(ttool.createTexture(200,100,new Color(0,0,.6f,1))),null,new LazyBitmapFont(assets.get("auto.ttf",FreeTypeFontGenerator.class),50,Color.WHITE));
    setScreen(cs=new ChooseScreen(this));
  }

  @Override
  public void render() {
    Gdx.gl.glClearColor(1,1,1,1);
    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

    super.render();
  }

  @Override
  public void dispose() {
    assets.dispose();
    batch.dispose();
    ttool.dispose();
    cs.dispose();
  }

  TextureRegionDrawable ttd(Texture t) {
    return new TextureRegionDrawable(new TextureRegion(t));
  }

  @Override
  public void resize(int width,int height) {
    super.resize(width,height);
  }

  @Override
  public void pause() {
    super.pause();
  }

  @Override
  public void resume() {
    super.resume();
  }

}
