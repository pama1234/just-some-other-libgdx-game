package pama1234.app.game.server.duel.util.player;

import pama1234.app.game.server.duel.DuelServer;
import pama1234.app.game.server.duel.util.Const;
import pama1234.app.game.server.duel.util.input.AbstractInputDevice;
import pama1234.math.UtilMath;

public class ServerDoTeleportPlayerActorState extends DrawBowPlayerActorState{
  public DuelServer duelSever;
  public final float unitAngleSpeed=0.1f*UtilMath.PI2/Const.IDEAL_FRAME_RATE;
  public final int chargeRequiredFrameCount=UtilMath.floor(0.5f*Const.IDEAL_FRAME_RATE);
  public final float ringSize=80;
  public final float ringStrokeWeight=8;

  public float desX,desY;
  public ServerDoTeleportPlayerActorState(DuelServer duelSever) {
    this.duelSever=duelSever;
  }
  @Override
  public PlayerActorState entryState(ServerPlayerActor parentActor) {
    parentActor.teleportChargedFrameCount=0;
    desX=parentActor.pos.x;
    desY=parentActor.pos.y;
    return this;
  }
  @Override
  public void act(ServerPlayerActor parentActor) {
    final AbstractInputDevice input=parentActor.engine.inputDevice;
    aim(parentActor,input);
    parentActor.addVelocity(input.horizontalMove,input.verticalMove);
    if(triggerPulled(parentActor)) fire(parentActor);
    if(!buttonPressed(input)) parentActor.state=moveState.entryState(parentActor);
  }
  @Override
  public void aim(ServerPlayerActor parentActor,AbstractInputDevice input) {
    // parentActor.aimAngle+=input.horizontalMove*unitAngleSpeed;
  }
  @Override
  public void fire(ServerPlayerActor parentActor) {}
  @Override
  public void displayEffect(ServerPlayerActor parentActor) {}
  @Override
  public void update(ServerPlayerActor parentActor) {
    parentActor.teleportChargedFrameCount++;
  }
  @Override
  public boolean isDrawingLongBow() {
    return true;
  }
  @Override
  public boolean hasCompletedTeleportCharge(ServerPlayerActor parentActor) {
    return parentActor.teleportChargedFrameCount>=chargeRequiredFrameCount;
  }
  @Override
  public boolean buttonPressed(AbstractInputDevice input) {
    return input.teleportButtonPressed;
  }
  @Override
  public boolean triggerPulled(ServerPlayerActor parentActor) {
    return !buttonPressed(parentActor.engine.inputDevice)&&hasCompletedTeleportCharge(parentActor);
  }
}
