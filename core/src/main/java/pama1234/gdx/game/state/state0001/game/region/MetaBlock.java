package pama1234.gdx.game.state.state0001.game.region;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

import pama1234.gdx.game.app.Screen0011;

public class MetaBlock{
  public MetaBlockCenter pc;
  public String name;
  public boolean display,empty;
  public TextureRegion[] tiles;
  public int defaultDisplayType;
  public BlockUpdater updater;
  public MetaBlock(MetaBlockCenter p,String name) {
    this.pc=p;
    this.name=name;
    empty=true;
  }
  public MetaBlock(MetaBlockCenter pc,String name,TextureRegion[] tiles,BlockUpdater updater) {
    this.pc=pc;
    this.name=name;
    this.tiles=tiles;
    this.updater=updater;
    display=true;
  }
  public void update(Block in,int x,int y) {
    if(updater!=null) updater.update(in,x,y);
  }
  public void display(Screen0011 p,Block in,int x,int y) {
    p.image(tiles[in.displayType],x,y,pc.pw.blockWidth+0.01f,pc.pw.blockHeight+0.01f);
  }
  public int getDisplayType() {//TODO
    return defaultDisplayType;
  }
  @FunctionalInterface
  public interface BlockUpdater{
    void update(Block in,int x,int y);
  }
}