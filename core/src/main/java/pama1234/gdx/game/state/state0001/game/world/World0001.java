package pama1234.gdx.game.state.state0001.game.world;

import java.util.Arrays;

import com.badlogic.gdx.Gdx;

import pama1234.gdx.game.app.Screen0011;
import pama1234.gdx.game.asset.ImageAsset;
import pama1234.gdx.game.state.state0001.Game;
import pama1234.gdx.game.state.state0001.State0001;
import pama1234.gdx.game.state.state0001.game.entity.MultiGameEntityCenter;
import pama1234.gdx.game.state.state0001.game.metainfo.MetaBlock;
import pama1234.gdx.game.state.state0001.game.metainfo.MetaCreature;
import pama1234.gdx.game.state.state0001.game.metainfo.MetaItem;
import pama1234.gdx.game.state.state0001.game.metainfo.info0001.center.MetaBlockCenter0001;
import pama1234.gdx.game.state.state0001.game.metainfo.info0001.center.MetaCreatureCenter0001;
import pama1234.gdx.game.state.state0001.game.metainfo.info0001.center.MetaItemCenter0001;
import pama1234.gdx.game.state.state0001.game.player.BlockPointer;
import pama1234.gdx.game.state.state0001.game.player.MainPlayer;
import pama1234.gdx.game.state.state0001.game.player.Player.PlayerCenter;
import pama1234.gdx.game.state.state0001.game.region.RegionCenter;
import pama1234.gdx.game.state.state0001.game.region.block.Block;
import pama1234.gdx.game.state.state0001.game.world.background.BackgroundCenter;
import pama1234.gdx.game.state.state0001.game.world.background.BackgroundSet;
import pama1234.gdx.game.state.state0001.game.world.background.Sky;
import pama1234.gdx.game.state.state0001.game.world.background.TextureBackground;
import pama1234.math.UtilMath;

public class World0001 extends WorldBase2D{
  public String dataDir="data/saved/test-world/";
  //---
  public MetaBlockCenter0001 metaBlocks;
  public MetaItemCenter0001 metaItems;
  public MetaCreatureCenter0001 metaEntitys;
  //---
  public MultiGameEntityCenter entities;
  public RegionCenter regions;
  public BackgroundCenter background;
  public MainPlayer yourself;
  public WorldSettings settings=new WorldSettings();
  public int time=12000;
  // public int time=72000;
  public float timeF;
  public Sky sky;
  public World0001(Screen0011 p,Game pg) {
    super(p,pg,3);
    metaBlocks=World0001Generator.createBlockC(this);
    metaItems=World0001Generator.createItemC(this);
    for(MetaBlock e:metaBlocks.list) e.initItemDrop();
    metaEntitys=World0001Generator.createCreatureC(this);
    list[0]=background=new BackgroundCenter(p,this);
    list[1]=regions=new RegionCenter(p,this);
    list[2]=entities=new MultiGameEntityCenter(p,this);
    entities.list.add(entities.players=new PlayerCenter(p));
    yourself=new MainPlayer(p,this,0,0);
    createBackground();
    sky=new Sky(this);
  }
  public void createBackground() {
    background.list.add(background.background0001=new BackgroundSet(p,background));
    BackgroundSet ts=background.background0001;
    ts.list.addAll(Arrays.asList(new TextureBackground[] {
      new TextureBackground(p,ts,yourself).setProportion(0.1f),
      new TextureBackground(p,ts,yourself).setProportion(0.2f),
      new TextureBackground(p,ts,yourself).setProportion(0.3f),
      new TextureBackground(p,ts,yourself).setProportion(0.4f)
    }));
  }
  public float random(float max) {
    return p.random(max);
  }
  public float random(float min,float max) {
    return p.random(min,max);
  }
  public boolean isEmpty(Block in) {
    return in==null||in.type.empty;
  }
  @Override
  public void init() {
    super.init();
    sky.init();
    for(MetaBlock e:metaBlocks.list) e.init();
    for(MetaItem e:metaItems.list) e.init();
    for(MetaCreature<?> e:metaEntitys.list) e.init();
    for(int i=0;i<background.background0001.list.size();i++) background.background0001.list.get(i).setTexture(ImageAsset.backgroundList[4-i]);
    regions.load();
    // background.clouds0001.setTexture(ImageAsset.background);
    Gdx.files.local(dataDir+"regions/").mkdirs();//TODO
  }
  @Override
  public void from(State0001 in) {
    innerResume();
  }
  @Override
  public void to(State0001 in) {
    innerPause();
  }
  @Override
  public void resume() {
    if(p.isAndroid) regions.load();
    super.resume();
  }
  public void innerResume() {
    if(p.isAndroid) p.cam2d.activeDrag=false;
    else p.cam2d.active(false);
    p.cam2d.scale.pos=yourself.ctrl.camScale;
    p.cam2d.scale.des=yourself.ctrl.camScale;
    p.cam2d.minScale=p.isAndroid?0.25f:0.5f;
    p.cam.point.pos.set(yourself.point.pos);
    p.cam.point.des.set(yourself.point.pos);
    p.centerCam.add.add(yourself);
  }
  @Override
  public void pause() {
    super.pause();
    if(p.isAndroid) regions.innerSave();
  }
  public void innerPause() {
    if(p.isAndroid) p.cam2d.activeDrag=true;
    else p.cam2d.active(true);
    p.cam2d.minScale=1;
    yourself.ctrl.camScale=p.cam2d.scale.des;
    p.centerCam.remove.add(yourself);
  }
  @Override
  public void update() {
    super.update();
    time+=1;
    timeF+=p.frameRate;
    sky.updateColor();
  }
  @Override
  public void dispose() {
    super.dispose();
    regions.save();
    regions.dispose();
  }
  public void destroyBlock(MainPlayer player,Block block,int x,int y) {
    placeBlock(player,block,metaBlocks.air,x,y);
  }
  public void destroyBlock(BlockPointer bp,Block block,int x,int y) {
    placeBlock(bp,block,metaBlocks.air,x,y);
  }
  public void destroyBlock(Block block,int x,int y) {
    placeBlock(block,metaBlocks.air,x,y);
  }
  public void placeBlock(MainPlayer player,Block block,MetaBlock in,int x,int y) {
    placeBlock(block,in,x,y);
  }
  public void placeBlock(BlockPointer bp,Block block,MetaBlock in,int x,int y) {
    placeBlock(block,in,x,y);
  }
  public void placeBlock(Block block,MetaBlock in,int x,int y) {
    // updateRectLighting(x,y);
    block.doItemDrop(p,x,y);
    block.type(in);
  }
  public void setBlock(Block block,MetaBlock in,int x,int y) {
    block.type(in);
    // block.type.updateDisplay(block,x,y);
    // updateRectLighting(x,y);
  }
  public void setBlock(MetaBlock in,int x,int y) {
    setBlock(getBlock(x,y),in,x,y);
    // if(block!=null) block.type(in);
    // else System.err.println("World0001.setBlock() "+x+" "+y+" block==null");
  }
  @Deprecated
  public void updateRectLighting(int x,int y) {
    for(int i=-settings.lightDist;i<=settings.lightDist;i++) for(int j=-settings.lightDist;j<=settings.lightDist;j++) {
      Block tb=regions.getBlock(x+i,y+j);
      if(tb!=null) tb.updateLighting=true;
    }
  }
  public int blockWidth() {
    return settings.blockWidth;
  }
  public int blockHeight() {
    return settings.blockHeight;
  }
  public int xToBlockCord(float in) {
    return UtilMath.floor(in/settings.blockWidth);
  }
  public int yToBlockCord(float in) {
    return UtilMath.floor(in/settings.blockHeight);
  }
  public Block getBlock(int x,int y) {
    return regions.getBlock(x,y);
  }
  public Block getBlock(float x,float y) {
    return regions.getBlock(xToBlockCord(x),yToBlockCord(y));
  }
  public float skyLight() {
    return sky.skyHsb[2];
  }
}
