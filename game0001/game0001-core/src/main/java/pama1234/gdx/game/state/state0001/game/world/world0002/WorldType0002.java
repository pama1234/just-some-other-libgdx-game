package pama1234.gdx.game.state.state0001.game.world.world0002;

import pama1234.gdx.game.state.state0001.game.metainfo.info0001.center.MetaWorldCenter0001;
import pama1234.gdx.game.state.state0001.game.world.world0001.WorldType0001Base;

// MetaWorld<World0002,MetaBlockCenter0001<?>,MetaItemCenter0001<?>,MetaCreatureCenter0001<?>>
public class WorldType0002 extends WorldType0001Base<World0002>{
  public WorldType0002(MetaWorldCenter0001 pc,int id) {
    super(pc,"test-world-2",id);
  }
  @Override
  public void init() {}
  @Override
  public World0002 createWorld() {
    return new World0002(pc.pg.p,pc.pg,this);
  }
}