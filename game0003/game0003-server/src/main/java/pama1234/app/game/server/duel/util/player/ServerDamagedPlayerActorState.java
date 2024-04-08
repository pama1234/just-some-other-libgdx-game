package pama1234.app.game.server.duel.util.player;

import pama1234.app.game.server.duel.util.Const;
import pama1234.math.UtilMath;

public class ServerDamagedPlayerActorState extends PlayerActorState{
  public final int durationFrameCount=UtilMath.floor(0.75f*Const.IDEAL_FRAME_RATE);
  public PlayerActorState moveState;
  @Override
  public void act(ServerPlayerActor parentActor) {
    parentActor.damageRemainingFrameCount--;
    if(parentActor.damageRemainingFrameCount<=0) parentActor.state=moveState.entryState(parentActor);
  }
  @Override
  public void displayEffect(ServerPlayerActor parentActor) {}
  @Override
  public PlayerActorState entryState(ServerPlayerActor parentActor) {
    parentActor.damageRemainingFrameCount=durationFrameCount;
    return this;
  }
  @Override
  public boolean isDamaged() {
    return true;
  }
  @Override
  public void update(ServerPlayerActor parentActor) {}
}
