package hhs.gdx.hsgame.tools;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.SpriteCache;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Disposable;
import hhs.gdx.hsgame.screens.BasicScreen;
import hhs.gdx.hsgame.util.CacheRenderer;
import java.util.HashMap;

public class Resourse implements Disposable{
  public static int width=Gdx.graphics.getWidth();
  public static int height=Gdx.graphics.getHeight();
  public static Game game;
  public static HashMap<Class,BasicScreen> screens;
  public static AppRecorder recoder;
  public static SpriteBatch batch;
  public static ShapeRenderer shape;
  public static CacheRenderer cacheRender;
  public static AssetManager asset;
  public static FontManager font;
  public Resourse(Game game) {
    this.game=game;
    asset=new AssetManager();
    batch=new SpriteBatch();
    shape=new ShapeRenderer();
    cacheRender=new CacheRenderer(new SpriteCache());
    recoder=new AppRecorder();
    screens=new HashMap<>();
  }
  @Override
  public void dispose() {
    asset.dispose();
    batch.dispose();
    shape.dispose();
    font.dispose();
    cacheRender.dispose();
    for(BasicScreen bs:screens.values()) {
      bs.dispose();
    }
  }
}
