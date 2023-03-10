package pama1234.processing.game.duel.util.ai;

import pama1234.processing.game.duel.Duel;
import pama1234.processing.game.duel.util.actor.AbstractPlayerActor;
import pama1234.processing.game.duel.util.actor.PlayerActor;
import pama1234.processing.game.duel.util.input.AbstractInputDevice;
import processing.core.PApplet;
import processing.core.PConstants;

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
    if(PApplet.abs(relativeAngle)<PApplet.radians(1.0f)) horizontalMove=0;
    else {
      if((relativeAngle+PConstants.TWO_PI)%PConstants.TWO_PI>PConstants.PI) horizontalMove=-1;
      else horizontalMove=+1;
    }
    input.operateMoveButton(horizontalMove,0);
    input.operateShotButton(false);
    if(player.state.hasCompletedLongBowCharge(player)&&this.duel.random(1.0f)<0.05f) input.operateLongShotButton(false);
    else input.operateLongShotButton(true);
  }
  @Override
  public PlayerPlan nextPlan(PlayerActor player) {
    final AbstractPlayerActor enemy=player.group.enemyGroup.player;
    if((PApplet.abs(player.getAngle(player.group.enemyGroup.player)-player.aimAngle)>PConstants.QUARTER_PI)||(player.getDistance(enemy)<400.0f)||!player.engine.controllingInputDevice.longShotButtonPressed) return movePlan;
    return this;
  }
}