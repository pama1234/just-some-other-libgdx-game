package pama1234.gdx.game.state.state0001.game.metainfo.info0001.center;

import pama1234.gdx.game.state.state0001.game.metainfo.MetaInfoUtil.MetaItemCenter;
import pama1234.gdx.game.state.state0001.game.world.world0001.World0001;
import pama1234.gdx.game.state.state0001.game.metainfo.MetaItem;

public class MetaItemCenter0001 extends MetaItemCenter{
  public World0001 pw;
  // public MetaItem inventoryConfig;
  public MetaItem dirt,stone,
    log,branch,leaf,workbench,
    stonePickaxe,stoneAxe,stoneChisel,stoneSword,
    sapling,torch,woodPlank,woodPlatform,furnace,door,chest,
    lightOre,lightIngot,lightPickaxe,lightAxe,lightChisel,lightSword;
  public MetaItem colorBlock,lightBlock,worldRoot;
  public MetaItemCenter0001(World0001 pw) {
    this.pw=pw;
  }
}