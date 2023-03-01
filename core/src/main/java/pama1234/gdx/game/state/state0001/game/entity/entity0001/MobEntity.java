package pama1234.gdx.game.state.state0001.game.entity.entity0001;

import pama1234.gdx.game.app.Screen0011;
import pama1234.gdx.game.state.state0001.game.entity.TextureLivingEntity;
import pama1234.gdx.game.state.state0001.game.entity.util.MovementLimitBox;
import pama1234.gdx.game.state.state0001.game.metainfo.MetaCreature;
import pama1234.gdx.game.state.state0001.game.player.Player;
import pama1234.gdx.game.state.state0001.game.world.World0001;

public class MobEntity extends TextureLivingEntity{
  public MovementLimitBox limitBox;
  public Player target;
  public MobEntity(Screen0011 p,World0001 pw,float x,float y,MetaCreature<? extends MobEntity> type) {
    super(p,pw,x,y,type);
  }
  @Override
  public void dispose() {
    type.count-=1;
  }
}