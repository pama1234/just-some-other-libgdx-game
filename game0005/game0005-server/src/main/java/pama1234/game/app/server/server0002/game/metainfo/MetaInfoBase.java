package pama1234.game.app.server.server0002.game.metainfo;

public abstract class MetaInfoBase{
  public String name;
  public int id;
  public int[] intData;
  public MetaInfoBase(String name,int id) {
    this.name=name;
    this.id=id;
  }
  public abstract void init();
}
