package pama1234.gdx.game.sandbox.platformer.world.world0001;

import pama1234.gdx.game.sandbox.platformer.metainfo.MetaBlock;
import pama1234.gdx.game.sandbox.platformer.metainfo.MetaWorld;
import pama1234.gdx.game.sandbox.platformer.metainfo.info0001.center.MetaBlockCenter0001;
import pama1234.gdx.game.sandbox.platformer.metainfo.info0001.center.MetaCreatureCenter0001;
import pama1234.gdx.game.sandbox.platformer.metainfo.info0001.center.MetaItemCenter0001;
import pama1234.gdx.game.sandbox.platformer.metainfo.info0001.center.MetaWorldCenter0001;
import pama1234.gdx.game.sandbox.platformer.world.WorldBase2D;

public abstract class WorldType0001Base<W extends WorldBase2D<?>>extends MetaWorld<W,MetaBlockCenter0001<?>,MetaItemCenter0001<?>,MetaCreatureCenter0001<?>>{
  public WorldType0001Base(MetaWorldCenter0001 pc,String name,int id) {
    super(pc,name,id);

    metaBlocks=WorldMetaInfoUtil.loadBlockC(this);
    metaItems=WorldMetaInfoUtil.loadItemC(this);
    for(MetaBlock<?,?> e:metaBlocks.list) e.initItemDrop();
    metaEntitys=WorldMetaInfoUtil.loadCreatureC(this);
  }
}