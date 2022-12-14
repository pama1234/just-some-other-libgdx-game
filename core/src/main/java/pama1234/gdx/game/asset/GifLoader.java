package pama1234.gdx.game.asset;

import java.io.InputStream;

import com.badlogic.gdx.assets.AssetLoaderParameters;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

import pama1234.gdx.game.util.GifDecoder;

public class GifLoader{
  public static GifDecoder gdec=new GifDecoder();
  public static Gif load(Animation.PlayMode playMode,InputStream is) {
    gdec.read(is);
    return gdec.getAnimation(playMode);
  }
  public static void setLoader(AssetManager manager) {
    manager.setLoader(Gif.class,new GifDecoder(manager.getFileHandleResolver()));
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
  //----------------------------------------------------
  public static class GifParameter extends AssetLoaderParameters<Gif>{
  }
  public static class Gif extends Animation<TextureRegion>{
    public Gif(float frameDuration,TextureRegion... keyFrames) {
      super(frameDuration,keyFrames);
    }
    public Gif(float frameDuration,Array<? extends TextureRegion> keyFrames,PlayMode playMode) {
      super(frameDuration,keyFrames,playMode);
    }
    public Gif(float frameDuration,Array<? extends TextureRegion> keyFrames) {
      super(frameDuration,keyFrames);
    }
  }
}
