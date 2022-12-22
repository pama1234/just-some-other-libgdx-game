package pama1234.gdx.game.state.state0001.game.item;

import pama1234.gdx.game.state.state0001.game.world.World0001;
import pama1234.util.wrapper.Center;

public class MetaItemCenter extends Center<MetaItem<?>>{
  public World0001 pw;
  public MetaItem<?> dirt;
  public MetaItemCenter(World0001 pw) {
    this.pw=pw;
  }
}