package pama1234.gdx.game.element.duel.server.util.player;

import pama1234.gdx.game.element.duel.server.util.ai.mesh.ComputerPlayerEngine;
import pama1234.gdx.game.element.duel.server.util.input.AbstractInputDevice;
import pama1234.gdx.game.element.duel.server.util.input.InputDevice;

/**
 * 子类：{@link ComputerPlayerEngine}
 */
public abstract class PlayerEngine{
  public final AbstractInputDevice inputDevice;
  public PlayerEngine() {
    inputDevice=new InputDevice();
  }
  public abstract void run(ServerPlayerActor player);
  public abstract void setScore(int scoreType,float score);
  public abstract float getScore(int index);
}