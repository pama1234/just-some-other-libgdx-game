package pama1234.gdx.game.element.duel.server.util.state;

import pama1234.gdx.game.element.duel.server.ServerGameSystem;

public abstract class ServerGameSystemState{
  public ServerGameSystem system;
  public int properFrameCount;
  public ServerGameSystemState(ServerGameSystem system) {
    this.system=system;
  }
  public void update() {
    checkStateTransition();
    properFrameCount++;
    updateSystem();
  }
  public abstract void updateSystem();
  public abstract void checkStateTransition();
  public abstract float getScore(int group);
}
