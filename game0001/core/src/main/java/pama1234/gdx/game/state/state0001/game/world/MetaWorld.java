package pama1234.gdx.game.state.state0001.game.world;

import pama1234.game.app.server.server0002.game.metainfo.MetaInfoBase;
import pama1234.gdx.game.state.state0001.game.metainfo.MetaInfoUtil.MetaBlockCenter;
import pama1234.gdx.game.state.state0001.game.metainfo.MetaInfoUtil.MetaCreatureCenter;
import pama1234.gdx.game.state.state0001.game.metainfo.MetaInfoUtil.MetaItemCenter;

public abstract class MetaWorld extends MetaInfoBase{
  public MetaBlockCenter metaBlocks;//方块
  public MetaItemCenter metaItems;//物品
  public MetaCreatureCenter metaEntitys;//生物
  public MetaWorld(String name,int id) {
    super(name,id);
  }
}