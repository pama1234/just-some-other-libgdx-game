package pama1234.gdx.game.state.state0001.game.metainfo;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

import pama1234.gdx.game.app.Screen0011;
import pama1234.gdx.game.state.state0001.game.metainfo.info0001.center.MetaBlockCenter0001;
import pama1234.gdx.game.state.state0001.game.region.block.Block;

public class MetaBlock extends MetaInfoBase{
  public MetaBlockCenter0001 pc;
  public boolean display,empty,light;
  public TextureRegion[] tiles;
  public int buildTime,destroyTime;
  public float hardness,lightIntensity;
  public int displayTypeSize;
  public int defaultDisplayType;
  public BlockUpdater updater,displayUpdater;
  public BlockChanger from,to;
  public BlockDisplayer displayer=(p,in,x,y)-> {
    p.tint(getLighting(in.lighting));
    p.image(tiles[in.displayType[0]],x,y,pc.pw.blockWidth+0.01f,pc.pw.blockHeight+0.01f);
  };
  public int getLighting(int in) {
    in<<=4;
    if(in>255) return 255;
    return in&0xff;
  }
  public MetaBlock(MetaBlockCenter0001 pc,String name,int id) {
    super(name,id);
    this.pc=pc;
    this.name=name;
    empty=true;
  }
  public MetaBlock(MetaBlockCenter0001 pc,String name,int id,TextureRegion[] tiles) {
    super(name,id);
    this.pc=pc;
    this.name=name;
    this.tiles=tiles;
    display=true;
    displayTypeSize=1;
  }
  public MetaBlock(MetaBlockCenter0001 pc,String name,int id,TextureRegion[] tiles,BlockUpdater updater,BlockUpdater displayUpdater) {
    this(pc,name,id,tiles);
    this.updater=updater;
    this.displayUpdater=displayUpdater;
  }
  public MetaBlock(MetaBlockCenter0001 pc,String name,int id,TextureRegion[] tiles,int displayTypeSize,BlockChanger from,BlockChanger to) {//TODO
    this(pc,name,id,tiles);
    this.displayTypeSize=displayTypeSize;
    this.from=from;
    this.to=to;
  }
  public MetaBlock(MetaBlockCenter0001 pc,String name,int id,TextureRegion[] tiles,BlockUpdater updater,BlockUpdater displayUpdater,BlockDisplayer displayer,int displayTypeSize,BlockChanger from,BlockChanger to) {
    this(pc,name,id,tiles,updater,displayUpdater);
    this.displayer=displayer;
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
    if(displayUpdater!=null) displayUpdater.update(in,x,y);
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
}