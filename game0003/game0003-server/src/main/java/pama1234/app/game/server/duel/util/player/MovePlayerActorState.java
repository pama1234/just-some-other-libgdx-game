package pama1234.app.game.server.duel.util.player;

import pama1234.app.game.server.duel.util.input.AbstractInputDevice;

public final class MovePlayerActorState extends PlayerActorState{
  public PlayerActorState drawShortbowState,drawLongbowState;
  @Override
  public void act(ServerPlayerActor parentActor) {
    final AbstractInputDevice input=parentActor.engine.inputDevice;
    parentActor.addVelocity(input.horizontalMove,input.verticalMove);
    if(input.shotButtonPressed) {
      parentActor.state=drawShortbowState.entryState(parentActor);
      parentActor.aimAngle=getEnemyPlayerActorAngle(parentActor);
      return;
    }
    if(input.longShotButtonPressed) {
      parentActor.state=drawLongbowState.entryState(parentActor);
      parentActor.aimAngle=getEnemyPlayerActorAngle(parentActor);
      return;
    }
  }
  @Override
  public void displayEffect(ServerPlayerActor parentActor) {}
  @Override
  public PlayerActorState entryState(ServerPlayerActor parentActor) {
    return this;
  }
}