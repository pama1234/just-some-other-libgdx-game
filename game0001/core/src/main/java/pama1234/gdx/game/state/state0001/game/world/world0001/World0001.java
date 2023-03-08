package pama1234.gdx.game.state.state0001.game.world.world0001;

import java.util.Arrays;

import com.badlogic.gdx.Gdx;

import pama1234.gdx.game.app.Screen0011;
import pama1234.gdx.game.asset.ImageAsset;
import pama1234.gdx.game.state.state0001.Game;
import pama1234.gdx.game.state.state0001.State0001;
import pama1234.gdx.game.state.state0001.game.entity.LivingEntity;
import pama1234.gdx.game.state.state0001.game.entity.center.MultiGameEntityCenter0001;
import pama1234.gdx.game.state.state0001.game.entity.entity0001.DroppedItem;
import pama1234.gdx.game.state.state0001.game.item.Item;
import pama1234.gdx.game.state.state0001.game.metainfo.MetaBlock;
import pama1234.gdx.game.state.state0001.game.metainfo.MetaCreature;
import pama1234.gdx.game.state.state0001.game.metainfo.MetaItem;
import pama1234.gdx.game.state.state0001.game.metainfo.info0001.center.MetaBlockCenter0001;
import pama1234.gdx.game.state.state0001.game.metainfo.info0001.center.MetaCreatureCenter0001;
import pama1234.gdx.game.state.state0001.game.metainfo.info0001.center.MetaItemCenter0001;
import pama1234.gdx.game.state.state0001.game.net.NetMode;
import pama1234.gdx.game.state.state0001.game.player.MainPlayer;
import pama1234.gdx.game.state.state0001.game.region.LoopThread;
import pama1234.gdx.game.state.state0001.game.region.RegionCenter;
import pama1234.gdx.game.state.state0001.game.region.block.Block;
import pama1234.gdx.game.state.state0001.game.world.RegionWrapper;
import pama1234.gdx.game.state.state0001.game.world.WorldBase2D;
import pama1234.gdx.game.state.state0001.game.world.WorldData;
import pama1234.gdx.game.state.state0001.game.world.WorldSettings;
import pama1234.gdx.game.state.state0001.game.world.background.BackgroundCenter;
import pama1234.gdx.game.state.state0001.game.world.background.BackgroundList;
import pama1234.gdx.game.state.state0001.game.world.background.Sky;
import pama1234.gdx.game.state.state0001.game.world.background.TextureBackground;
import pama1234.gdx.game.util.Mutex;
import pama1234.math.UtilMath;

public class World0001 extends WorldBase2D<WorldType0001>{
  public WorldData data;
  //---
  public MetaBlockCenter0001 metaBlocks;//方块
  public MetaItemCenter0001 metaItems;//物品
  public MetaCreatureCenter0001 metaEntitys;//生物
  //---
  public MultiGameEntityCenter0001 entities;//实体
  public RegionCenter regions;//地图系统
  public BackgroundCenter background;//背景
  //---
  public MainPlayer yourself;
  public WorldSettings settings=new WorldSettings();
  public float timeF;
  public Sky sky;
  //---
  public RegionWrapper r;
  public Mutex saving;
  public World0001(Screen0011 p,Game pg,WorldType0001 type) {
    super(p,pg,3,type);
    this.type=type;
    //---
    worldDataDir=Gdx.files.local("data/saved/test-world.bin");//TODO
    if(netMode()!=NetMode.client) data=WorldData.load(worldDataDir);
    else data=new WorldData();
    if(data.dir==null) data.dir="data/saved/test-world/";
    metaBlocks=type.metaBlocks;
    metaItems=type.metaItems;
    metaEntitys=type.metaEntitys;
    // metaBlocks=World0001Generator.createBlockC(type);
    // metaItems=World0001Generator.createItemC(type);
    // for(MetaBlock e:metaBlocks.list) e.initItemDrop();
    // metaEntitys=World0001Generator.createCreatureC(type);
    list[0]=background=new BackgroundCenter(p,this);
    list[1]=regions=new RegionCenter(p,this);
    list[2]=entities=new MultiGameEntityCenter0001(p,this);
    regionGeneratorFix();
    r=new RegionWrapper(this);
    yourself=new MainPlayer(p,this,0,0,Gdx.files.local(data.dir+"/main-player.bin"));
    createBackground();
    sky=new Sky(this);
    saving=new Mutex(false);
  }
  @Deprecated
  public void regionGeneratorFix() {
    regions.pool.gen.pe=entities;//TODO
  }
  public String dir() {
    return data.dir;
  }
  public void createBackground() {
    background.list.add(background.background0001=new BackgroundList(p,background));
    BackgroundList tbl=background.background0001;
    tbl.list.addAll(Arrays.asList(new TextureBackground[] {
      new TextureBackground(p,tbl,yourself).setProportion(0.1f),
      new TextureBackground(p,tbl,yourself).setProportion(0.2f),
      new TextureBackground(p,tbl,yourself).setProportion(0.3f),
      new TextureBackground(p,tbl,yourself).setProportion(0.4f)
    }));
  }
  @Override
  public void init() {
    super.init();
    sky.init();
    for(MetaBlock e:metaBlocks.list) e.init();
    for(MetaItem e:metaItems.list) e.init();
    for(MetaCreature<?> e:metaEntitys.list) e.init();
    for(int i=0;i<background.background0001.list.size();i++) background.background0001.list.get(i).setTexture(ImageAsset.backgroundList[4-i]);
    // regionGeneratorFix();
    if(netMode()!=NetMode.client) {
      Gdx.files.local(dir()+"regions/").mkdirs();//TODO
      yourself.load();
      regions.load();
      regions.startAllLoop();
    }else {
      regions.updateDisplayLoop.start();
      regions.updateLoop.start();
    }
    yourself.init();
  }
  @Override
  public void from(State0001 in) {
    if(p.isAndroid) p.cam2d.activeDrag=p.cam2d.activeScrollZoom=false;
    else p.cam2d.active(false);
    p.cam2d.scale.pos=yourself.ctrl.camScale;
    p.cam2d.scale.des=yourself.ctrl.camScale;
    // p.cam2d.minScale=0.25f;
    p.cam2d.minScale=p.isAndroid?0.25f:0.5f;
    p.cam2d.testScale();
    p.cam.point.pos.set(yourself.cx(),yourself.cy());
    p.cam.point.des.set(p.cam.point.pos);
    p.centerCam.add.add(yourself);
  }
  @Override
  public void to(State0001 in) {
    p.cam2d.activeDrag=true;
    p.cam2d.active(true);
    p.cam2d.minScale=1;
    p.cam2d.testScale();
    yourself.ctrl.camScale=p.cam2d.scale.des;
    p.centerCam.remove.add(yourself);
  }
  @Override
  public void resume() {
    if(p.isAndroid&&pg.netMode!=NetMode.client) resumeLoad();
    super.resume();
  }
  public void resumeLoad() {
    for(LoopThread e:regions.loops) e.doFinished=LoopThread.doNothing;
    if(p.settings.printLog) System.out.println("World0001.resumeLoad()");
    saving.step();
    yourself.load();
    regions.load();
  }
  @Override
  public void pause() {
    super.pause();
    if(p.isAndroid&&pg.netMode!=NetMode.client) {
      for(LoopThread e:regions.loops) e.doFinished=(self)-> {
        boolean flag=true;
        for(LoopThread i:regions.loops) if(!i.finished) flag=false;
        if(flag) pauseSave();
      };
    }
  }
  public void pauseSave() {
    saving.lock();
    if(p.settings.printLog) System.out.println("World0001.pauseSave()");
    WorldData.save(worldDataDir,data);
    regions.innerSave();
    yourself.save();
    saving.unlock();
  }
  public void innerPause() {
    if(p.isAndroid) p.cam2d.activeDrag=true;
    else p.cam2d.active(true);
    p.cam2d.minScale=1;
    p.cam2d.testScale();
    yourself.ctrl.camScale=p.cam2d.scale.des;
    p.centerCam.remove.add(yourself);
  }
  @Override
  public void update() {
    super.update();
    data.time+=1;
    timeF+=p.frameRate;
    sky.updateColor();
  }
  @Override
  public void dispose() {
    super.dispose();
    if(pg.netMode!=NetMode.client) disposeSave();
  }
  public void disposeSave() {
    if(p.settings.printLog) System.out.println("World0001.disposeSave()");
    WorldData.save(worldDataDir,data);
    yourself.save();
    regions.stop=true;
    regions.save();
    regions.dispose();
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
  public int xToBlockCordInt(float in) {
    return UtilMath.floor(in/settings.blockWidth);
  }
  public int yToBlockCordInt(float in) {
    return UtilMath.floor(in/settings.blockHeight);
  }
  public float xToBlockCordFloat(float in) {
    return in/settings.blockWidth;
  }
  public float yToBlockCordFloat(float in) {
    return in/settings.blockHeight;
  }
  public Block getBlock(int x,int y) {
    return regions.getBlock(x,y);
  }
  public Block getBlock(float x,float y) {
    return regions.getBlock(xToBlockCordInt(x),yToBlockCordInt(y));
  }
  public float skyLight() {
    return sky.skyHsb[2];
  }
  public NetMode netMode() {
    return pg.netMode;
  }
  public void dropItem(LivingEntity pe,Item item,float x,float y) {
    DroppedItem.dropItem_2(p,pe,x,y,this,2f,item);
  }
}