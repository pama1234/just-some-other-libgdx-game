package pama1234.gdx.game.duel.util.state;

import pama1234.gdx.game.duel.Duel;
import pama1234.gdx.game.duel.GameSystem;

public abstract class GameSystemState{
  public final Duel duel;
  public GameSystem system;
  //---
  public GameSystemState(Duel duel,GameSystem system) {
    this.duel=duel;
    this.system=system;
  }
  public int properFrameCount;
  public void update() {
    checkStateTransition();
    properFrameCount++;
    updateSystem();
  }
  public void display() {
    displaySystem();
  }
  public void displayScreen() {
    displayMessage();
  }
  public abstract void updateSystem();
  public abstract void displaySystem();
  public abstract void displayMessage();
  public abstract void checkStateTransition();
}