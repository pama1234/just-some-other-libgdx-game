package pama1234.gdx.game.element.duel.server.util.ai.mesh;


import pama1234.gdx.game.element.duel.server.util.input.AbstractInputDevice;
import pama1234.gdx.game.element.duel.server.util.player.ServerPlayerActor;

public abstract class PlayerPlan{
  /**
   * Professional level
   */
  public float generalLevel;
  public abstract void execute(ServerPlayerActor player, AbstractInputDevice input);
  public abstract PlayerPlan nextPlan(ServerPlayerActor player);
}