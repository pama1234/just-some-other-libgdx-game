package pama1234.gdx.game.state.state0001.game.metainfo;

import pama1234.game.app.server.server0002.game.metainfo.MetaInfoBase;
import pama1234.gdx.game.state.state0001.game.metainfo.MetaInfoUtil.MetaBlockCenter;
import pama1234.gdx.game.state.state0001.game.metainfo.MetaInfoUtil.MetaCreatureCenter;
import pama1234.gdx.game.state.state0001.game.metainfo.MetaInfoUtil.MetaItemCenter;
import pama1234.gdx.game.state.state0001.game.metainfo.info0001.center.MetaWorldCenter0001;
import pama1234.gdx.game.state.state0001.game.world.WorldBase2D;

public abstract class MetaWorld<W extends WorldBase2D<?>,B extends MetaBlockCenter,I extends MetaItemCenter,E extends MetaCreatureCenter>extends MetaInfoBase{
  public MetaWorldCenter0001 pc;
  //---
  public B metaBlocks;//方块
  public I metaItems;//物品
  public E metaEntitys;//生物
  public MetaWorld(MetaWorldCenter0001 pc,String name,int id) {
    super(name,id);
    this.pc=pc;
  }
  public abstract W createWorld();
}