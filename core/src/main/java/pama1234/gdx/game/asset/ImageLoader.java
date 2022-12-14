package pama1234.gdx.game.asset;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

import pama1234.gdx.util.FileUtil;

public class ImageLoader{
  public static TextureRegion background;
  public static void load_0001() {
    background=load("background.png");
  }
  private static TextureRegion load(String in) {
    return FileUtil.loadTextureRegion("image/"+in);
  }
}
