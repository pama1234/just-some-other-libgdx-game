package pama1234.gdx.game.duel.util.player;

import pama1234.gdx.game.duel.Duel;
import pama1234.gdx.game.duel.util.actor.PlayerActor;
import pama1234.math.UtilMath;

public final class DamagedPlayerActorState extends PlayerActorState{
  private final Duel duel;
  public PlayerActorState moveState;
  public DamagedPlayerActorState(Duel duel) {
    this.duel=duel;
  }
  final int durationFrameCount=UtilMath.floor(0.75f*Duel.IDEAL_FRAME_RATE);
  @Override
  public void act(PlayerActor parentActor) {
    parentActor.damageRemainingFrameCount--;
    if(parentActor.damageRemainingFrameCount<=0) parentActor.state=moveState.entryState(parentActor);
  }
  @Override
  public void displayEffect(PlayerActor parentActor) {
    duel.noFill();
    // duel.beginBlend();
    duel.stroke(192,64,64,UtilMath.floor(256*(float)parentActor.damageRemainingFrameCount/durationFrameCount));
    duel.circle(0,0,32);
    // duel.endBlend();
  }
  @Override
  public PlayerActorState entryState(PlayerActor parentActor) {
    parentActor.damageRemainingFrameCount=durationFrameCount;
    return this;
  }
  @Override
  public boolean isDamaged() {
    return true;
  }
}