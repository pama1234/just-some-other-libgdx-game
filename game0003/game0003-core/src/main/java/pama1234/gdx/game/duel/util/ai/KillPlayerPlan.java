package pama1234.gdx.game.duel.util.ai;

import pama1234.gdx.game.duel.Duel;
import pama1234.gdx.game.duel.util.actor.AbstractPlayerActor;
import pama1234.gdx.game.duel.util.actor.PlayerActor;
import pama1234.gdx.game.duel.util.input.AbstractInputDevice;
import pama1234.math.UtilMath;

public final class KillPlayerPlan extends PlayerPlan{
  private final Duel duel;
  public PlayerPlan movePlan,escapePlan;
  public KillPlayerPlan(Duel duel) {
    this.duel=duel;
  }
  @Override
  public void execute(PlayerActor player,AbstractInputDevice input) {
    int horizontalMove;
    final float relativeAngle=player.getAngle(player.group.enemyGroup.player)-player.aimAngle;
    if(UtilMath.abs(relativeAngle)<UtilMath.rad(1.0f)) horizontalMove=0;
    else {
      if((relativeAngle+UtilMath.PI2)%UtilMath.PI2>UtilMath.PI) horizontalMove=-1;
      else horizontalMove=+1;
    }
    input.operateMoveButton(horizontalMove,0);
    input.operateShotButton(false);
    if(player.state.hasCompletedLongBowCharge(player)&&duel.random(1.0f)<0.05f) input.operateLongShotButton(false);
    else input.operateLongShotButton(true);
  }
  @Override
  public PlayerPlan nextPlan(PlayerActor player) {
    final AbstractPlayerActor enemy=player.group.enemyGroup.player;
    if((UtilMath.abs(player.getAngle(player.group.enemyGroup.player)-player.aimAngle)>UtilMath.QUARTER_PI)||(player.getDistance(enemy)<400.0f)||!player.engine.controllingInputDevice.longShotButtonPressed) return movePlan;
    return this;
  }
}