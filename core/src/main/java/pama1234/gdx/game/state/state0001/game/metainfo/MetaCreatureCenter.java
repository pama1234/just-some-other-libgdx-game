package pama1234.gdx.game.state.state0001.game.metainfo;

import pama1234.gdx.game.state.state0001.game.entity.Fly.FlyType;
import pama1234.gdx.game.state.state0001.game.player.Player2D.PlayerType2D;
import pama1234.gdx.game.state.state0001.game.world.World0001;
import pama1234.util.wrapper.Center;

public class MetaCreatureCenter extends Center<MetaCreature<?>>{
  public World0001 pw;
  public PlayerType2D player;
  public FlyType fly;
  public MetaCreatureCenter(World0001 pw) {
    this.pw=pw;
  }
}
