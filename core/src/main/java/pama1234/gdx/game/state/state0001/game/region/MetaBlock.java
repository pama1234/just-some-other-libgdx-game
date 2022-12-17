package pama1234.gdx.game.state.state0001.game.region;

public class MetaBlock{
  public MetaBlockCenter p;
  public String name;
  public int tileX,tileY;
  public MetaBlock(MetaBlockCenter p) {
    this.p=p;
  }
  public MetaBlock(MetaBlockCenter p,String name,int tileX,int tileY) {
    this.p=p;
    this.name=name;
    this.tileX=tileX;
    this.tileY=tileY;
  }
}