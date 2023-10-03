package pama1234.gdx.game.sandbox.platformer.world;

import pama1234.data.TreeNode;
import pama1234.gdx.game.app.Screen0011;
import pama1234.gdx.game.sandbox.platformer.entity.GamePointEntity;
import pama1234.gdx.game.sandbox.platformer.entity.LivingEntity;
import pama1234.gdx.game.sandbox.platformer.entity.center.MultiGameEntityCenter0001;
import pama1234.gdx.game.sandbox.platformer.entity.entity0001.DroppedItem;
import pama1234.gdx.game.sandbox.platformer.item.Item;
import pama1234.gdx.game.sandbox.platformer.metainfo.info0001.center.MetaBlockCenter0001;
import pama1234.gdx.game.sandbox.platformer.metainfo.info0001.center.MetaCreatureCenter0001;
import pama1234.gdx.game.sandbox.platformer.metainfo.info0001.center.MetaItemCenter0001;
import pama1234.gdx.game.sandbox.platformer.net.NetMode;
import pama1234.gdx.game.sandbox.platformer.player.MainPlayer;
import pama1234.gdx.game.sandbox.platformer.region.RegionCenter;
import pama1234.gdx.game.sandbox.platformer.region.block.Block;
import pama1234.gdx.game.sandbox.platformer.world.background.BackgroundCenter;
import pama1234.gdx.game.sandbox.platformer.world.background.Sky;
import pama1234.gdx.game.sandbox.platformer.world.world0001.WorldType0001Base;
import pama1234.gdx.game.state.state0001.Game;
import pama1234.gdx.game.state.state0001.State0001Util.StateEntityListener0001;
import pama1234.gdx.game.util.Mutex;
import pama1234.math.UtilMath;

// WorldBase2D<M extends MetaWorld<?,?,?,?>>
public abstract class WorldBase2D<M extends WorldType0001Base<?>>extends World<Screen0011,Game> implements StateEntityListener0001{
  public TreeNode<WorldBase2D<?>> node;

  public int typeId;
  public M type;

  public WorldSettings settings=new WorldSettings();
  public WorldData data;

  public MetaBlockCenter0001<?> metaBlocks;//方块
  public MetaItemCenter0001<?> metaItems;//物品
  public MetaCreatureCenter0001<?> metaEntitys;//生物

  public MultiGameEntityCenter0001 entities;//实体
  public RegionCenter regions;//地图系统
  public BackgroundCenter background;//背景

  public MainPlayer yourself;
  public float timeF;
  public Sky sky;

  public RegionWrapper r;
  public Mutex saving;

  public int entityIdCount;
  public WorldBase2D(Screen0011 p,Game pg,int size,M type) {
    super(p,pg,size);
    node=new TreeNode<>(this);
    this.type=type;
    typeId=type.id;
  }
  public abstract void initCenter();
  public float random(float max) {
    return p.random(max);
  }
  public float random(float min,float max) {
    return p.random(min,max);
  }
  public abstract void pauseSave();
  public abstract void resumeLoad();
  public NetMode netMode() {
    return pg.netMode;
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
  public void dropItem(LivingEntity pe,Item item,float x,float y) {
    DroppedItem.dropItem_2(p,pe,x,y,this,2f,item);
  }
  public int newEntityId(GamePointEntity<?> gamePointEntity) {//TODO
    return entityIdCount++;
  }
}