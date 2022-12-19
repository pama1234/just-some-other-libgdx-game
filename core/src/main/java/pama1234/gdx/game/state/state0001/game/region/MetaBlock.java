package pama1234.gdx.game.state.state0001.game.region;

import pama1234.gdx.game.app.Screen0011;
import pama1234.gdx.game.asset.ImageAsset;

public class MetaBlock{
  public MetaBlockCenter pc;
  public String name;
  public boolean display,empty;
  public int tileX,tileY;
  public MetaBlock(MetaBlockCenter p,String name) {
    this.pc=p;
    this.name=name;
    empty=true;
  }
  public MetaBlock(MetaBlockCenter p,String name,int tileX,int tileY) {
    this.pc=p;
    this.name=name;
    this.tileX=tileX;
    this.tileY=tileY;
    display=true;
  }
  public void display(Screen0011 p,Block in,int x,int y) {
    p.image(ImageAsset.tiles[tileX][tileY],x,y,pc.pw.blockWidth+0.001f,pc.pw.blockHeight+0.001f);
  }
  // public void update(Block in,int x,int y) {}
}