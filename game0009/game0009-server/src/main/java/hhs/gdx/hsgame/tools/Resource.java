package hhs.gdx.hsgame.tools;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.SpriteCache;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.XmlReader;
import hhs.gdx.hsgame.screens.BasicScreen;
import hhs.gdx.hsgame.util.CacheRenderer;
import java.util.HashMap;
import hhs.gdx.hsgame.screens.BasicScreen;
import hhs.gdx.hsgame.util.CacheRenderer;

/**
 * 创建BasicScreen和子类时从此处获取共用绘制工具
 */
public class Resource implements Disposable{
  public static int width=Gdx.graphics.getWidth();
  public static int height=Gdx.graphics.getHeight();
  public static float u;
  public static Game game;
  public static HashMap<Class<?>,Screen> screens;
  public static AppRecorder recoder;
  public static SpriteBatch batch,stageBatch;
  public static ShapeRenderer shape;
  public static CacheRenderer cacheRender;
  public static AssetManager asset;
  public static FontManager font;
  public static JsonReader jreader;
  public static XmlReader xreader;
  public Resource(Game gameIn) {
    this.game=gameIn;
  }
  public void init() {
    if(asset==null) asset=new AssetManager();
    if(batch==null) batch=new SpriteBatch();
    if(stageBatch==null) stageBatch=new SpriteBatch();
    if(shape==null) shape=new ShapeRenderer();
    if(cacheRender==null) cacheRender=new CacheRenderer(new SpriteCache());
    if(recoder==null) recoder=new AppRecorder();
    if(screens==null) screens=new HashMap<>();
    if(jreader==null) jreader=new JsonReader();
    if(xreader==null) xreader=new XmlReader();
  }
  public static void setScreen(Screen s) {
    game.setScreen(s);
  }
  public static <T extends BasicScreen> void setScreen(Class<T> screen) {
    Screen s;
    if((s=screens.get(screen))!=null) {
      game.setScreen(s);
      return;
    }
    try {
      s=screen.getConstructor().newInstance();
    }catch(Exception e) {
      e.printStackTrace();
    }
    game.setScreen(s);
  }
  @Override
  public void dispose() {
    asset.dispose();
    batch.dispose();
    shape.dispose();
    if(font!=null) font.dispose();
    cacheRender.dispose();
  }
}
