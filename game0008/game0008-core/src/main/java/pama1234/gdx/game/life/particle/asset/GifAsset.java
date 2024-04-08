package pama1234.gdx.game.life.particle.asset;

import java.io.InputStream;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;

import pama1234.gdx.util.gif.Gif;
import pama1234.gdx.util.gif.GifDecoder;
import pama1234.gdx.util.gif.GifLoader;

public class GifAsset{
  public static GifDecoder gdec=new GifDecoder();
  public static Gif load(PlayMode playMode,InputStream is) {
    gdec.read(is);
    return gdec.getAnimation(playMode);
  }
  public static void setLoader(AssetManager manager) {
    manager.setLoader(Gif.class,new GifLoader(manager.getFileHandleResolver()));
    // manager.setLoader(Gif.class,".gif",new GifDecoder(manager.getFileHandleResolver()));
  }
  //----------------------------------------------------
  // public static Animation<TextureRegion> bigEarth;
  public static void load_0001(AssetManager manager) {}
  public static void put_0001(AssetManager manager) {}
}