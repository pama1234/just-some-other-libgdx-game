package pama1234.gdx.game.state.state0001.game.world;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.Arrays;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.esotericsoftware.kryo.KryoException;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import com.esotericsoftware.kryo.serializers.TaggedFieldSerializer.Tag;

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
import pama1234.gdx.game.state.state0001.game.world.background.BackgroundList;
import pama1234.gdx.game.state.state0001.game.world.background.Sky;
import pama1234.gdx.game.state.state0001.game.world.background.TextureBackground;
import pama1234.math.UtilMath;

public class World0001 extends WorldBase2D{
  public static class WorldData{
    @Tag(0)
    public String dir;
    @Tag(1)
    public int time=12000;
    @Tag(2)
    public int tick;
    public static WorldData load(FileHandle file) {
      if(file.exists()) try(Input input=new Input(new FileInputStream(file.file()))) {
        WorldData out=WorldKryoUtil.kryo.readObject(input,WorldData.class);
        input.close();
        return out;
      }catch(FileNotFoundException|KryoException e) {
        e.printStackTrace();
      }
      return new WorldData();
    }
    public static void save(FileHandle file,WorldData in) {
      try(Output output=new Output(new FileOutputStream(file.file()))) {
        WorldKryoUtil.kryo.writeObject(output,in);
        output.close();
      }catch(FileNotFoundException|KryoException e) {
        e.printStackTrace();
      }
    }
  }
  public FileHandle worldDataDir=Gdx.files.local("data/saved/test-world.bin");
  public WorldData data;
  //---
  public MetaBlockCenter0001 metaBlocks;//方块
  public MetaItemCenter0001 metaItems;//物品
  public MetaCreatureCenter0001 metaEntitys;//生物
  //---
  public MultiGameEntityCenter entities;//实体
  public RegionCenter regions;//地图系统
  public BackgroundCenter background;//背景
  //---
  public MainPlayer yourself;
  public WorldSettings settings=new WorldSettings();
  // public int time=72000;
  public float timeF;
  public Sky sky;
  public World0001(Screen0011 p,Game pg) {
    super(p,pg,3);
    data=WorldData.load(worldDataDir);
    if(data.dir==null) data.dir="data/saved/test-world/";
    metaBlocks=World0001Generator.createBlockC(this);
    metaItems=World0001Generator.createItemC(this);
    for(MetaBlock e:metaBlocks.list) e.initItemDrop();
    metaEntitys=World0001Generator.createCreatureC(this);
    list[0]=background=new BackgroundCenter(p,this);
    list[1]=regions=new RegionCenter(p,this);
    list[2]=entities=new MultiGameEntityCenter(p,this);
    entities.list.add(entities.players=new PlayerCenter(p));
    yourself=new MainPlayer(p,this,0,0,Gdx.files.local(data.dir+"/main-player.bin"));
    createBackground();
    sky=new Sky(this);
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
    Gdx.files.local(dir()+"regions/").mkdirs();//TODO
    regions.load();
    yourself.load();
    yourself.init();
  }
  @Override
  public void from(State0001 in) {
    if(p.isAndroid) p.cam2d.activeDrag=p.cam2d.activeScrollZoom=false;
    else p.cam2d.active(false);
    p.cam2d.scale.pos=yourself.ctrl.camScale;
    p.cam2d.scale.des=yourself.ctrl.camScale;
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
    if(p.isAndroid) {
      regions.load();
      yourself.load();
    }
    super.resume();
  }
  @Override
  public void pause() {
    super.pause();
    if(p.isAndroid) pauseSave();
  }
  public void pauseSave() {
    regions.innerSave();
    yourself.save();
    WorldData.save(worldDataDir,data);
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
    // System.out.println(yourself.point.pos);
  }
  @Override
  public void dispose() {
    super.dispose();
    regions.save();
    regions.dispose();
    yourself.save();
    WorldData.save(worldDataDir,data);
    // yourself.dispose();
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
