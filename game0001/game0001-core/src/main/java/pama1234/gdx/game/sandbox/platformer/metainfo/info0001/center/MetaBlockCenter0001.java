package pama1234.gdx.game.sandbox.platformer.metainfo.info0001.center;

import pama1234.gdx.game.sandbox.platformer.metainfo.MetaBlock;
import pama1234.gdx.game.sandbox.platformer.metainfo.MetaInfoCenters.MetaBlockCenter;
import pama1234.gdx.game.sandbox.platformer.region.block.block0001.Dirt;
import pama1234.gdx.game.sandbox.platformer.region.block.block0001.Door;
import pama1234.gdx.game.sandbox.platformer.region.block.block0001.LightOre;
import pama1234.gdx.game.sandbox.platformer.region.block.block0001.NullBlock;
import pama1234.gdx.game.sandbox.platformer.region.block.block0001.Sapling;
import pama1234.gdx.game.sandbox.platformer.region.block.block0001.Stone;
import pama1234.gdx.game.sandbox.platformer.region.block.block0001.Torch;
import pama1234.gdx.game.sandbox.platformer.region.block.block0001.TreeBranch;
import pama1234.gdx.game.sandbox.platformer.region.block.block0001.TreeLeaf;
import pama1234.gdx.game.sandbox.platformer.region.block.block0001.TreeLog;
import pama1234.gdx.game.sandbox.platformer.region.block.block0001.WoodPlank;
import pama1234.gdx.game.sandbox.platformer.region.block.block0001.WoodPlatform;
import pama1234.gdx.game.sandbox.platformer.region.block.block0002.ColorBlock;
import pama1234.gdx.game.sandbox.platformer.region.block.block0002.LightBlock;
import pama1234.gdx.game.sandbox.platformer.region.block.block0002.WorldDebugger;
import pama1234.gdx.game.sandbox.platformer.region.block.workstation.Chest;
import pama1234.gdx.game.sandbox.platformer.region.block.workstation.Furnace;
import pama1234.gdx.game.sandbox.platformer.region.block.workstation.Workbench;
import pama1234.gdx.game.sandbox.platformer.world.world0001.WorldType0001Base;

public class MetaBlockCenter0001<M extends WorldType0001Base<?>>extends MetaBlockCenter<M>{
  public MetaBlock<M,MetaBlockCenter0001<M>> air;
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
  public Furnace furnace;
  public Door doorClosed;
  public Chest chest;
  public LightOre lightOre;
  public NullBlock nullBlock;

  public ColorBlock colorBlock;
  public LightBlock lightBlock;
  public WorldDebugger worldDebugger;
  public MetaBlockCenter0001(M pw) {
    super(pw);
  }
}