package pama1234.app.game.server.duel.util.player;

import pama1234.app.game.server.duel.util.input.AbstractInputDevice;
import pama1234.app.game.server.duel.util.input.InputDevice;

public abstract class PlayerEngine{
  public final AbstractInputDevice inputDevice;
  public PlayerEngine() {
    inputDevice=new InputDevice();
  }
  public abstract void run(ServerPlayerActor player);
  public abstract void setScore(int scoreType,float score);
  public abstract float getScore(int index);
}