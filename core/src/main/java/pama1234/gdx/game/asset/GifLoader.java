package pama1234.gdx.game.asset;

import java.io.InputStream;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import pama1234.gdx.game.util.GifDecoder;

public class GifLoader{
  public static GifDecoder gdec=new GifDecoder();
  public static Animation<TextureRegion> load(Animation.PlayMode playMode,InputStream is) {
    gdec.read(is);
    return gdec.getAnimation(playMode);
  }
  //----------------------------------------------------
  public static Animation<TextureRegion> bigEarth;
  public static void load_0001() {
    bigEarth=load(Animation.PlayMode.LOOP,Gdx.files.internal("image/bigEarth.gif").read());
  }
}
