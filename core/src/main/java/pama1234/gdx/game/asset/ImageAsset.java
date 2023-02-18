package pama1234.gdx.game.asset;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import pama1234.gdx.util.FileUtil;
import pama1234.math.UtilMath;

public class ImageAsset{
  public static Texture shaderOnly;
  public static TextureRegion //
  exit,
    background,sky,select;
  public static TextureRegion[] backgroundList=new TextureRegion[5];
  // public static int //
  // tileWidth=18,
  //   tileHeight=18,
  //   tileWidthGap=2,
  //   tileHeightGap=2;
  public static TextureRegion[][] tiles,items,creature,player;
  // public static TextureRegion background;
  public static Animation<TextureRegion> bigEarth;
  public static void load_0001(AssetManager manager) {
    // background=load("background.png");
    manager.load("image/exit.png",Texture.class);
    manager.load("image/background/background.png",Texture.class);
    for(int i=1;i<=5;i++) manager.load("image/background/"+i+".png",Texture.class);
    manager.load("image/tiles.png",Texture.class);
    manager.load("image/items.png",Texture.class);
    manager.load("image/player04.png",Texture.class);
    manager.load("image/characters.png",Texture.class);
    manager.load("image/sky.png",Texture.class);
    manager.load("image/select.png",Texture.class);
    manager.load("image/bigEarth.atlas",TextureAtlas.class);
  }
  public static void put_0001(AssetManager manager) {
    Pixmap tp=new Pixmap(1,1,Format.Alpha);
    tp.setColor(0,0,0,1);
    tp.drawPixel(0,0);
    shaderOnly=new Texture(tp);
    exit=loadFromTexture(manager.get("image/exit.png"));
    background=loadFromTexture(manager.get("image/background/background.png"));
    for(int i=0;i<5;i++) backgroundList[i]=loadFromTexture(manager.get("image/background/"+(i+1)+".png",Texture.class));
    tiles=loadFromTexture_0001(manager.get("image/tiles.png"),18,18,2,2);
    items=loadFromTexture_0001(manager.get("image/items.png"),18,18,2,2);
    player=loadFromTexture_0001(manager.get("image/player04.png"),36,54,0,0);
    // System.out.println(player[0].length);
    // player=loadFromTexture_0001(manager.get("image/player.png"),20,24,4,0);
    creature=loadFromTexture_0001(manager.get("image/characters.png"),24,24,2,2);
    sky=loadFromTexture(manager.get("image/sky.png"));
    select=loadFromTexture(manager.get("image/select.png"));
    TextureAtlas ta=manager.get("image/bigEarth.atlas",TextureAtlas.class);
    TextureRegion[] tr=new TextureRegion[270];
    for(int i=1;i<=270;i++) {
      TextureAtlas.AtlasRegion r=ta.findRegion(String.valueOf(i));
      r.flip(false,true);
      tr[i-1]=r;
    }
    bigEarth=new Animation<TextureRegion>(.04f,tr);
    bigEarth.setPlayMode(Animation.PlayMode.LOOP);
  }
  public static void loadEarth() {
    TextureAtlas ta=new TextureAtlas("assets/image/bigEarth.atlas");
    TextureRegion[] tr=new TextureRegion[270];
    for(int i=1;i<=270;i++) {
      TextureAtlas.AtlasRegion r=ta.findRegion(String.valueOf(i));
      r.flip(false,true);
      tr[i-1]=r;
    }
    bigEarth=new Animation<TextureRegion>(.04f,tr);
    bigEarth.setPlayMode(Animation.PlayMode.LOOP);
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
