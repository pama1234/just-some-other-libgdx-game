package pama1234.gdx.game.duel.util.player;

import pama1234.gdx.game.duel.util.actor.PlayerActor;
import pama1234.gdx.game.duel.util.input.AbstractInputDevice;

public abstract class DrawBowPlayerActorState extends PlayerActorState{
  public PlayerActorState moveState;
  @Override
  public void act(PlayerActor parentActor) {
    final AbstractInputDevice input=parentActor.engine.inputDevice;
    aim(parentActor,input);
    parentActor.addVelocity(0.25f*input.horizontalMove,0.25f*input.verticalMove);
    if(triggerPulled(parentActor)) fire(parentActor);
    if(!buttonPressed(input)) {
      parentActor.state=moveState.entryState(parentActor);
    }
  }
  public abstract void aim(PlayerActor parentActor,AbstractInputDevice input);
  public abstract void fire(PlayerActor parentActor);
  public abstract boolean buttonPressed(AbstractInputDevice input);
  public abstract boolean triggerPulled(PlayerActor parentActor);
}