package pama1234.gdx.game.state.state0001.game.world.world0002;

import pama1234.gdx.game.state.state0001.game.metainfo.MetaBlock;
import pama1234.gdx.game.state.state0001.game.metainfo.MetaWorld;
import pama1234.gdx.game.state.state0001.game.metainfo.info0001.center.MetaBlockCenter0001;
import pama1234.gdx.game.state.state0001.game.metainfo.info0001.center.MetaCreatureCenter0001;
import pama1234.gdx.game.state.state0001.game.metainfo.info0001.center.MetaItemCenter0001;
import pama1234.gdx.game.state.state0001.game.metainfo.info0001.center.MetaWorldCenter0001;
import pama1234.gdx.game.state.state0001.game.world.world0001.World0001Generator;

public class WorldType0002 extends MetaWorld<World0002,MetaBlockCenter0001,MetaItemCenter0001,MetaCreatureCenter0001>{
  public WorldType0002(MetaWorldCenter0001 pc,int id) {
    super(pc,"test-world-2",id);
    //---
    // metaBlocks=World0001Generator.createBlockC(this);
    // metaItems=World0001Generator.createItemC(this);
    // for(MetaBlock e:metaBlocks.list) e.initItemDrop();
    // metaEntitys=World0001Generator.createCreatureC(this);
  }
  @Override
  public void init() {}
  @Override
  public World0002 createWorld() {
    return new World0002(pc.pg.p,pc.pg,this);
  }
}