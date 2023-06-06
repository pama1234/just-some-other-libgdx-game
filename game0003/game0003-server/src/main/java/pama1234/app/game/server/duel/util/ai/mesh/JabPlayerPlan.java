package pama1234.app.game.server.duel.util.ai.mesh;

import pama1234.app.game.server.duel.GetRandom;
import pama1234.app.game.server.duel.util.input.AbstractInputDevice;
import pama1234.app.game.server.duel.util.player.ServerPlayerActor;

public final class JabPlayerPlan extends DefaultPlayerPlan{
  public JabPlayerPlan(GetRandom rng) {
    super(rng);
  }
  @Override
  public void execute(ServerPlayerActor player,AbstractInputDevice input) {
    super.execute(player,input);
    input.operateShotButton(true);
  }
}