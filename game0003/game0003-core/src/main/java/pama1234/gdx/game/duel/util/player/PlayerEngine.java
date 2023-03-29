package pama1234.gdx.game.duel.util.player;

import pama1234.gdx.game.duel.util.actor.PlayerActor;
import pama1234.gdx.game.duel.util.input.AbstractInputDevice;
import pama1234.gdx.game.duel.util.input.InputDevice;

public abstract class PlayerEngine{
  public final AbstractInputDevice controllingInputDevice;
  public PlayerEngine() {
    controllingInputDevice=new InputDevice();
  }
  public abstract void run(PlayerActor player);
}