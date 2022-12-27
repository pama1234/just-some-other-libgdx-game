package pama1234.gdx.game.state.state0001.game.world;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import pama1234.gdx.game.app.Screen0011;
import pama1234.gdx.game.asset.ImageAsset;
import pama1234.gdx.game.state.state0001.Game;
import pama1234.gdx.game.state.state0001.game.entity.GameEntityCenter;
import pama1234.gdx.game.state.state0001.game.entity.entity0001.Fly;
import pama1234.gdx.game.state.state0001.game.entity.entity0001.Fly.FlyType;
import pama1234.gdx.game.state.state0001.game.metainfo.MetaBlock;
import pama1234.gdx.game.state.state0001.game.metainfo.MetaCreature;
import pama1234.gdx.game.state.state0001.game.metainfo.MetaIntItem;
import pama1234.gdx.game.state.state0001.game.metainfo.MetaItem;
import pama1234.gdx.game.state.state0001.game.metainfo.info0001.center.MetaBlockCenter0001;
import pama1234.gdx.game.state.state0001.game.metainfo.info0001.center.MetaCreatureCenter0001;
import pama1234.gdx.game.state.state0001.game.metainfo.info0001.center.MetaItemCenter0001;
import pama1234.gdx.game.state.state0001.game.player.MainPlayer2D;
import pama1234.gdx.game.state.state0001.game.player.Player2D.PlayerCenter2D;
import pama1234.gdx.game.state.state0001.game.player.Player2D.PlayerType2D;
import pama1234.gdx.game.state.state0001.game.region.RegionCenter;
import pama1234.gdx.game.state.state0001.game.region.block.Block;
import pama1234.gdx.game.state.state0001.game.region.block.block0001.Dirt;
import pama1234.math.Tools;
import pama1234.math.UtilMath;

public class World0001 extends World<Screen0011,Game>{
  public MetaBlockCenter0001 metaBlocks;
  public MetaItemCenter0001 metaItems;
  public MetaCreatureCenter0001 metaEntitys;
  public GameEntityCenter entitys;
  public PlayerCenter2D players;
  public RegionCenter regions;
  public MainPlayer2D yourself;
  public int blockWidth=18,blockHeight=18;
  public float g=1f,jumpForce=-blockHeight*1.5f;
  // public int daySize=3600;
  public int daySize=216000/3;
  public int time;
  public float ambientLight;
  public int typeCache;
  public Pixmap skyColorMap;
  public int skyColorPos,skyColorCount;
  public float daySkyGridSize;
  public Color backgroundColor,colorA,colorB;
  public World0001(Screen0011 p,Game pg) {
    super(p,pg,2);
    createBlockC();
    createItemC();
    createCreatureC();
    // list[0]=players=new PlayerCenter2D(p);
    list[0]=entitys=new GameEntityCenter(p);
    entitys.list.add(entitys.players=players=new PlayerCenter2D(p));
    list[1]=regions=new RegionCenter(p,this,Gdx.files.local("data/saved/regions.bin"));
    // regions.load();
    yourself=new MainPlayer2D(p,this,0,-1,pg);
    backgroundColor=p.color(0);
    colorA=p.color(0);
    colorB=p.color(0);
    entitys.points.add.add(new Fly(p,this,0,18*10,pg));//TODO
  }
  public void createCreatureC() {
    metaEntitys=new MetaCreatureCenter0001(this);
    metaEntitys.list.add(metaEntitys.player=new PlayerType2D(metaEntitys,metaEntitys.id()));
    metaEntitys.list.add(metaEntitys.fly=new FlyType(metaEntitys,metaEntitys.id()));
  }
  public void createItemC() {
    metaItems=new MetaItemCenter0001(this);
    metaItems.list.add(metaItems.empty=new MetaIntItem(metaItems,"empty",metaItems.id()) {
      @Override
      public void init() {
        tiles=new TextureRegion[1];
        tiles[0]=ImageAsset.tiles[20][1];
      }
    });
    metaItems.list.add(metaItems.dirt=new MetaIntItem(metaItems,"dirt",metaItems.id()) {
      @Override
      public void init() {
        blockType=metaBlocks.dirt;
        tiles=new TextureRegion[1];
        tiles[0]=ImageAsset.tiles[20][0];
      }
    });
  }
  public void createBlockC() {
    metaBlocks=new MetaBlockCenter0001(this);
    metaBlocks.list.add(metaBlocks.air=new MetaBlock(metaBlocks,"air",metaBlocks.id()));
    metaBlocks.list.add(metaBlocks.dirt=new Dirt(metaBlocks,metaBlocks.id()));
  }
  public boolean isEmpty(Block in) {
    return in==null||in.type.empty;
  }
  @Override
  public void init() {
    super.init();
    initSky();
    for(MetaBlock e:metaBlocks.list) e.init();
    for(MetaItem<?> e:metaItems.list) e.init();
    for(MetaCreature<?> e:metaEntitys.list) e.init();
    regions.load();
  }
  public void initSky() {
    ImageAsset.sky.getTexture().getTextureData().prepare();
    skyColorMap=ImageAsset.sky.getTexture().getTextureData().consumePixmap();
    skyColorCount=skyColorMap.getWidth();
    daySkyGridSize=(float)daySize/skyColorCount;
  }
  @Override
  public void resume() {
    super.resume();
    p.cam2d.activeDrag=false;
    p.centerCam.add.add(yourself);
  }
  @Override
  public void pause() {
    super.pause();
    p.cam2d.activeDrag=true;
    p.centerCam.remove.add(yourself);
  }
  @Override
  public void update() {
    super.update();
    time+=1;
    int tp=getSkyPos(time);
    if(skyColorPos!=tp) {
      skyColorPos=tp;
      colorA.set(colorB);
      colorB.set(getSkyColor(tp));
    }
    p.lerpColor(colorA,colorB,backgroundColor,Tools.moveInRange(time,0,daySkyGridSize)/daySkyGridSize);
    p.backgroundColor(backgroundColor);
  }
  public int getSkyColor(int pos) {
    return skyColorMap.getPixel(pos,0);
  }
  public int getSkyPos(int in) {
    return UtilMath.floor(Tools.map(in%daySize,0,daySize,0,skyColorCount));
  }
  @Override
  public void dispose() {
    super.dispose();
    regions.save();
    regions.dispose();
  }
  public void destroyBlock(MainPlayer2D player,Block block,int tx,int ty) {
    block.type(metaBlocks.air);
  }
  public void placeBlock(MainPlayer2D player,Block block,MetaBlock in,int tx,int ty) {
    block.type(in);
  }
}