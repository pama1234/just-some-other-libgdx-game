package pama1234.gdx.game.state.state0001.game.region;

public class Block{
  public MetaBlock type;
  public int displayType;
  public Block(MetaBlock type) {
    this.type=type;
    displayType=type.getDisplayType();
  }
}
