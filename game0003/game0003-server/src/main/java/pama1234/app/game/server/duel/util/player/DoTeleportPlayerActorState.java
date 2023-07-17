package pama1234.app.game.server.duel.util.player;

import pama1234.app.game.server.duel.DuelServer;
import pama1234.app.game.server.duel.util.Const;
import pama1234.app.game.server.duel.util.input.AbstractInputDevice;
import pama1234.math.UtilMath;

public class DoTeleportPlayerActorState extends DrawBowPlayerActorState{
  public DuelServer duelSever;
  public final float unitAngleSpeed=0.1f*UtilMath.PI2/Const.IDEAL_FRAME_RATE;
  public final int chargeRequiredFrameCount=UtilMath.floor(0.5f*Const.IDEAL_FRAME_RATE);
  public final float ringSize=80;
  public final float ringStrokeWeight=8;
  public DoTeleportPlayerActorState(DuelServer duelSever) {
    this.duelSever=duelSever;
  }
  @Override
  public PlayerActorState entryState(ServerPlayerActor parentActor) {
    parentActor.chargedFrameCount=0;
    return this;
  }
  @Override
  public void aim(ServerPlayerActor parentActor,AbstractInputDevice input) {
    parentActor.aimAngle+=input.horizontalMove*unitAngleSpeed;
  }
  @Override
  public void fire(ServerPlayerActor parentActor) {}
  @Override
  public void displayEffect(ServerPlayerActor parentActor) {}
  @Override
  public void update(ServerPlayerActor parentActor) {
    parentActor.chargedFrameCount++;
  }
  @Override
  public boolean isDrawingLongBow() {
    return true;
  }
  @Override
  public boolean hasCompletedLongBowCharge(ServerPlayerActor parentActor) {
    return parentActor.chargedFrameCount>=chargeRequiredFrameCount;
  }
  @Override
  public boolean buttonPressed(AbstractInputDevice input) {
    return input.longShotButtonPressed;
  }
  @Override
  public boolean triggerPulled(ServerPlayerActor parentActor) {
    return !buttonPressed(parentActor.engine.inputDevice)&&hasCompletedLongBowCharge(parentActor);
  }
}
