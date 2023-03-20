package pama1234.gdx.game.state.state0001.game.metainfo.info0001.center;

import pama1234.gdx.game.state.state0001.Game;
import pama1234.gdx.game.state.state0001.game.metainfo.MetaInfoUtil.MetaWorldCenter;
import pama1234.gdx.game.state.state0001.game.world.world0001.WorldType0001;
import pama1234.gdx.game.state.state0001.game.world.world0002.WorldType0002;

public class MetaWorldCenter0001 extends MetaWorldCenter{
  public Game pg;
  //---
  public WorldType0001 world0001;
  public WorldType0002 world0002;
  public MetaWorldCenter0001(Game pg) {
    this.pg=pg;
  }
}