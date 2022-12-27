package pama1234.gdx.game.state.state0001.game.metainfo;

public abstract class MetaInfoBase{
  public String name;
  public int id;
  public MetaInfoBase(String name,int id) {
    this.name=name;
    this.id=id;
  }
  public abstract void init();
}
