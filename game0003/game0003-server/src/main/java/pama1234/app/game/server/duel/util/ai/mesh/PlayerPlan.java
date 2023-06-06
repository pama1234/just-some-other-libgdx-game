package pama1234.app.game.server.duel.util.ai.mesh;

import pama1234.app.game.server.duel.util.input.AbstractInputDevice;
import pama1234.app.game.server.duel.util.player.ServerPlayerActor;

public abstract class PlayerPlan{
  public abstract void execute(ServerPlayerActor player,AbstractInputDevice input);
  public abstract PlayerPlan nextPlan(ServerPlayerActor player);
}