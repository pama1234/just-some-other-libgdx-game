package hhs.game.diffjourney;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGeneratorLoader;
import hhs.game.diffjourney.screens.LoadingScreen;
import hhs.game.diffjourney.screens.MainScreen;
import hhs.gdx.hsgame.screens.BasicScreen;
import hhs.gdx.hsgame.tools.BasicLoader;
import hhs.gdx.hsgame.tools.FontManager;
import hhs.gdx.hsgame.tools.Resource;
import hhs.gdx.hsgame.util.GameAssetManager;

public class MainGame extends Game{
  Resource res;
  AssetManager asset;
  BasicLoader basicLoader;
  static LoadingScreen loadScreen;
  @Override
  public void create() {
    res=new Resource(this);
    res.asset=new GameAssetManager();
    res.init();
    asset=res.asset;
    load();
    setScreen(basicLoader=new BasicLoader(asset,MainScreen.class));
    loadScreen=new LoadingScreen();
    basicLoader.setInit(()->Resource.font=new FontManager(Resource.asset.get("font.ttf")));
  }
  public static void setLoadingScreen(Runnable run) {
    loadScreen.set(run);
  }
  public static <T extends BasicScreen> void setLoadingScreen(Class<T> screen) {
    loadScreen.set(screen);
  }
  void load() {
    asset.setLoader(FreeTypeFontGenerator.class,
      new FreeTypeFontGeneratorLoader(new InternalFileHandleResolver()));
    asset.load("font.ttf",FreeTypeFontGenerator.class);
    asset.load("music/start.mp3",Music.class);
    asset.load("music/play.mp3",Music.class);
    asset.load("map/map.atlas",TextureAtlas.class);
    asset.load("textures/character.atlas",TextureAtlas.class);
    loadTexture("anim/blink.png");
    loadTexture("null.png");
    loadTexture("ele.png");
    loadTexture("dian.png");
    loadTexture("pao.png");
    loadTexture("bullet.png");
    loadTexture("Mushroom/Run.png");
    loadTexture("Mushroom/Idle.png");
    loadTexture("Mushroom/Hit.png");
    loadTexture("Mushroom/Attack.png");
    loadTexture("Mushroom/Death.png");
    loadTexture("praticle/praticle1.png");
    loadTexture("praticle/praticle2.png");
  }
  public void loadTexture(String fileName) {
    asset.load(fileName,Texture.class);
  }
  @Override
  public void dispose() {
    super.dispose();
    res.dispose();
    // TODO: Implement this method
  }
  @Override
  public void resize(int arg0,int arg1) {
    super.resize(arg0,arg1);
    Resource.width=arg0;
    Resource.height=arg1;
    // TODO: Implement this method
  }
}
