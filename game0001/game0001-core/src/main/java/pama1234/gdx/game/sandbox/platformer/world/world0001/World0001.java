package pama1234.gdx.game.sandbox.platformer.world.world0001;

import java.util.Arrays;

import com.badlogic.gdx.Gdx;

import pama1234.gdx.game.app.Screen0011;
import pama1234.gdx.game.asset.ImageAsset;
import pama1234.gdx.game.sandbox.platformer.entity.center.MultiGameEntityCenter0001;
import pama1234.gdx.game.sandbox.platformer.metainfo.MetaBlock;
import pama1234.gdx.game.sandbox.platformer.metainfo.MetaCreature;
import pama1234.gdx.game.sandbox.platformer.metainfo.MetaItem;
import pama1234.gdx.game.sandbox.platformer.net.NetMode;
import pama1234.gdx.game.sandbox.platformer.player.MainPlayer;
import pama1234.gdx.game.sandbox.platformer.region.LoopThread;
import pama1234.gdx.game.sandbox.platformer.region.RegionCenter;
import pama1234.gdx.game.sandbox.platformer.region.block.Block;
import pama1234.gdx.game.sandbox.platformer.world.RegionWrapper;
import pama1234.gdx.game.sandbox.platformer.world.WorldBase2D;
import pama1234.gdx.game.sandbox.platformer.world.WorldData;
import pama1234.gdx.game.sandbox.platformer.world.background.BackgroundCenter;
import pama1234.gdx.game.sandbox.platformer.world.background.BackgroundList;
import pama1234.gdx.game.sandbox.platformer.world.background.Sky;
import pama1234.gdx.game.sandbox.platformer.world.background.TextureBackground;
import pama1234.gdx.game.state.state0001.Game;
import pama1234.gdx.game.state.state0001.State0001Util.StateEntity0001;
import pama1234.gdx.game.util.Mutex;

public class World0001 extends WorldBase2D<WorldType0001>{
  public World0001(Screen0011 p,Game pg,WorldType0001 type) {
    super(p,pg,3,type);

    worldDataDir=Gdx.files.local("data/saved/test-world.bin");//TODO
    if(netMode()!=NetMode.Client) {
      data=WorldData.load(worldDataDir);
      if(data.dir==null) data.dir="data/saved/test-world/";
    }else data=new WorldData();
    metaBlocks=type.metaBlocks;
    metaItems=type.metaItems;
    metaEntitys=type.metaEntitys;
    initCenter();
    regionGeneratorFix();
    r=new RegionWrapper(this);
    yourself=new MainPlayer(p,this,0,0,Gdx.files.local(data.dir+"/main-player.bin"));
    createBackground();
    sky=new Sky(this);
    saving=new Mutex(false);
  }
  @Override
  public void initCenter() {
    list[0]=background=new BackgroundCenter(p,this);
    list[1]=regions=new RegionCenter(p,this);
    list[2]=entities=new MultiGameEntityCenter0001(p,this);
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
    for(MetaBlock<?,?> e:metaBlocks.list) e.init();
    for(MetaItem e:metaItems.list) e.init();
    for(MetaCreature<?> e:metaEntitys.list) e.init();
    for(int i=0;i<background.background0001.list.size();i++) background.background0001.list.get(i).setTexture(ImageAsset.backgroundList[4-i]);
    // regionGeneratorFix();
    if(netMode()!=NetMode.Client) {
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
  public void from(StateEntity0001 in) {
    if(p.isAndroid) p.cam2d.activeDrag=p.cam2d.activeScrollZoom=false;
    else p.cam2d.active(false);
    p.cam2d.scale.pos=yourself.camScale;
    p.cam2d.scale.des=yourself.camScale;
    // p.cam2d.minScale=0.25f;
    p.cam2d.minScale=p.isAndroid?0.25f:0.5f;
    p.cam2d.testScale();
    p.cam.point.pos.set(yourself.cx(),yourself.cy());
    p.cam.point.des.set(p.cam.point.pos);
    p.centerCam.add.add(yourself);
  }
  @Override
  public void to(StateEntity0001 in) {
    p.cam2d.activeDrag=true;
    p.cam2d.active(true);
    p.cam2d.minScale=1;
    p.cam2d.testScale();
    yourself.camScale=p.cam2d.scale.des;
    p.centerCam.remove.add(yourself);
  }
  @Override
  public void resume() {
    if(p.isAndroid&&pg.netMode!=NetMode.Client) resumeLoad();
    super.resume();
  }
  @Override
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
    if(p.isAndroid&&pg.netMode!=NetMode.Client) {
      for(LoopThread e:regions.loops) e.doFinished=(self)-> {
        boolean flag=true;
        for(LoopThread i:regions.loops) if(!i.finished) flag=false;
        if(flag) pauseSave();
      };
    }
  }
  @Override
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
    yourself.camScale=p.cam2d.scale.des;
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
    if(pg.netMode!=NetMode.Client) disposeSave();
  }
  /**
   * 在不处于多人游戏客户端模式时进行保存
   */
  public void disposeSave() {
    if(p.settings.printLog) System.out.println("World0001.disposeSave()");
    WorldData.save(worldDataDir,data);
    yourself.save();
    regions.stop=true;
    regions.save();
    regions.dispose();

    WorldMetaInfoUtil.saveBlockC(metaBlocks);
  }
  @Deprecated
  public void updateRectLighting(int x,int y) {
    for(int i=-settings.lightDist;i<=settings.lightDist;i++) for(int j=-settings.lightDist;j<=settings.lightDist;j++) {
      Block tb=regions.getBlock(x+i,y+j);
      if(tb!=null) tb.updateLighting=true;
    }
  }
}