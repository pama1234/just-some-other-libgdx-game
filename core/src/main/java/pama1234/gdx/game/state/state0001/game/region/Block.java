package pama1234.gdx.game.state.state0001.game.region;

public class Block{
  public MetaBlock type;
  public int[] displayType;
  public Block(MetaBlock type) {
    this.type=type;
    init(type);
  }
  public void init(MetaBlock type) {
    if(type.displayTypeSize>0) {
      displayType=new int[type.displayTypeSize];
      displayType[0]=type.getDisplayType();
    }else displayType=null;
  }
  public void type(MetaBlock in) {
    MetaBlock t=type;
    if(in==t) return;
    type=in;
    init(in);//TODO
    t.to(this,in);
    in.from(this,t);
  }
}