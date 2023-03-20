package pama1234.gdx.game.state.state0001.game.metainfo.info0001.center;

import pama1234.gdx.game.state.state0001.game.metainfo.MetaInfoUtil.MetaItemCenter;
import pama1234.gdx.game.state.state0001.game.metainfo.MetaItem;
import pama1234.gdx.game.state.state0001.game.world.world0001.WorldType0001Base;

public class MetaItemCenter0001<M extends WorldType0001Base<?>>extends MetaItemCenter<M>{
  public MetaItem dirt,stone,
    log,branch,leaf,workbench,
    stonePickaxe,stoneAxe,stoneChisel,stoneSword,
    sapling,torch,woodPlank,woodPlatform,furnace,door,chest,
    lightOre,lightIngot,lightPickaxe,lightAxe,lightChisel,lightSword;
  public MetaItem colorBlock,lightBlock,worldRoot;
  public MetaItemCenter0001(M pw) {
    super(pw);
  }
}