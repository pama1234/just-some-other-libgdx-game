package pama1234.gdx.game.element.duel.util.state;


import pama1234.gdx.game.element.duel.ClientGameSystem;
import pama1234.gdx.game.element.duel.Duel;
import pama1234.gdx.game.element.duel.server.util.state.ServerGameSystemState;

/**
 * {@link ClientGameResultState}
 * </p>
 * {@link ClientPlayGameState}
 * </p>
 * {@link ClientStartGameState}
 * </p>
 */
public abstract class ClientGameSystemState extends ServerGameSystemState {
  public final Duel p;
  public ClientGameSystem system;

  public ClientGameSystemState(Duel duel,ClientGameSystem system) {
    super(system);
    this.p=duel;
    this.system=system;
  }
  public void display() {
    displaySystem();
  }
  public void displayScreen() {
    displayMessage();
  }
  public abstract void displaySystem();
  public abstract void displayMessage();
}