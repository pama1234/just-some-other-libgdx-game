package hhs.game.diffjourney;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGeneratorLoader;

import hhs.game.diffjourney.screens.StartMenu;
import hhs.gdx.hsgame.tools.BasicLoader;
import hhs.gdx.hsgame.tools.Resourse;

public class MainGame extends Game{
  Resourse res;
  AssetManager asset;
  @Override
  public void create() {
    res=new Resourse(this);
    asset=Resourse.asset;
    load();
    setScreen(new BasicLoader(asset,StartMenu.class));
  }
  void load() {
    asset.setLoader(
      FreeTypeFontGenerator.class,
      new FreeTypeFontGeneratorLoader(new InternalFileHandleResolver()));
    asset.load("font.ttf",FreeTypeFontGenerator.class);
    asset.load("music/start.mp3",Music.class);
    asset.load("music/play.mp3",Music.class);
    asset.load("anim/blink.png",Texture.class);
    asset.load("Mushroom/Idle.png",Texture.class);
    asset.load("ele.png",Texture.class);
    asset.load("dian.png",Texture.class);
    asset.load("pao.png",Texture.class);
    asset.load("Mushroom/Run.png",Texture.class);
    asset.load("map/map.atlas",TextureAtlas.class);
  }
  @Override
  public void dispose() {
    super.dispose();
    res.dispose();
  }
}
