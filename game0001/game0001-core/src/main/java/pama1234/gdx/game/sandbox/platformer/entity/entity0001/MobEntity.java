package pama1234.gdx.game.sandbox.platformer.entity.entity0001;

import pama1234.gdx.game.app.Screen0011;
import pama1234.gdx.game.sandbox.platformer.entity.TextureLivingEntity;
import pama1234.gdx.game.sandbox.platformer.entity.util.MovementLimitBox;
import pama1234.gdx.game.sandbox.platformer.metainfo.MetaCreature;
import pama1234.gdx.game.sandbox.platformer.player.Player;
import pama1234.gdx.game.sandbox.platformer.world.world0001.World0001;

public class MobEntity extends TextureLivingEntity{
  public MovementLimitBox limitBox;
  public Player target;
  @Deprecated
  public MobEntity() {//kryo only
    super();
  }
  public MobEntity(Screen0011 p,World0001 pw,float x,float y,MetaCreature<? extends MobEntity> type) {
    super(p,pw,x,y,type);
  }
  @Override
  public void dispose() {
    type.attr.count-=1;
  }
}