package pama1234.gdx.game.duel.util.actor;

import pama1234.gdx.game.duel.util.player.PlayerActorState;
import pama1234.gdx.game.duel.util.player.PlayerEngine;

public abstract class AbstractPlayerActor extends Actor{
  public final PlayerEngine engine;
  public PlayerActorState state;
  public AbstractPlayerActor(float collisionRadius,PlayerEngine engine) {
    super(collisionRadius);
    this.engine=engine;
  }
  public boolean isNull() {
    return false;
  }
}