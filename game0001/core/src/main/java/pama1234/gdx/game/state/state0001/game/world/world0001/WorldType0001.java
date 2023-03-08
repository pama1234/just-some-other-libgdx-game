package pama1234.gdx.game.state.state0001.game.world.world0001;

import pama1234.gdx.game.state.state0001.game.metainfo.MetaBlock;
import pama1234.gdx.game.state.state0001.game.metainfo.MetaWorld;
import pama1234.gdx.game.state.state0001.game.metainfo.info0001.center.MetaBlockCenter0001;
import pama1234.gdx.game.state.state0001.game.metainfo.info0001.center.MetaCreatureCenter0001;
import pama1234.gdx.game.state.state0001.game.metainfo.info0001.center.MetaItemCenter0001;
import pama1234.gdx.game.state.state0001.game.metainfo.info0001.center.MetaWorldCenter0001;

public class WorldType0001 extends MetaWorld<World0001>{
  public MetaBlockCenter0001 metaBlocks;//方块
  public MetaItemCenter0001 metaItems;//物品
  public MetaCreatureCenter0001 metaEntitys;//生物
  public WorldType0001(MetaWorldCenter0001 pc,int id) {
    super(pc,"test-world",id);
    //---
    metaBlocks=World0001Generator.createBlockC(this);
    metaItems=World0001Generator.createItemC(this);
    for(MetaBlock e:metaBlocks.list) e.initItemDrop();
    metaEntitys=World0001Generator.createCreatureC(this);
  }
  @Override
  public void init() {}
  @Override
  public World0001 createWorld() {
    return new World0001(pc.pg.p,pc.pg,this);
  }
}