package pama1234.gdx.game.state.state0001.game.region;

import pama1234.gdx.game.state.state0001.game.world.World0001;
import pama1234.util.wrapper.Center;

public class MetaBlockCenter extends Center<MetaBlock>{
  public World0001 pw;
  public MetaBlock dirt,air;
  public MetaBlockCenter(World0001 pw) {
    this.pw=pw;
  }
}