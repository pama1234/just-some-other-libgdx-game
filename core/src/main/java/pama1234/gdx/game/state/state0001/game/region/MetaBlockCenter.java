package pama1234.gdx.game.state.state0001.game.region;

import pama1234.gdx.game.state.state0001.game.region.block0001.Dirt;
import pama1234.gdx.game.state.state0001.game.world.World0001;
import pama1234.util.wrapper.Center;

public class MetaBlockCenter extends Center<MetaBlock>{
  public World0001 pw;
  public Dirt dirt;
  public MetaBlock air;
  public MetaBlockCenter(World0001 pw) {
    this.pw=pw;
  }
}