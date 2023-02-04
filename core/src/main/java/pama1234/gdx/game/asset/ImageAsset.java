package pama1234.gdx.game.asset;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import pama1234.gdx.util.FileUtil;
import pama1234.math.UtilMath;

public class ImageAsset{
  public static Texture shaderOnly;
  public static TextureRegion //
  exit,
    background,sky,select;
  // public static int //
  // tileWidth=18,
  //   tileHeight=18,
  //   tileWidthGap=2,
  //   tileHeightGap=2;
  public static TextureRegion[][] tiles,items,creature,player;
  // public static TextureRegion background;
  public static void load_0001(AssetManager manager) {
    // background=load("background.png");
    manager.load("image/exit.png",Texture.class);
    manager.load("image/background.png",Texture.class);
    manager.load("image/tiles.png",Texture.class);
    manager.load("image/items.png",Texture.class);
    manager.load("image/player04.png",Texture.class);
    manager.load("image/characters.png",Texture.class);
    manager.load("image/sky.png",Texture.class);
    manager.load("image/select.png",Texture.class);
  }
  public static void put_0001(AssetManager manager) {
    Pixmap tp=new Pixmap(1,1,Format.Alpha);
    tp.setColor(1,0,0,1);
    tp.drawPixel(0,0);
    shaderOnly=new Texture(tp);
    exit=loadFromTexture(manager.get("image/exit.png"));
    background=loadFromTexture(manager.get("image/background.png"));
    tiles=loadFromTexture_0001(manager.get("image/tiles.png"),18,18,2,2);
    items=loadFromTexture_0001(manager.get("image/items.png"),18,18,2,2);
    player=loadFromTexture_0001(manager.get("image/player04.png"),36,54,0,0);
    // System.out.println(player[0].length);
    // player=loadFromTexture_0001(manager.get("image/player.png"),20,24,4,0);
    creature=loadFromTexture_0001(manager.get("image/characters.png"),24,24,2,2);
    sky=loadFromTexture(manager.get("image/sky.png"));
    select=loadFromTexture(manager.get("image/select.png"));
  }
  //----------------------------------------------------
  public static TextureRegion load(String in) {
    return FileUtil.loadTextureRegion("image/"+in);
  }
  public static TextureRegion loadFromTexture(Texture in) {
    return FileUtil.toTextureRegion(in);
  }
  public static TextureRegion[][] loadFromTexture_0001(Texture in,int w,int h,int w2,int h2) {
    int tw=w+w2,th=h+h2;
    TextureRegion[][] out=new TextureRegion[UtilMath.round(in.getWidth()/(float)tw)][UtilMath.round(in.getHeight()/(float)th)];
    for(int i=0;i<out.length;i++) for(int j=0;j<out[i].length;j++) out[i][j]=FileUtil.toTextureRegion(in,i*tw,j*th,w,h);
    return out;
  }
}
