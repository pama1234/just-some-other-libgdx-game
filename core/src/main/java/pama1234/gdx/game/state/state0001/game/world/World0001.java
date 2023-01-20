package pama1234.gdx.game.state.state0001.game.world;

import com.badlogic.gdx.Gdx;
import com.esotericsoftware.kryo.serializers.TaggedFieldSerializer.Tag;

import pama1234.gdx.game.app.Screen0011;
import pama1234.gdx.game.state.state0001.Game;
import pama1234.gdx.game.state.state0001.State0001;
import pama1234.gdx.game.state.state0001.StateGenerator0001.StateEntityListener0001;
import pama1234.gdx.game.state.state0001.game.entity.GameEntityCenter;
import pama1234.gdx.game.state.state0001.game.entity.LivingEntity;
import pama1234.gdx.game.state.state0001.game.entity.entity0001.MobEntity;
import pama1234.gdx.game.state.state0001.game.metainfo.MetaBlock;
import pama1234.gdx.game.state.state0001.game.metainfo.MetaCreature;
import pama1234.gdx.game.state.state0001.game.metainfo.MetaCreature.SpawnData;
import pama1234.gdx.game.state.state0001.game.metainfo.MetaItem;
import pama1234.gdx.game.state.state0001.game.metainfo.info0001.center.MetaBlockCenter0001;
import pama1234.gdx.game.state.state0001.game.metainfo.info0001.center.MetaCreatureCenter0001;
import pama1234.gdx.game.state.state0001.game.metainfo.info0001.center.MetaItemCenter0001;
import pama1234.gdx.game.state.state0001.game.player.BlockPointer;
import pama1234.gdx.game.state.state0001.game.player.MainPlayer;
import pama1234.gdx.game.state.state0001.game.player.Player;
import pama1234.gdx.game.state.state0001.game.player.Player.PlayerCenter;
import pama1234.gdx.game.state.state0001.game.region.RegionCenter;
import pama1234.gdx.game.state.state0001.game.region.block.Block;
import pama1234.math.UtilMath;

public class World0001 extends World<Screen0011,Game> implements StateEntityListener0001{
  public static class WorldData{
    @Tag(0)
    public String name;
    @Tag(1)
    public int version;
    public WorldData(String name,int version) {
      this.name=name;
      this.version=version;
    }
  }
  public WorldData data;
  public String dataDir="data/saved/test-world/";
  //---
  public MetaBlockCenter0001 metaBlocks;
  public MetaItemCenter0001 metaItems;
  public MetaCreatureCenter0001 metaEntitys;
  //---
  public GameEntityCenter entities;
  public RegionCenter regions;
  public MainPlayer yourself;
  public WorldSettings settings=new WorldSettings();
  public int time=12000;
  public Sky sky;
  public World0001(Screen0011 p,Game pg) {
    super(p,pg,2);
    metaBlocks=World0001Generator.createBlockC(this);
    metaItems=World0001Generator.createItemC(this);
    for(MetaBlock e:metaBlocks.list) e.initItemDrop();
    metaEntitys=World0001Generator.createCreatureC(this);
    list[0]=entities=new GameEntityCenter(p);
    entities.list.add(entities.players=new PlayerCenter(p));
    list[1]=regions=new RegionCenter(p,this);
    yourself=new MainPlayer(p,this,0,0);
    sky=new Sky(this);
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
    // initSky();
    sky.init();
    for(MetaBlock e:metaBlocks.list) e.init();
    for(MetaItem e:metaItems.list) e.init();
    for(MetaCreature<?> e:metaEntitys.list) e.init();
    regions.load();
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
    sky.updateColor();
    for(Player player:entities.players.list) testCreatureSpawnWithPlayer(player);
    testCreatureSpawnWithPlayer(yourself);
  }
  public void testCreatureSpawnWithPlayer(Player player) {
    for(MetaCreature<?> e:metaEntitys.list) {
      if(e.spawnDatas==null) continue;
      if(e.count>=e.naturalMaxCount) continue;
      float rdeg=random(UtilMath.PI2);
      float rdist=random(36,regions.regionLoadDist/2f);
      float tx=player.cx()+UtilMath.sin(rdeg)*rdist*settings.blockWidth,
        ty=player.cy()+UtilMath.cos(rdeg)*rdist*settings.blockHeight;
      Block block=getBlock(tx,ty);
      if(block==null) continue;
      for(SpawnData i:e.spawnDatas) {
        if(random(1)>i.rate) continue;
        if(i.block==block.type) {
          LivingEntity out=e.createCreature(tx,ty);
          if(out instanceof MobEntity mob) {
            mob.target=player;
            entities.mobEntities.add.add(mob);
          }else entities.pointEntities.add.add(out);
          return;
        }
      }
    }
  }
  @Override
  public void dispose() {
    super.dispose();
    regions.save();
    regions.dispose();
  }
  public void updateRectLighting(int x,int y) {
    for(int i=-settings.lightDist;i<=settings.lightDist;i++) for(int j=-settings.lightDist;j<=settings.lightDist;j++) {
      Block tb=regions.getBlock(x+i,y+j);
      if(tb!=null) tb.updateLighting=true;
    }
  }
  public void destroyBlock(MainPlayer player,Block block,int x,int y) {
    placeBlock(player,block,metaBlocks.air,x,y);
  }
  public void placeBlock(MainPlayer player,Block block,MetaBlock in,int x,int y) {
    block.doItemDrop(p,x,y);
    block.type(in);
    // block.type.updateDisplay(block,x,y);
    updateRectLighting(x,y);
  }
  public void destroyBlock(BlockPointer bp,Block block,int x,int y) {
    placeBlock(bp,block,metaBlocks.air,x,y);
  }
  public void placeBlock(BlockPointer bp,Block block,MetaBlock in,int x,int y) {
    updateRectLighting(x,y);
    block.doItemDrop(p,x,y);
    block.type(in);
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