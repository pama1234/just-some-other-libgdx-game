package pama1234.gdx.game.sandbox.platformer.region.block;

import pama1234.gdx.game.sandbox.platformer.metainfo.MetaBlock;
import pama1234.gdx.game.sandbox.platformer.metainfo.info0001.center.MetaBlockCenter0001;
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

/** 方块类型第一页 */
public class BlockGenerator0001{

  /** 加载方块类型第一页 */
  public static void load(MetaBlockCenter0001<WorldType0001Base<?>> metaBlocks) {
    metaBlocks.list.add(metaBlocks.air=new MetaBlock<>(metaBlocks,"air",metaBlocks.id()));
    metaBlocks.list.add(metaBlocks.dirt=new Dirt(metaBlocks,metaBlocks.id()));
    metaBlocks.list.add(metaBlocks.stone=new Stone(metaBlocks,metaBlocks.id()));
    metaBlocks.list.add(metaBlocks.log=new TreeLog(metaBlocks,metaBlocks.id()));
    metaBlocks.list.add(metaBlocks.branch=new TreeBranch(metaBlocks,metaBlocks.id()));
    metaBlocks.list.add(metaBlocks.leaf=new TreeLeaf(metaBlocks,metaBlocks.id()));
    metaBlocks.list.add(metaBlocks.workbench=new Workbench(metaBlocks,metaBlocks.id()));
    metaBlocks.list.add(metaBlocks.sapling=new Sapling(metaBlocks,metaBlocks.id()));
    metaBlocks.list.add(metaBlocks.torch=new Torch(metaBlocks,metaBlocks.id()));
    metaBlocks.list.add(metaBlocks.woodPlank=new WoodPlank(metaBlocks,metaBlocks.id()));
    metaBlocks.list.add(metaBlocks.woodPlatform=new WoodPlatform(metaBlocks,metaBlocks.id()));
    metaBlocks.list.add(metaBlocks.furnace=new Furnace(metaBlocks,metaBlocks.id()));
    metaBlocks.list.add(metaBlocks.doorClosed=new Door(metaBlocks,metaBlocks.id(),true));
    metaBlocks.list.add(metaBlocks.chest=new Chest(metaBlocks,metaBlocks.id()));
    metaBlocks.list.add(metaBlocks.lightOre=new LightOre(metaBlocks,metaBlocks.id()));
    metaBlocks.list.add(metaBlocks.nullBlock=new NullBlock(metaBlocks,metaBlocks.id()));
    metaBlocks.list.add(metaBlocks.colorBlock=new ColorBlock(metaBlocks,metaBlocks.id()));
    metaBlocks.list.add(metaBlocks.lightBlock=new LightBlock(metaBlocks,metaBlocks.id()));
    metaBlocks.list.add(metaBlocks.doorClosed=new Door(metaBlocks,metaBlocks.id(),false));
    metaBlocks.list.add(metaBlocks.worldDebugger=new WorldDebugger(metaBlocks,metaBlocks.id()));
  }
}
