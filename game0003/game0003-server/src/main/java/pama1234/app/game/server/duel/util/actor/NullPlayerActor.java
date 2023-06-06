package pama1234.app.game.server.duel.util.actor;

import pama1234.app.game.server.duel.util.player.PlayerEngine;

public final class NullPlayerActor extends AbstractPlayerActor{
  public NullPlayerActor(PlayerEngine engine) {
    super(0,engine);
    xPosition=Float.NaN;
    yPosition=Float.NaN;
  }
  public NullPlayerActor(PlayerEngine engine,float xIn,float yIn,ActorGroup group) {
    super(0,engine);
    xPosition=xIn;
    yPosition=yIn;
    this.group=group;
  }
  @Override
  public void act() {}
  @Override
  public void display() {}
  @Override
  public boolean isNull() {
    return true;
  }
}