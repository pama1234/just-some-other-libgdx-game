package pama1234.processing.game.duel.util.state;

import pama1234.processing.game.duel.Duel;
import pama1234.processing.game.duel.GameSystem;

public abstract class GameSystemState{
  public final Duel duel;
  public GameSystemState(Duel duel) {
    this.duel=duel;
  }
  public int properFrameCount;
  public void update(GameSystem system) {
    properFrameCount++;
    updateSystem(system);
  }
  public void display(GameSystem system) {
    displaySystem(system);
    this.duel.translate(Duel.INTERNAL_CANVAS_SIDE_LENGTH*0.5f,Duel.INTERNAL_CANVAS_SIDE_LENGTH*0.5f);
    displayMessage(system);
    checkStateTransition(system);
  }
  public abstract void updateSystem(GameSystem system);
  public abstract void displaySystem(GameSystem system);
  public abstract void displayMessage(GameSystem system);
  public abstract void checkStateTransition(GameSystem system);
}