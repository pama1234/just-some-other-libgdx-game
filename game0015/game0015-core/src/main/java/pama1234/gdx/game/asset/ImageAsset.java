package pama1234.gdx.game.asset;

import static pama1234.gdx.game.asset.ImageAssetUtil.loadFromTexture;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class ImageAsset{
  public static Texture shaderOnly;
  public static TextureRegion exit,playerTexture,enemyTexture,obstacleTexture;
  public static TextureRegion[] backgroundList=new TextureRegion[5];
  public static TextureRegion[][] tiles,items,creature,player;
  public static Animation<TextureRegion> bigEarth;
  public static void load_0001(AssetManager manager) {
    manager.load("image/player05.png",Texture.class);
    manager.load("image/enemy.png",Texture.class); // Load enemyTexture
    manager.load("image/obstacle.png",Texture.class); // Load obstacleTexture
  }
  public static void put_0001(AssetManager manager) {
    Pixmap tp=new Pixmap(1,1,Format.Alpha);
    tp.setColor(0,0,0,1);
    tp.drawPixel(0,0);
    shaderOnly=new Texture(tp);
    playerTexture=loadFromTexture(manager.get("image/player05.png",Texture.class));
    enemyTexture=loadFromTexture(manager.get("image/enemy.png",Texture.class)); // Put enemyTexture
    obstacleTexture=loadFromTexture(manager.get("image/obstacle.png",Texture.class)); // Put obstacleTexture
  }
}