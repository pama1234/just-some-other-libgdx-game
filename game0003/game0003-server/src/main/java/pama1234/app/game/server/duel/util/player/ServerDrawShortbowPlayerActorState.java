package pama1234.app.game.server.duel.util.player;

import pama1234.app.game.server.duel.DuelServer;
import pama1234.app.game.server.duel.util.Const;
import pama1234.app.game.server.duel.util.arrow.ServerShortbowArrow;
import pama1234.app.game.server.duel.util.input.AbstractInputDevice;
import pama1234.math.UtilMath;

public class ServerDrawShortbowPlayerActorState extends DrawBowPlayerActorState{
  public DuelServer duelSever;
  public final int fireIntervalFrameCount=UtilMath.floor(Const.IDEAL_FRAME_RATE*0.2f);
  public ServerDrawShortbowPlayerActorState(DuelServer duelSever) {
    this.duelSever=duelSever;
  }
  @Override
  public void aim(ServerPlayerActor parentActor,AbstractInputDevice input) {
    parentActor.aimAngle=getEnemyPlayerActorAngle(parentActor);
  }
  @Override
  public void fire(ServerPlayerActor parentActor) {
    ServerShortbowArrow newArrow=new ServerShortbowArrow();
    final float directionAngle=parentActor.aimAngle;
    newArrow.pos.x=parentActor.pos.x+24*UtilMath.cos(directionAngle);
    newArrow.pos.y=parentActor.pos.y+24*UtilMath.sin(directionAngle);
    newArrow.rotationAngle=directionAngle;
    newArrow.vel(directionAngle,24);
    parentActor.group.addArrow(newArrow);
  }
  @Override
  public void displayEffect(ServerPlayerActor parentActor) {}
  @Override
  public void update(ServerPlayerActor parentActor) {}
  @Override
  public PlayerActorState entryState(ServerPlayerActor parentActor) {
    return this;
  }
  @Override
  public boolean buttonPressed(AbstractInputDevice input) {
    return input.shotButtonPressed;
  }
  @Override
  public boolean triggerPulled(ServerPlayerActor parentActor) {
    return duelSever.frameCount%fireIntervalFrameCount==0;
  }
}
