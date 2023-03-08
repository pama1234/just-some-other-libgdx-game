package pama1234.gdx.game.state.state0001.game.world.world0001;

import pama1234.gdx.game.state.state0001.game.metainfo.info0001.center.MetaWorldCenter0001;

public class WorldType0001 extends WorldType0001Base<World0001>{
  public WorldType0001(MetaWorldCenter0001 pc,int id) {
    super(pc,id);
  }
  @Override
  public void init() {}
  @Override
  public World0001 createWorld() {
    return new World0001(pc.pg.p,pc.pg,this);
  }
}