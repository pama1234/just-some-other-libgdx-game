package pama1234.gdx.game.duel.util.player;

import pama1234.app.game.server.duel.util.player.ServerDamagedPlayerActorState;
import pama1234.app.game.server.duel.util.player.ServerPlayerActor;
import pama1234.gdx.game.duel.Duel;
import pama1234.math.UtilMath;

public final class ClientDamagedPlayerActorState extends ServerDamagedPlayerActorState{
  public final Duel p;
  public ClientDamagedPlayerActorState(Duel duel) {
    this.p=duel;
  }
  @Override
  public void displayEffect(ServerPlayerActor parentActor) {
    p.noFill();
    // duel.beginBlend();
    p.stroke(p.theme().playerDamaged,UtilMath.floor(256*(float)parentActor.damageRemainingFrameCount/durationFrameCount));
    p.circle(0,0,32);
    // duel.endBlend();
  }
}