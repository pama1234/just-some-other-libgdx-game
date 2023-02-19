package pama1234.gdx.game.state.state0001.game.metainfo.info0001.center;

import pama1234.game.app.server.server0002.game.metainfo.IDGenerator;
import pama1234.gdx.game.state.state0001.game.metainfo.MetaBlock;
import pama1234.gdx.game.state.state0001.game.metainfo.MetaInfoUtil.MetaBlockCenter;
import pama1234.gdx.game.state.state0001.game.region.block.block0001.Dirt;
import pama1234.gdx.game.state.state0001.game.region.block.block0001.Sapling;
import pama1234.gdx.game.state.state0001.game.region.block.block0001.Stone;
import pama1234.gdx.game.state.state0001.game.region.block.block0001.Torch;
import pama1234.gdx.game.state.state0001.game.region.block.block0001.TreeBranch;
import pama1234.gdx.game.state.state0001.game.region.block.block0001.TreeLeaf;
import pama1234.gdx.game.state.state0001.game.region.block.block0001.TreeLog;
import pama1234.gdx.game.state.state0001.game.region.block.block0001.WoodPlank;
import pama1234.gdx.game.state.state0001.game.region.block.block0001.WoodPlatform;
import pama1234.gdx.game.state.state0001.game.region.block.workstation.Workbench;
import pama1234.gdx.game.state.state0001.game.world.World0001;

public class MetaBlockCenter0001 extends MetaBlockCenter{
  public World0001 pw;
  public IDGenerator idg;
  public MetaBlock air;
  public Dirt dirt;
  public Stone stone;
  public TreeLog log;
  public TreeBranch branch;
  public TreeLeaf leaf;
  public Workbench workbench;
  public Sapling sapling;
  public Torch torch;
  public WoodPlank woodPlank;
  public WoodPlatform woodPlatform;
  public MetaBlockCenter0001(World0001 pw) {
    this.pw=pw;
    idg=new IDGenerator();
  }
  public int id() {
    return idg.get();
  }
}