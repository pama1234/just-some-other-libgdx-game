package pama1234.gdx.game.asset;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import pama1234.gdx.util.FileUtil;

public class ImageAsset{
  public static TextureRegion background;
  // public static TextureRegion background;
  public static void load_0001(AssetManager manager) {
    // background=load("background.png");
    manager.load("image/background.png",Texture.class);
  }
  public static void put_0001(AssetManager manager) {
    background=loadFromTexture(manager.get("image/background.png"));
  }
  //----------------------------------------------------
  public static TextureRegion load(String in) {
    return FileUtil.loadTextureRegion("image/"+in);
  }
  public static TextureRegion loadFromTexture(Texture in) {
    return FileUtil.toTextureRegion(in);
  }
}
