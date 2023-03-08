package pama1234.gdx.game.state.state0001.game.metainfo;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

import pama1234.game.app.server.server0002.game.metainfo.MetaInfoBase;
import pama1234.gdx.game.app.Screen0011;
import pama1234.gdx.game.state.state0001.game.metainfo.info0001.center.MetaBlockCenter0001;
import pama1234.gdx.game.state.state0001.game.region.block.Block;
import pama1234.gdx.game.state.state0001.game.world.WorldBase2D;
import pama1234.gdx.game.state.state0001.game.world.world0001.World0001;
import pama1234.math.UtilMath;

public class MetaBlock extends MetaInfoBase{
  public static class FullBlockType{
    public static final int stoneType=0,leafType=1,plankType=2,platformType=3;//,oreType=4;
  }
  public static final int noType=0,everyType=1,dirtType=2,stoneType=3,woodType=4;//blockType
  public MetaBlockCenter0001 pc;
  public boolean display,empty,light;//TODO need light boolean?
  public TextureRegion[] tiles;
  public int buildTime=1,destroyTime=1;
  public float hardness=1,lightIntensity;
  public int blockType;
  public boolean fullBlock=true;
  // public boolean overrideFullBlock;
  public int fullBlockType;
  public boolean workStation;
  public int width=1,height=1;
  public boolean[][] rectSolid;
  public ItemDropAttr[] itemDrop;
  public int displayTypeSize;
  public int defaultDisplayType;
  public BlockUpdater updater=lightUpdater,displayUpdater=defaultDisplayUpdater;
  public BlockChanger from,to;
  public BlockDisplayer displayer=defaultBlockDisplayer;
  public static int getLighting(int in) {
    in<<=4;
    if(in>255) return 255;
    return in&0xff;
  }
  public static int getLighting(float in) {
    return UtilMath.constrain(UtilMath.floor(in*16),0,255);
  }
  public MetaBlock(MetaBlockCenter0001 pc,String name,int id) {
    super(name,id);
    this.pc=pc;
    this.name=name;
    empty=true;
    itemDrop=new ItemDropAttr[0];
  }
  public MetaBlock(MetaBlockCenter0001 pc,
    String name,int id,
    int tilesSize,
    BlockUpdater updater,BlockUpdater displayUpdater,BlockDisplayer displayer,
    int displayTypeSize,
    BlockChanger from,BlockChanger to) {
    this(pc,name,id,tilesSize,displayTypeSize,from,to);
    this.updater=updater;
    this.displayUpdater=displayUpdater;
    this.displayer=displayer;
  }
  public MetaBlock(MetaBlockCenter0001 pc,
    String name,int id,
    int tilesSize,
    int displayTypeSize,
    BlockChanger from,BlockChanger to) {
    super(name,id);
    this.pc=pc;
    this.name=name;
    this.tiles=new TextureRegion[tilesSize];
    display=true;
    this.displayTypeSize=displayTypeSize;
    this.from=from;
    this.to=to;
  }
  @Override
  public void init() {}
  public void update(World0001 world,Block in,int x,int y) {
    if(updater!=null) updater.update(world,in,x,y);
  }
  public void updateDisplay(World0001 world,Block in,int x,int y) {
    displayUpdater.update(world,in,x,y);
  }
  public void display(TilemapRenderer r,Screen0011 p,World0001 world,Block in,int x,int y) {
    // p.image(tiles[in.displayType],x,y,pc.pw.blockWidth+0.01f,pc.pw.blockHeight+0.01f);
    displayer.display(r,p,world,in,x,y);
  }
  public int getDisplayType() {//TODO
    return defaultDisplayType;
  }
  public void initBlock(WorldBase2D world,Block in) {}
  public void from(World0001 world,Block block,MetaBlock type,int x,int y) {
    if(from!=null) from.change(world,block,type,x,y);
  }
  public void to(World0001 world,Block block,MetaBlock in,int x,int y) {
    // block.intData=null;
    if(to!=null) to.change(world,block,in,x,y);
  }
  public void initFullBlockLambda() {
    // updater=lightUpdater;
    displayUpdater=fullBlockDisplayUpdater;
    displayer=fullBlockDisplayer;
  }
  public void setLightIntensity(float in) {
    light=true;
    lightIntensity=in;
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
    void change(World0001 world,Block block,MetaBlock type,int x,int y);
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
    //---
    if(in.updateLighting) lightingUpdate(in,x,y,world);
  },defaultDisplayUpdater=(world,in,x,y)-> {
    if(in.updateLighting) lightingUpdate(in,x,y,world);
  };
  public static boolean isNotFullBlockOrNotSameType(Block b,Block a) {
    return Block.isNotFullBlock(a)||(a.type.fullBlockType!=b.type.fullBlockType);
  }
  public static boolean isNotFullBlockOrNotSameType(Block a,int fullBlockType) {
    return Block.isNotFullBlock(a)||(a.type.fullBlockType!=fullBlockType);
  }
  public static void lightingUpdate(Block in,int x,int y,World0001 world) {
    float cr=0;
    int lDist=world.settings.lightDist;
    for(int i=-lDist;i<=lDist;i++) for(int j=-lDist;j<=lDist;j++) {
      float mag=UtilMath.mag(i,j);
      if(mag>lDist) continue;
      Block block=world.regions.getBlock(x+i,y+j);
      if(Block.isNotFullBlock(block)) cr+=world.skyLight();
      if(block!=null&&block.type.light) cr+=block.type.lightIntensity*(1-mag/lDist);
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
    if(in.displayType==null) r.tile(in.type.tiles[0],x,y);
    else r.tile(in.type.tiles[in.displayType[0]],x,y);
  },fullBlockDisplayer=(r,p,world,in,x,y)-> {
    // if(in.light.isDark())
    r.tint(
      getLighting(in.light.r()),
      getLighting(in.light.g()),
      getLighting(in.light.b()));
    int tp_0=in.displayType[0];
    r.tile(in.type.tiles[tp_0],x,y);
    int tp_1=in.displayType[1];
    if(tp_1!=0) {
      if((tp_0&2)+(tp_0&8)==0&&(tp_1&4)!=0) r.tile(in.type.tiles[16],x,y);
      if((tp_0&2)+(tp_0&4)==0&&(tp_1&2)!=0) r.tile(in.type.tiles[17],x,y);
      if((tp_0&1)+(tp_0&8)==0&&(tp_1&8)!=0) r.tile(in.type.tiles[18],x,y);
      if((tp_0&1)+(tp_0&4)==0&&(tp_1&1)!=0) r.tile(in.type.tiles[19],x,y);
    }
  };
}