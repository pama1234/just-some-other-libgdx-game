package pama1234.gdx.game.state.state0001.game.metainfo;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

import pama1234.gdx.game.app.Screen0011;
import pama1234.gdx.game.state.state0001.game.metainfo.info0001.center.MetaBlockCenter0001;
import pama1234.gdx.game.state.state0001.game.region.block.Block;
import pama1234.gdx.game.state.state0001.game.world.World0001;
import pama1234.math.UtilMath;

public class MetaBlock extends MetaInfoBase{
  public static final BlockUpdater doNothing=(in,x,y)-> {},
    fullBlockDisplayUpdater=(in,x,y)-> {
      World0001 world=in.type.pc.pw;
      int typeCache=0;
      if(Block.isEmpty(world.getBlock(x,y-1))) typeCache+=1;// up
      if(Block.isEmpty(world.getBlock(x,y+1))) typeCache+=2;// down
      if(Block.isEmpty(world.getBlock(x-1,y))) typeCache+=4;// left
      if(Block.isEmpty(world.getBlock(x+1,y))) typeCache+=8;// right
      in.displayType[0]=typeCache;
      typeCache=0;
      if(Block.isEmpty(world.getBlock(x-1,y-1))) typeCache+=1;
      if(Block.isEmpty(world.getBlock(x-1,y+1))) typeCache+=2;
      if(Block.isEmpty(world.getBlock(x+1,y+1))) typeCache+=4;
      if(Block.isEmpty(world.getBlock(x+1,y-1))) typeCache+=8;
      in.displayType[1]=typeCache;
      //---
      if(in.updateLighting) {
        int tc=0;
        for(int i=-world.lightDist;i<=world.lightDist;i++) for(int j=-world.lightDist;j<=world.lightDist;j++) if(Block.isEmpty(world.regions.getBlock(x+i,y+j))) tc+=1;
        in.lighting=UtilMath.constrain(UtilMath.floor(UtilMath.map(tc*2,0,world.lightCount,0,16)),0,16);
      }
    },defaultDisplayUpdater=(in,x,y)-> {
      World0001 world=in.type.pc.pw;
      if(in.updateLighting) {
        int tc=0;
        for(int i=-world.lightDist;i<=world.lightDist;i++) for(int j=-world.lightDist;j<=world.lightDist;j++) if(Block.isEmpty(world.regions.getBlock(x+i,y+j))) tc+=1;
        in.lighting=UtilMath.constrain(UtilMath.floor(UtilMath.map(tc*2,0,world.lightCount,0,16)),0,16);
      }
    };
  public static final BlockDisplayer fullBlockDisplayer=(p,in,x,y)-> {
    World0001 world=in.type.pc.pw;
    p.tint(getLighting(in.lighting));
    int tp_0=in.displayType[0];
    p.image(in.type.tiles[tp_0],x,y,world.blockWidth+0.01f,world.blockHeight+0.01f);
    int tp_1=in.displayType[1];
    if(tp_1!=0) {
      if((tp_0&2)+(tp_0&8)==0&&(tp_1&4)!=0) p.image(in.type.tiles[16],x,y,world.blockWidth+0.01f,world.blockHeight+0.01f);
      if((tp_0&2)+(tp_0&4)==0&&(tp_1&2)!=0) p.image(in.type.tiles[17],x,y,world.blockWidth+0.01f,world.blockHeight+0.01f);
      if((tp_0&1)+(tp_0&8)==0&&(tp_1&8)!=0) p.image(in.type.tiles[18],x,y,world.blockWidth+0.01f,world.blockHeight+0.01f);
      if((tp_0&1)+(tp_0&4)==0&&(tp_1&1)!=0) p.image(in.type.tiles[19],x,y,world.blockWidth+0.01f,world.blockHeight+0.01f);
    }
  };
  public MetaBlockCenter0001 pc;
  public boolean display,empty,light;
  public TextureRegion[] tiles;
  public int buildTime,destroyTime;
  public float hardness,lightIntensity;
  public ItemDropAttr[] itemDrop;
  public int displayTypeSize;
  public int defaultDisplayType;
  public BlockUpdater updater,displayUpdater=defaultDisplayUpdater;
  public BlockChanger from,to;
  public BlockDisplayer displayer=(p,in,x,y)-> {
    p.tint(getLighting(in.lighting));
    p.image(tiles[in.displayType[0]],x,y,pc.pw.blockWidth+0.01f,pc.pw.blockHeight+0.01f);
  };
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
  public void from(Block block,MetaBlock type) {
    if(from!=null) from.change(block,type);
  }
  public void to(Block block,MetaBlock in) {
    if(to!=null) to.change(block,in);
  }
  public void initBlockLambda() {
    updater=doNothing;
    displayUpdater=fullBlockDisplayUpdater;
    displayer=fullBlockDisplayer;
  }
  public void initItemDrop() {}
  public class ItemDropAttr{
    public MetaIntItem item;
    public int min,max;
    public float probability;
    public ItemDropAttr(MetaIntItem item,int in) {
      this.item=item;
      min=max=in;
      probability=1;
    }
  }
}