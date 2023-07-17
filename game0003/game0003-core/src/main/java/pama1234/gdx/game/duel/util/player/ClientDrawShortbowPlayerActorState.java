package pama1234.gdx.game.duel.util.player;

import pama1234.app.game.server.duel.util.Const;
import pama1234.app.game.server.duel.util.player.ServerDrawShortbowPlayerActorState;
import pama1234.app.game.server.duel.util.player.ServerPlayerActor;
import pama1234.gdx.game.duel.Duel;
import pama1234.gdx.game.duel.util.arrow.ClientShortbowArrow;
import pama1234.math.UtilMath;

public final class ClientDrawShortbowPlayerActorState extends ServerDrawShortbowPlayerActorState{
  public final Duel duel;
  public final int fireIntervalFrameCount=UtilMath.floor(Const.IDEAL_FRAME_RATE*0.2f);
  public ClientDrawShortbowPlayerActorState(Duel duel) {
    super(null);
    this.duel=duel;
  }
  @Override
  public void fire(ServerPlayerActor parentActor) {
    ClientShortbowArrow newArrow=new ClientShortbowArrow(this.duel);
    final float directionAngle=parentActor.aimAngle;
    // newArrow.pos.set(parentActor.pos);
    // newArrow.pos.add(
    //   24*UtilMath.cos(directionAngle),
    //   24*UtilMath.sin(directionAngle));
    newArrow.pos.x=parentActor.pos.x+24*UtilMath.cos(directionAngle);
    newArrow.pos.y=parentActor.pos.y+24*UtilMath.sin(directionAngle);
    newArrow.rotationAngle=directionAngle;
    newArrow.vel(directionAngle,24);
    parentActor.group.addArrow(newArrow);
  }
  @Override
  public void displayEffect(ServerPlayerActor parentActor) {
    duel.strokeWeight(3);
    duel.line(0,0,70*UtilMath.cos(parentActor.aimAngle),70*UtilMath.sin(parentActor.aimAngle));
    duel.noFill();
    duel.arc(0,0,50,UtilMath.deg(parentActor.aimAngle)-22.5f,45);
  }
  @Override
  public boolean triggerPulled(ServerPlayerActor parentActor) {
    return duel.frameCount%fireIntervalFrameCount==0;
  }
}