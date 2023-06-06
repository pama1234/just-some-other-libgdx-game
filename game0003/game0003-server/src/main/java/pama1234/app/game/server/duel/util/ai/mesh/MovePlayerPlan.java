package pama1234.app.game.server.duel.util.ai.mesh;

import pama1234.app.game.server.duel.GetRandom;
import pama1234.app.game.server.duel.util.input.AbstractInputDevice;
import pama1234.app.game.server.duel.util.player.ServerPlayerActor;

public final class MovePlayerPlan extends DefaultPlayerPlan{
  public MovePlayerPlan(GetRandom rng) {
    super(rng);
  }
  @Override
  public void execute(ServerPlayerActor player,AbstractInputDevice input) {
    super.execute(player,input);
    input.operateShotButton(false);
  }
}