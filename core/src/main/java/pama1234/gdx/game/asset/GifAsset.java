package pama1234.gdx.game.asset;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import java.io.InputStream;
import pama1234.gdx.game.util.gif.Gif;
import pama1234.gdx.game.util.gif.GifDecoder;
import pama1234.gdx.game.util.gif.GifLoader;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import pama1234.gdx.util.FileUtil;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.Gdx;

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
  public static Animation<TextureRegion> bigEarth;
  public static void load_0001(AssetManager manager) {
    // bigEarth=load(Animation.PlayMode.LOOP,Gdx.files.internal("image/bigEarth.gif").read());
    manager.load("image/bigEarth.atlas",TextureAtlas.class);
  }
  public static void put_0001(AssetManager manager) {
      TextureAtlas ta=manager.get("image/bigEarth.atlas",TextureAtlas.class);
    TextureRegion[] tr=new TextureRegion[270];
    for (int i = 1; i <= 270; i++) {
        TextureAtlas.AtlasRegion r=ta.findRegion(String.valueOf(i));
        r.flip(false,true);
        tr[i-1]=r;
    }
    bigEarth=new Animation<TextureRegion>(.04f,tr);
    bigEarth.setPlayMode(Animation.PlayMode.LOOP);
  }
  public static void loadWithAtlas(){
      TextureAtlas ta=new TextureAtlas(Gdx.files.internal("image/bigEarth.atlas"));
      TextureRegion[] tr=new TextureRegion[270];
      for (int i = 1; i <= 270; i++) {
          TextureAtlas.AtlasRegion r=ta.findRegion(String.valueOf(i));
          r.flip(false,true);
          tr[i-1]=r;
      }
      bigEarth=new Animation<TextureRegion>(.04f,tr);
      bigEarth.setPlayMode(Animation.PlayMode.LOOP);
  }
}
