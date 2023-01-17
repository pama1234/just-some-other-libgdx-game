package pama1234.gdx.game.state.state0001.game.metainfo;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

import pama1234.gdx.game.app.Screen0011;
import pama1234.gdx.game.state.state0001.game.metainfo.info0001.center.MetaBlockCenter0001;
import pama1234.gdx.game.state.state0001.game.region.block.Block;
import pama1234.gdx.game.state.state0001.game.world.World0001;
import pama1234.math.UtilMath;

public class MetaBlock extends MetaInfoBase{
  public static final BlockUpdater doNothing=(in,x,y)-> {},lightUpdater=(in,x,y)-> {
    in.light.update();
  },fullBlockDisplayUpdater=(in,x,y)-> {
    World0001 world=in.type.pc.pw;
    int typeCache=0;
    if(Block.isNotFullBlock(world.getBlock(x,y-1))) typeCache+=1;// up
    if(Block.isNotFullBlock(world.getBlock(x,y+1))) typeCache+=2;// down
    if(Block.isNotFullBlock(world.getBlock(x-1,y))) typeCache+=4;// left
    if(Block.isNotFullBlock(world.getBlock(x+1,y))) typeCache+=8;// right
    in.displayType[0]=typeCache;
    typeCache=0;
    if(Block.isNotFullBlock(world.getBlock(x-1,y-1))) typeCache+=1;
    if(Block.isNotFullBlock(world.getBlock(x-1,y+1))) typeCache+=2;
    if(Block.isNotFullBlock(world.getBlock(x+1,y+1))) typeCache+=4;
    if(Block.isNotFullBlock(world.getBlock(x+1,y-1))) typeCache+=8;
    in.displayType[1]=typeCache;
    //---
    if(in.updateLighting) lightingUpdate(in,x,y,world);
    // in.light.update();
  },defaultDisplayUpdater=(in,x,y)-> {
    World0001 world=in.type.pc.pw;
    if(in.updateLighting) lightingUpdate(in,x,y,world);
    // in.light.update();
  };
  public static void lightingUpdate(Block in,int x,int y,World0001 world) {
    float cr=0;
    int lDist=world.lightDist;
    for(int i=-lDist;i<=lDist;i++) for(int j=-lDist;j<=lDist;j++) {
      float mag=UtilMath.mag(i,j);
      if(mag>lDist) continue;
      Block block=world.regions.getBlock(x+i,y+j);
      if(Block.isNotFullBlock(block)) cr+=world.skyLight();
      if(block!=null&&block.type.light) cr+=block.type.lightIntensity*(1-mag/lDist);
      // if(block!=null&&block.type.light) cr+=block.type.lightIntensity*((lDist-mag)/lDist);
    }
    in.light.set(worldLighting(world.lightCount,cr));
  }
  public static int worldLighting(float in,float count) {
    return UtilMath.constrain(UtilMath.floor(UtilMath.map(count*2,0,in,0,16)),0,16);
  }
  public static final BlockDisplayer defaultBlockDisplayer=(p,in,x,y)-> {
    World0001 world=in.type.pc.pw;
    p.tint(
      getLighting(in.light.r()),
      getLighting(in.light.g()),
      getLighting(in.light.b()));
    int tp_0=in.displayType[0];
    p.innerImage(in.type.tiles[tp_0],x,y,world.blockWidth+0.01f,world.blockHeight+0.01f);
  },fullBlockDisplayer=(p,in,x,y)-> {
    World0001 world=in.type.pc.pw;
    p.tint(
      getLighting(in.light.r()),
      getLighting(in.light.g()),
      getLighting(in.light.b()));
    int tp_0=in.displayType[0];
    p.innerImage(in.type.tiles[tp_0],x,y,world.blockWidth+0.01f,world.blockHeight+0.01f);
    int tp_1=in.displayType[1];
    if(tp_1!=0) {
      if((tp_0&2)+(tp_0&8)==0&&(tp_1&4)!=0) p.innerImage(in.type.tiles[16],x,y,world.blockWidth+0.01f,world.blockHeight+0.01f);
      if((tp_0&2)+(tp_0&4)==0&&(tp_1&2)!=0) p.innerImage(in.type.tiles[17],x,y,world.blockWidth+0.01f,world.blockHeight+0.01f);
      if((tp_0&1)+(tp_0&8)==0&&(tp_1&8)!=0) p.innerImage(in.type.tiles[18],x,y,world.blockWidth+0.01f,world.blockHeight+0.01f);
      if((tp_0&1)+(tp_0&4)==0&&(tp_1&1)!=0) p.innerImage(in.type.tiles[19],x,y,world.blockWidth+0.01f,world.blockHeight+0.01f);
    }
  };
  public MetaBlockCenter0001 pc;
  public boolean display,empty,light;//TODO need light boolean?
  public TextureRegion[] tiles;
  public int buildTime=1,destroyTime=1;
  public float hardness,lightIntensity;
  public boolean fullBlock=true;
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
  public void update(Block in,int x,int y) {
    if(updater!=null) updater.update(in,x,y);
  }
  public void updateDisplay(Block in,int x,int y) {
    // if(displayUpdater!=null)
    displayUpdater.update(in,x,y);
  }
  public void display(Screen0011 p,Block in,int x,int y) {
    // p.image(tiles[in.displayType],x,y,pc.pw.blockWidth+0.01f,pc.pw.blockHeight+0.01f);
    displayer.display(p,in,x,y);
  }
  public int getDisplayType() {//TODO
    return defaultDisplayType;
  }
  public void from(Block block,MetaBlock type) {
    if(from!=null) from.change(block,type);
  }
  public void to(Block block,MetaBlock in) {
    if(to!=null) to.change(block,in);
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
  public class ItemDropAttr{
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
    void update(Block in,int x,int y);
  }
  @FunctionalInterface
  public interface BlockDisplayer{
    void display(Screen0011 p,Block in,int x,int y);
  }
  @FunctionalInterface
  public interface BlockChanger{
    void change(Block block,MetaBlock type);
  }
}