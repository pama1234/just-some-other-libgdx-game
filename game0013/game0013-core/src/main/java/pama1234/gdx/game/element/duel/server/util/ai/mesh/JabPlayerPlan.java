package pama1234.gdx.game.element.duel.server.util.ai.mesh;


import pama1234.gdx.game.element.duel.server.GetRandom;
import pama1234.gdx.game.element.duel.server.util.input.AbstractInputDevice;
import pama1234.gdx.game.element.duel.server.util.player.ServerPlayerActor;

public final class JabPlayerPlan extends DefaultPlayerPlan{
  public JabPlayerPlan(GetRandom rng, float level) {
    super(rng,level);
  }
  @Override
  public void execute(ServerPlayerActor player, AbstractInputDevice input) {
    super.execute(player,input);
    input.operateShotButton(true);
  }
}