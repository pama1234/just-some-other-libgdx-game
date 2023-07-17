package pama1234.gdx.game.state.state0001.game.world.world0001;

import pama1234.gdx.game.state.state0001.game.metainfo.MetaBlock;
import pama1234.gdx.game.state.state0001.game.metainfo.MetaWorld;
import pama1234.gdx.game.state.state0001.game.metainfo.info0001.center.MetaBlockCenter0001;
import pama1234.gdx.game.state.state0001.game.metainfo.info0001.center.MetaCreatureCenter0001;
import pama1234.gdx.game.state.state0001.game.metainfo.info0001.center.MetaItemCenter0001;
import pama1234.gdx.game.state.state0001.game.metainfo.info0001.center.MetaWorldCenter0001;
import pama1234.gdx.game.state.state0001.game.world.WorldBase2D;

public abstract class WorldType0001Base<W extends WorldBase2D<?>>extends MetaWorld<W,MetaBlockCenter0001<?>,MetaItemCenter0001<?>,MetaCreatureCenter0001<?>>{
  public WorldType0001Base(MetaWorldCenter0001 pc,String name,int id) {
    super(pc,name,id);
    //---
    metaBlocks=WorldType0001BaseGenerator.createBlockC(this);
    metaItems=WorldType0001BaseGenerator.createItemC(this);
    for(MetaBlock<?,?> e:metaBlocks.list) e.initItemDrop();
    metaEntitys=WorldType0001BaseGenerator.createCreatureC(this);
  }
}