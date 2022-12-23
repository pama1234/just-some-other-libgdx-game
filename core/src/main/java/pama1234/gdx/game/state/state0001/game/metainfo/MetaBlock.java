package pama1234.gdx.game.state.state0001.game.metainfo;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

import pama1234.gdx.game.app.Screen0011;
import pama1234.gdx.game.state.state0001.game.region.block.Block;

public class MetaBlock extends MetaInfoBase{
  public MetaBlockCenter pc;
  public String name;
  public boolean display,empty,light;
  public TextureRegion[] tiles;
  public int buildTime,destroyTime;
  public float hardness,lightIntensity;
  public int displayTypeSize;
  public int defaultDisplayType;
  public BlockUpdater updater;
  public BlockChanger from,to;
  public BlockDisplayer displayer=(p,in,x,y)->p.image(tiles[in.displayType[0]],x,y,pc.pw.blockWidth+0.01f,pc.pw.blockHeight+0.01f);
  public MetaBlock(MetaBlockCenter pc,String name) {
    this.pc=pc;
    this.name=name;
    empty=true;
  }
  public MetaBlock(MetaBlockCenter pc,String name,TextureRegion[] tiles) {
    this.pc=pc;
    this.name=name;
    this.tiles=tiles;
    display=true;
    displayTypeSize=1;
  }
  public MetaBlock(MetaBlockCenter pc,String name,TextureRegion[] tiles,BlockUpdater updater) {
    this(pc,name,tiles);
    this.updater=updater;
  }
  public MetaBlock(MetaBlockCenter pc,String name,TextureRegion[] tiles,int displayTypeSize,BlockChanger from,BlockChanger to) {//TODO
    this(pc,name,tiles);
    this.displayTypeSize=displayTypeSize;
    this.from=from;
    this.to=to;
  }
  public MetaBlock(MetaBlockCenter pc,String name,TextureRegion[] tiles,BlockUpdater updater,BlockDisplayer displayer,int displayTypeSize,BlockChanger from,BlockChanger to) {
    this(pc,name,tiles,updater);
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