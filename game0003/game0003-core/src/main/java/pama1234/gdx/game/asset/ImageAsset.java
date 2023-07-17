package pama1234.gdx.game.asset;

import static pama1234.gdx.game.asset.ImageAssetUtil.loadFromTexture;
import static pama1234.gdx.game.asset.ImageAssetUtil.loadFromTexture_0001;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

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
    manager.load("image/earth02.png",Texture.class);
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
    player=loadFromTexture_0001(manager.get("image/player04.png"),2,2,33,52,3,2);
    creature=loadFromTexture_0001(manager.get("image/characters.png"),24,24,2,2);
    sky=loadFromTexture(manager.get("image/sky.png"));
    select=loadFromTexture(manager.get("image/select.png"));
    Texture ta=manager.get("image/earth02.png",Texture.class);
    loadEarth(ta);
  }
  public static void loadEarth() {
    loadEarth(new Texture(Gdx.files.local("assets/image/earth02.png")));
  }
  public static void loadEarth(Texture ta) {
    TextureRegion[] tr=new TextureRegion[256];
    int gapX=2,
      gapY=2;
    int x=gapX,
      y=gapY;
    int w=256,
      h=256;
    for(int i=0;i<tr.length;i++) {
      int tx=i%16;
      int ty=i/16;
      tr[i]=new TextureRegion(ta,x+tx*(w+gapX),y+ty*(h+gapY),w,h);
    }
    bigEarth=new Animation<TextureRegion>(.04f,tr);
    bigEarth.setPlayMode(Animation.PlayMode.LOOP);
  }
  //----------------------------------------------------
}
