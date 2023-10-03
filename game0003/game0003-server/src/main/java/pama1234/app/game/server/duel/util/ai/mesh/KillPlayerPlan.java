package pama1234.app.game.server.duel.util.ai.mesh;

import pama1234.app.game.server.duel.GetRandom;
import pama1234.app.game.server.duel.util.actor.AbstractPlayerActor;
import pama1234.app.game.server.duel.util.input.AbstractInputDevice;
import pama1234.app.game.server.duel.util.player.ServerPlayerActor;
import pama1234.math.UtilMath;

public final class KillPlayerPlan extends PlayerPlan{
  public GetRandom rng;
  public PlayerPlan movePlan,escapePlan;
  public KillPlayerPlan(GetRandom rng,float level) {
    this.rng=rng;
    this.generalLevel=level;
  }
  @Override
  public void execute(ServerPlayerActor player,AbstractInputDevice input) {
    if(player.group.enemyGroup.list.getFirst().playerCenter.list.isEmpty()) return;
    int horizontalMove;
    final float relativeAngle=player.angle(player.group.enemyGroup.list.getFirst().playerCenter.list.getFirst())-player.aimAngle;
    if(UtilMath.abs(relativeAngle)<UtilMath.rad(1)) horizontalMove=0;
    else {
      if((relativeAngle+UtilMath.PI2)%UtilMath.PI2>UtilMath.PI) horizontalMove=-1;
      else horizontalMove=+1;
    }
    input.operateMoveButton(horizontalMove,0);
    input.operateShotButton(false);
    // if(player.state.hasCompletedLongBowCharge(player)&&rng.random(1)<0.05f)
    if(player.state.hasCompletedLongBowCharge(player)&&rng.random(1)<generalLevel-0.95f) input.operateLongShotButton(false);
    else input.operateLongShotButton(true);
  }
  @Override
  public PlayerPlan nextPlan(ServerPlayerActor player) {
    if(player.group.enemyGroup.list.getFirst().playerCenter.list.isEmpty()) return this;
    final AbstractPlayerActor enemy=player.group.enemyGroup.list.getFirst().playerCenter.list.getFirst();
    if((UtilMath.abs(player.angle(player.group.enemyGroup.list.getFirst().playerCenter.list.getFirst())-player.aimAngle)>UtilMath.QUARTER_PI)||(player.dist(enemy)<400)||!player.engine.inputDevice.longShotButtonPressed) return movePlan;
    return this;
  }
}