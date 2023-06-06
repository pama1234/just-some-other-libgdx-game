package pama1234.app.game.server.duel.util.actor;

import pama1234.app.game.server.duel.util.player.PlayerActorState;
import pama1234.app.game.server.duel.util.player.PlayerEngine;

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