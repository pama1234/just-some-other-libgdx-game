package pama1234.gdx.game.duel.util.ai.nnet;

import pama1234.gdx.game.duel.Duel;
import pama1234.gdx.game.duel.util.actor.PlayerActor;
import pama1234.gdx.game.duel.util.player.PlayerEngine;

public class ComputerLifeEngine extends PlayerEngine{
  public final Duel duel;
  public ComputerLifeEngine(Duel duel) {
    this.duel=duel;
  }
  @Override
  public void run(PlayerActor player) {}
}