package pama1234.gdx.game.duel.util.ai.mech;

import pama1234.gdx.game.duel.util.actor.PlayerActor;
import pama1234.gdx.game.duel.util.input.AbstractInputDevice;

public abstract class PlayerPlan{
  public abstract void execute(PlayerActor player,AbstractInputDevice input);
  public abstract PlayerPlan nextPlan(PlayerActor player);
}