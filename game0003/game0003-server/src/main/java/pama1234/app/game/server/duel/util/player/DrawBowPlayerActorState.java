package pama1234.app.game.server.duel.util.player;

import pama1234.app.game.server.duel.util.input.AbstractInputDevice;

public abstract class DrawBowPlayerActorState extends PlayerActorState{
  public PlayerActorState moveState;
  @Override
  public void act(ServerPlayerActor parentActor) {
    final AbstractInputDevice input=parentActor.engine.inputDevice;
    aim(parentActor,input);
    parentActor.addVelocity(0.25f*input.horizontalMove,0.25f*input.verticalMove);
    if(triggerPulled(parentActor)) fire(parentActor);
    if(!buttonPressed(input)) {
      parentActor.state=moveState.entryState(parentActor);
    }
  }
  public abstract void aim(ServerPlayerActor parentActor,AbstractInputDevice input);
  public abstract void fire(ServerPlayerActor parentActor);
  public abstract boolean buttonPressed(AbstractInputDevice input);
  public abstract boolean triggerPulled(ServerPlayerActor parentActor);
}