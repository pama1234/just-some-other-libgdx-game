package pama1234.gdx.game.duel.util.ai.mech;

import pama1234.gdx.game.duel.GetRandom;
import pama1234.gdx.game.duel.util.actor.AbstractPlayerActor;
import pama1234.gdx.game.duel.util.actor.PlayerActor;
import pama1234.gdx.game.duel.util.input.AbstractInputDevice;
import pama1234.math.UtilMath;

public final class KillPlayerPlan extends PlayerPlan{
  public GetRandom rng;
  public PlayerPlan movePlan,escapePlan;
  public KillPlayerPlan(GetRandom rng) {
    this.rng=rng;
  }
  @Override
  public void execute(PlayerActor player,AbstractInputDevice input) {
    int horizontalMove;
    final float relativeAngle=player.getAngle(player.group.enemyGroup.player)-player.aimAngle;
    if(UtilMath.abs(relativeAngle)<UtilMath.rad(1)) horizontalMove=0;
    else {
      if((relativeAngle+UtilMath.PI2)%UtilMath.PI2>UtilMath.PI) horizontalMove=-1;
      else horizontalMove=+1;
    }
    input.operateMoveButton(horizontalMove,0);
    input.operateShotButton(false);
    if(player.state.hasCompletedLongBowCharge(player)&&rng.random(1)<0.05f) input.operateLongShotButton(false);
    else input.operateLongShotButton(true);
  }
  @Override
  public PlayerPlan nextPlan(PlayerActor player) {
    final AbstractPlayerActor enemy=player.group.enemyGroup.player;
    if((UtilMath.abs(player.getAngle(player.group.enemyGroup.player)-player.aimAngle)>UtilMath.QUARTER_PI)||(player.getDistance(enemy)<400)||!player.engine.inputDevice.longShotButtonPressed) return movePlan;
    return this;
  }
}