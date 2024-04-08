package pama1234.gdx.game.sandbox.platformer.metainfo;

import pama1234.gdx.game.app.Screen0011;
import pama1234.gdx.game.asset.ImageAsset;
import pama1234.gdx.game.sandbox.platformer.metainfo.MetaBlock.MetaBlockAttribute;
import pama1234.gdx.game.sandbox.platformer.metainfo.MetaBlock.MetaBlockRuntimeAttribute;
import pama1234.gdx.game.sandbox.platformer.metainfo.MetaBlock.MetaBlockStoredAttribute;
import pama1234.gdx.game.sandbox.platformer.metainfo.MetaInfoCenters.MetaBlockCenter;
import pama1234.gdx.game.sandbox.platformer.metainfo.io.MetaPropertiesCenter;
import pama1234.gdx.game.sandbox.platformer.region.block.Block;
import pama1234.gdx.game.sandbox.platformer.world.WorldBase2D;
import pama1234.gdx.game.sandbox.platformer.world.world0001.World0001;
import pama1234.math.UtilMath;
import pama1234.server.game.app.server0002.game.metainfo.MetaInfoBase;
import pama1234.server.game.app.server0002.game.metainfo.io.PlainAttribute;
import pama1234.server.game.app.server0002.game.metainfo.io.RuntimeAttribute;
import pama1234.server.game.app.server0002.game.metainfo.io.StoredAttribute;
import pama1234.server.game.app.server0002.game.metainfo.io.TextureRegionInfo;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class MetaBlock<M extends MetaWorld<?,?,?,?>,C extends MetaBlockCenter<M>>
  extends MetaInfoBase<MetaBlockAttribute,MetaBlockStoredAttribute,MetaBlockRuntimeAttribute>{
  public static class FullBlockType{
    public static final int stoneType=0,leafType=1,plankType=2,platformType=3;//,oreType=4;
  }
  /** 方块的种类，影响破坏方块需要的工具种类 */
  public static final int noType=0,everyType=1,dirtType=2,stoneType=3,woodType=4;//blockType
  public C pc;
  {
    attr=new MetaBlockAttribute();
    sttr=new MetaBlockStoredAttribute();
    rttr=new MetaBlockRuntimeAttribute();
  }
  public static class MetaBlockAttribute extends PlainAttribute{
    public boolean display,empty,light;//TODO need light boolean?
    public int buildTime=1,destroyTime=1;
    public float hardness=1,lightIntensity;
    public int blockType;
    public boolean fullBlock=true;
    public int fullBlockType;
    public boolean workStation;
    public int width=1,height=1;
    /** 如果方块占用多个格子（例如熔炉），而定义的方框区域中有些格子并不属于这个方块，那么数组的对应位置就设置为false，数组为null表示所有格子都被方块占用 */
    public boolean[][] rectSolid;
    public int displayTypeSize;
    public int defaultDisplayType;
  }
  public static class MetaBlockStoredAttribute extends StoredAttribute{
    public TextureRegionInfo[] tiles;
    public StoredItemDropAttr[] itemDrop;

    public ProgramUnit updater,displayUpdater;
    public ProgramUnit from,to;
    public ProgramUnit displayer;
  }
  public static class MetaBlockRuntimeAttribute extends RuntimeAttribute{
    public TextureRegion[] tiles;
    public ItemDropAttr[] itemDrop;
    //以下内容的序列化难度较高
    public BlockUpdater updater=lightUpdater,displayUpdater=defaultDisplayUpdater;
    public BlockChanger from,to;
    public BlockDisplayer displayer=defaultBlockDisplayer;
  }

  @Override
  public void loadRuntimeAttribute() {
    if(sttr.tiles!=null) {
      rttr.tiles=new TextureRegion[sttr.tiles.length];
      for(int i=0;i<sttr.tiles.length;i++) {
        if(sttr.tiles[i]!=null) {
          rttr.tiles[i]=MetaPropertiesCenter.newTextureRegion(ImageAsset.tilesTexture,sttr.tiles[i]);
        }
      }
    }

    //    if(sttr.itemDrop!=null) {
    //      rttr.itemDrop=new ItemDropAttr[sttr.itemDrop.length];
    //      for(int i=0;i<sttr.itemDrop.length;i++) {
    //        if(sttr.itemDrop[i]!=null) {
    //          rttr.itemDrop[i]=sttr.itemDrop[i].toItemDropAttr();
    //        }
    //      }
    //    }

  }
  @Override
  public void saveRuntimeAttribute() {
    if(rttr.tiles!=null) {
      sttr.tiles=new TextureRegionInfo[rttr.tiles.length];
      for(int i=0;i<rttr.tiles.length;i++) {
        if(rttr.tiles[i]!=null) sttr.tiles[i]=MetaPropertiesCenter.newTextureRegionInfo("image/tiles.png",rttr.tiles[i]);
        //        System.out.println(i);
      }
    }
  }

  //  public static int getLighting(int in) {
  //    in<<=4;
  //    if(in>255) return 255;
  //    return in&0xff;
  //  }
  public static int getLighting(float in) {
    return UtilMath.constrain(UtilMath.floor(in*16),0,255);
  }
  public MetaBlock(C pc,String name,int id) {
    super(name,id);
    this.pc=pc;
    this.name=name;
    attr.empty=true;
    rttr.itemDrop=new ItemDropAttr[0];
  }
  public MetaBlock(C pc,
    String name,int id,
    int tilesSize,
    BlockUpdater updater,BlockUpdater displayUpdater,BlockDisplayer displayer,
    int displayTypeSize,
    BlockChanger from,BlockChanger to) {
    this(pc,name,id,tilesSize,displayTypeSize,from,to);
    this.rttr.updater=updater;
    this.rttr.displayUpdater=displayUpdater;
    this.rttr.displayer=displayer;
  }
  public MetaBlock(C pc,
    String name,int id,
    int tilesSize,
    int displayTypeSize,
    BlockChanger from,BlockChanger to) {
    super(name,id);
    this.pc=pc;
    this.name=name;
    this.rttr.tiles=new TextureRegion[tilesSize];
    attr.display=true;
    this.attr.displayTypeSize=displayTypeSize;
    this.rttr.from=from;
    this.rttr.to=to;
  }
  @Override
  public void init() {}
  public void update(World0001 world,Block in,int x,int y) {
    if(rttr.updater!=null) rttr.updater.update(world,in,x,y);
  }
  public void updateDisplay(World0001 world,Block in,int x,int y) {
    rttr.displayUpdater.update(world,in,x,y);
  }
  public void display(TilemapRenderer r,Screen0011 p,World0001 world,Block in,int x,int y) {
    // p.image(tiles[in.displayType],x,y,pc.pw.blockWidth+0.01f,pc.pw.blockHeight+0.01f);
    rttr.displayer.display(r,p,world,in,x,y);
  }
  public int getDisplayType() {//TODO
    return attr.defaultDisplayType;
  }
  public void initBlock(WorldBase2D<?> world,Block in) {}
  public void from(World0001 world,Block block,MetaBlock<?,?> type,int x,int y) {
    if(rttr.from!=null) rttr.from.change(world,block,type,x,y);
  }
  public void to(World0001 world,Block block,MetaBlock<?,?> in,int x,int y) {
    // block.intData=null;
    if(rttr.to!=null) rttr.to.change(world,block,in,x,y);
  }
  public void initFullBlockLambda() {
    // updater=lightUpdater;
    rttr.displayUpdater=fullBlockDisplayUpdater;
    rttr.displayer=fullBlockDisplayer;
  }
  public void setLightIntensity(float in) {
    attr.light=true;
    attr.lightIntensity=in;
  }
  public void initItemDrop() {}
  public static class ItemDropAttr{
    public MetaItem item;
    public int min,max;
    public float probability;
    public ItemDropAttr(MetaItem item,int in) {
      this.item=item;
      min=max=in;
      probability=1;
    }
    public int dropNumber(World0001 world) {
      if(probability==1||world.random(1)<probability) return (int)world.random(min,max);
      else return 0;
    }
  }
  public static class StoredItemDropAttr{
    public int itemId;
    public int min,max;
    public float probability;
  }
  @FunctionalInterface
  public interface BlockUpdater{
    void update(World0001 world,Block in,int x,int y);
  }
  @FunctionalInterface
  public interface BlockDisplayer{
    void display(TilemapRenderer r,Screen0011 p,World0001 world,Block in,int x,int y);
  }
  @FunctionalInterface
  public interface BlockChanger{
    void change(World0001 world,Block block,MetaBlock<?,?> type,int x,int y);
  }
  public interface TilemapRenderer{
    public void tint(float x,float y,float z);
    public void tile(TextureRegion in,boolean next);
    public void tile(TextureRegion in,float x,float y);
    public void tile(TextureRegion in,float x,float y,float w,float h);
    public void begin();
    public void end();
  }
  public static final BlockUpdater doNothing=(w,in,x,y)-> {},lightUpdater=(w,in,x,y)-> {
    in.light.update();
  };
  public static final BlockUpdater fullBlockDisplayUpdater=(world,in,x,y)-> {
    int typeCache=0;
    if(isNotFullBlockOrNotSameType(in,world.getBlock(x,y-1))) typeCache+=1;// up
    if(isNotFullBlockOrNotSameType(in,world.getBlock(x,y+1))) typeCache+=2;// down
    if(isNotFullBlockOrNotSameType(in,world.getBlock(x-1,y))) typeCache+=4;// left
    if(isNotFullBlockOrNotSameType(in,world.getBlock(x+1,y))) typeCache+=8;// right
    in.displayType[0]=typeCache;
    typeCache=0;
    if(isNotFullBlockOrNotSameType(in,world.getBlock(x-1,y-1))) typeCache+=1;
    if(isNotFullBlockOrNotSameType(in,world.getBlock(x-1,y+1))) typeCache+=2;
    if(isNotFullBlockOrNotSameType(in,world.getBlock(x+1,y+1))) typeCache+=4;
    if(isNotFullBlockOrNotSameType(in,world.getBlock(x+1,y-1))) typeCache+=8;
    in.displayType[1]=typeCache;

    if(in.updateLighting) lightingUpdate(in,x,y,world);
  },defaultDisplayUpdater=(world,in,x,y)-> {
    if(in.updateLighting) lightingUpdate(in,x,y,world);
  };
  public static boolean isNotFullBlockOrNotSameType(Block b,Block a) {
    return Block.isNotFullBlock(a)||(a.type.attr.fullBlockType!=b.type.attr.fullBlockType);
  }
  public static boolean isNotFullBlockOrNotSameType(Block a,int fullBlockType) {
    return Block.isNotFullBlock(a)||(a.type.attr.fullBlockType!=fullBlockType);
  }
  public static void lightingUpdate(Block in,int x,int y,World0001 world) {
    float cr=0;
    int lDist=world.settings.lightDist;
    for(int i=-lDist;i<=lDist;i++) for(int j=-lDist;j<=lDist;j++) {
      float mag=UtilMath.mag(i,j);
      if(mag>lDist) continue;
      Block block=world.regions.getBlock(x+i,y+j);
      if(Block.isNotFullBlock(block)) cr+=world.skyLight();
      if(block!=null&&block.type.attr.light) cr+=block.type.attr.lightIntensity*(1-mag/lDist);
      // if(block!=null&&block.type.light) cr+=block.type.lightIntensity*((lDist-mag)/lDist);
    }
    in.light.set(worldLighting(world.settings.lightCount,cr));
  }
  public static int worldLighting(float in,float count) {
    return UtilMath.constrain(UtilMath.floor(UtilMath.map(count*2,0,in,0,16)),0,16);
  }
  public static final BlockDisplayer defaultBlockDisplayer=(r,p,world,in,x,y)-> {
    r.tint(
      getLighting(in.light.r()),
      getLighting(in.light.g()),
      getLighting(in.light.b()));
    if(in.displayType==null) r.tile(in.type.rttr.tiles[0],x,y);
    else r.tile(in.type.rttr.tiles[in.displayType[0]],x,y);
  },fullBlockDisplayer=(r,p,world,in,x,y)-> {
    // if(in.light.isDark())
    r.tint(
      getLighting(in.light.r()),
      getLighting(in.light.g()),
      getLighting(in.light.b()));
    int tp_0=in.displayType[0];
    r.tile(in.type.rttr.tiles[tp_0],x,y);
    int tp_1=in.displayType[1];
    if(tp_1!=0) {
      if((tp_0&2)+(tp_0&8)==0&&(tp_1&4)!=0) r.tile(in.type.rttr.tiles[16],x,y);
      if((tp_0&2)+(tp_0&4)==0&&(tp_1&2)!=0) r.tile(in.type.rttr.tiles[17],x,y);
      if((tp_0&1)+(tp_0&8)==0&&(tp_1&8)!=0) r.tile(in.type.rttr.tiles[18],x,y);
      if((tp_0&1)+(tp_0&4)==0&&(tp_1&1)!=0) r.tile(in.type.rttr.tiles[19],x,y);
    }
  };
}