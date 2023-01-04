package pama1234.gdx.game.state.state0001.game.world;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;

import pama1234.gdx.game.app.Screen0011;
import pama1234.gdx.game.asset.ImageAsset;
import pama1234.gdx.game.state.state0001.Game;
import pama1234.gdx.game.state.state0001.game.entity.GameEntityCenter;
import pama1234.gdx.game.state.state0001.game.metainfo.MetaBlock;
import pama1234.gdx.game.state.state0001.game.metainfo.MetaCreature;
import pama1234.gdx.game.state.state0001.game.metainfo.MetaItem;
import pama1234.gdx.game.state.state0001.game.metainfo.info0001.center.MetaBlockCenter0001;
import pama1234.gdx.game.state.state0001.game.metainfo.info0001.center.MetaCreatureCenter0001;
import pama1234.gdx.game.state.state0001.game.metainfo.info0001.center.MetaItemCenter0001;
import pama1234.gdx.game.state.state0001.game.player.MainPlayer;
import pama1234.gdx.game.state.state0001.game.player.Player.PlayerCenter;
import pama1234.gdx.game.state.state0001.game.region.RegionCenter;
import pama1234.gdx.game.state.state0001.game.region.block.Block;
import pama1234.math.Tools;
import pama1234.math.UtilMath;

public class World0001 extends World<Screen0011,Game>{
  public MetaBlockCenter0001 metaBlocks;
  public MetaItemCenter0001 metaItems;
  public MetaCreatureCenter0001 metaEntitys;
  public GameEntityCenter entities;
  public PlayerCenter players;
  public RegionCenter regions;
  public MainPlayer yourself;
  public int blockWidth=18,blockHeight=18;
  public float g=1f,jumpForce=-blockHeight*1.5f;
  // public int daySize=3600;
  public int daySize=216000/3;
  public int time=12000;
  public float ambientLight;
  public int lightDist=7,lightCount=UtilMath.sq(lightDist*2+1);
  public int typeCache;
  public Pixmap skyColorMap;
  public int skyColorPos,skyColorCount;
  public float daySkyGridSize;
  public Color backgroundColor,colorA,colorB;
  public World0001(Screen0011 p,Game pg) {
    super(p,pg,2);
    metaBlocks=World0001Generator.createBlockC(this);
    metaItems=World0001Generator.createItemC(this);
    for(MetaBlock e:metaBlocks.list) e.initItemDrop();
    metaEntitys=World0001Generator.createCreatureC(this);
    // list[0]=players=new PlayerCenter2D(p);
    list[0]=entities=new GameEntityCenter(p);
    entities.list.add(entities.players=players=new PlayerCenter(p));
    list[1]=regions=new RegionCenter(p,this,Gdx.files.local("data/saved/regions.bin"));
    // regions.load();
    yourself=new MainPlayer(p,this,0,-1,pg);
    backgroundColor=p.color(0);
    colorA=p.color(0);
    colorB=p.color(0);
    // entitys.points.add.add(new Fly(p,this,0,18*10,pg));//TODO
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
    // p.cam2d.activeDrag=false;
    p.cam2d.active(false);
    p.cam2d.scale.pos=yourself.ctrl.camScale;
    p.cam2d.scale.des=yourself.ctrl.camScale;
    p.cam2d.minScale=2;
    p.cam.point.pos.set(yourself.point.pos);
    p.cam.point.des.set(yourself.point.pos);
    p.centerCam.add.add(yourself);
  }
  @Override
  public void pause() {
    super.pause();
    // p.cam2d.activeDrag=true;
    p.cam2d.active(true);
    p.cam2d.minScale=1;
    yourself.ctrl.camScale=p.cam2d.scale.des;
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
  public void updateRectLighting(int x,int y) {
    for(int i=-lightDist;i<=lightDist;i++) for(int j=-lightDist;j<=lightDist;j++) {
      Block tb=regions.getBlock(x+i,y+j);
      if(tb!=null) tb.updateLighting=true;
    }
  }
  public void destroyBlock(MainPlayer player,Block block,int x,int y) {
    // updateRectLighting(x,y);
    // block.doItemDrop(x,y);
    // block.type(metaBlocks.air);
    placeBlock(player,block,metaBlocks.air,x,y);
  }
  public void placeBlock(MainPlayer player,Block block,MetaBlock in,int x,int y) {
    updateRectLighting(x,y);
    block.doItemDrop(x,y);
    block.type(in);
  }
  public int xToBlockCord(float in) {
    return UtilMath.floor(in/blockWidth);
  }
  public int yToBlockCord(float in) {
    return UtilMath.floor(in/blockHeight);
  }
  public Block getBlock(int x,int y) {
    return regions.getBlock(x,y);
  }
  public Block getBlock(float x,float y) {
    return regions.getBlock(xToBlockCord(x),yToBlockCord(y));
  }
}