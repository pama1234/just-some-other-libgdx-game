package pama1234.gdx.game.state.state0001.game.metainfo;

import pama1234.gdx.game.state.state0001.game.world.World0001;
import pama1234.util.wrapper.Center;

public class MetaItemCenter extends Center<MetaItem<?>>{
  public World0001 pw;
  public MetaIntItem dirt;
  public MetaIntItem empty;
  public MetaItemCenter(World0001 pw) {
    this.pw=pw;
  }
}