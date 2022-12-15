package pama1234.gdx.game.asset;

import java.io.InputStream;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;

import pama1234.gdx.game.util.gif.Gif;
import pama1234.gdx.game.util.gif.GifDecoder;
import pama1234.gdx.game.util.gif.GifLoader;

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
  public static Gif bigEarth;
  public static void load_0001(AssetManager manager) {
    // bigEarth=load(Animation.PlayMode.LOOP,Gdx.files.internal("image/bigEarth.gif").read());
    manager.load("image/bigEarth.gif",Gif.class);
  }
  public static void put_0001(AssetManager manager) {
    bigEarth=manager.get("image/bigEarth.gif");
  }
}
