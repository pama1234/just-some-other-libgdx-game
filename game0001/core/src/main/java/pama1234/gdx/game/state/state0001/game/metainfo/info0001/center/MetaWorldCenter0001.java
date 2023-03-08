package pama1234.gdx.game.state.state0001.game.metainfo.info0001.center;

import pama1234.gdx.game.state.state0001.Game;
import pama1234.gdx.game.state.state0001.game.metainfo.MetaInfoUtil.MetaWorldCenter;
import pama1234.gdx.game.state.state0001.game.metainfo.MetaWorld;
import pama1234.gdx.game.state.state0001.game.world.world0001.World0001;

public class MetaWorldCenter0001 extends MetaWorldCenter{
  public Game pg;
  //---
  public MetaWorld<World0001> world0001;
  public MetaWorldCenter0001(Game pg) {
    this.pg=pg;
  }
}