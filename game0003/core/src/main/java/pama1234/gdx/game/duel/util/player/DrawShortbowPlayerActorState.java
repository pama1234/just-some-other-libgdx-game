package pama1234.gdx.game.duel.util.player;

import pama1234.gdx.game.duel.Duel;
import pama1234.gdx.game.duel.util.actor.PlayerActor;
import pama1234.gdx.game.duel.util.actor.ShortbowArrow;
import pama1234.gdx.game.duel.util.input.AbstractInputDevice;
import pama1234.math.UtilMath;

public final class DrawShortbowPlayerActorState extends DrawBowPlayerActorState{
  private final Duel duel;
  public final int fireIntervalFrameCount=UtilMath.floor(Duel.IDEAL_FRAME_RATE*0.2f);
  public DrawShortbowPlayerActorState(Duel duel) {
    this.duel=duel;
  }
  @Override
  public void aim(PlayerActor parentActor,AbstractInputDevice input) {
    parentActor.aimAngle=getEnemyPlayerActorAngle(parentActor);
  }
  @Override
  public void fire(PlayerActor parentActor) {
    ShortbowArrow newArrow=new ShortbowArrow(this.duel);
    final float directionAngle=parentActor.aimAngle;
    newArrow.xPosition=parentActor.xPosition+24.0f*UtilMath.cos(directionAngle);
    newArrow.yPosition=parentActor.yPosition+24.0f*UtilMath.sin(directionAngle);
    newArrow.rotationAngle=directionAngle;
    newArrow.setVelocity(directionAngle,24.0f);
    parentActor.group.addArrow(newArrow);
  }
  @Override
  public void displayEffect(PlayerActor parentActor) {
    duel.strokeWeight(5);
    duel.line(0,0,70*UtilMath.cos(parentActor.aimAngle),70*UtilMath.sin(parentActor.aimAngle));
    duel.noFill();
    duel.arc(0,0,50,UtilMath.deg(parentActor.aimAngle)-22.5f,45);
  }
  @Override
  public PlayerActorState entryState(PlayerActor parentActor) {
    return this;
  }
  @Override
  public boolean buttonPressed(AbstractInputDevice input) {
    return input.shotButtonPressed;
  }
  @Override
  public boolean triggerPulled(PlayerActor parentActor) {
    return duel.frameCount%fireIntervalFrameCount==0;
  }
}