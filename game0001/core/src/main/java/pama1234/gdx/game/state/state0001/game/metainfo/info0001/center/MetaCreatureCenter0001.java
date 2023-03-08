package pama1234.gdx.game.state.state0001.game.metainfo.info0001.center;

import pama1234.gdx.game.state.state0001.game.entity.entity0001.DroppedItem.DroppedItemType;
import pama1234.gdx.game.state.state0001.game.entity.entity0001.Fly.FlyType;
import pama1234.gdx.game.state.state0001.game.metainfo.MetaInfoUtil.MetaCreatureCenter;
import pama1234.gdx.game.state.state0001.game.player.Player.PlayerType;
import pama1234.gdx.game.state.state0001.game.world.world0001.WorldType0001Base;

public class MetaCreatureCenter0001<M extends WorldType0001Base<?>>extends MetaCreatureCenter<M>{
  public PlayerType player;
  public DroppedItemType droppedItem;
  public FlyType fly;
  public MetaCreatureCenter0001(M pw) {
    super(pw);
  }
}