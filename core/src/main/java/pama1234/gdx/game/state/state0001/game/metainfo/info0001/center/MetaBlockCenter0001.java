package pama1234.gdx.game.state.state0001.game.metainfo.info0001.center;

import pama1234.gdx.game.state.state0001.game.metainfo.MetaBlock;
import pama1234.gdx.game.state.state0001.game.metainfo.MetaInfoUtil.MetaBlockCenter;
import pama1234.gdx.game.state.state0001.game.region.block.block0001.Dirt;
import pama1234.gdx.game.state.state0001.game.world.IDGenerator;
import pama1234.gdx.game.state.state0001.game.world.World0001;

public class MetaBlockCenter0001 extends MetaBlockCenter{
  public World0001 pw;
  public IDGenerator idg;
  public Dirt dirt;
  public MetaBlock air;
  public MetaBlockCenter0001(World0001 pw) {
    this.pw=pw;
    idg=new IDGenerator();
  }
  public int id() {
    return idg.get();
  }
}