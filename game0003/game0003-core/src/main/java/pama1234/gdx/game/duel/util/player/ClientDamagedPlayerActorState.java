package pama1234.gdx.game.duel.util.player;

import pama1234.app.game.server.duel.util.player.ServerDamagedPlayerActorState;
import pama1234.app.game.server.duel.util.player.ServerPlayerActor;
import pama1234.gdx.game.duel.Duel;
import pama1234.math.UtilMath;

public final class ClientDamagedPlayerActorState extends ServerDamagedPlayerActorState{
  public final Duel duel;
  public ClientDamagedPlayerActorState(Duel duel) {
    this.duel=duel;
  }
  @Override
  public void displayEffect(ServerPlayerActor parentActor) {
    duel.noFill();
    // duel.beginBlend();
    duel.stroke(192,64,64,UtilMath.floor(256*(float)parentActor.damageRemainingFrameCount/durationFrameCount));
    duel.circle(0,0,32);
    // duel.endBlend();
  }
}