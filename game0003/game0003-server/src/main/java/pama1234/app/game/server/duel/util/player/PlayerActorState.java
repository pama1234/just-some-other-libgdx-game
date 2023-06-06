package pama1234.app.game.server.duel.util.player;

import pama1234.app.game.server.duel.util.actor.AbstractPlayerActor;
import pama1234.math.UtilMath;

public abstract class PlayerActorState{
  public abstract void act(ServerPlayerActor parentActor);
  public abstract void displayEffect(ServerPlayerActor parentActor);
  public abstract PlayerActorState entryState(ServerPlayerActor parentActor);
  public float getEnemyPlayerActorAngle(ServerPlayerActor parentActor) {
    final AbstractPlayerActor enemyPlayer=parentActor.group.enemyGroup.player;
    return UtilMath.atan2(enemyPlayer.yPosition-parentActor.yPosition,enemyPlayer.xPosition-parentActor.xPosition);
  }
  public boolean isDamaged() {
    return false;
  }
  public boolean isDrawingLongBow() {
    return false;
  }
  public boolean hasCompletedLongBowCharge(ServerPlayerActor parentActor) {
    return false;
  }
}