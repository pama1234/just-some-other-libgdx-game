package pama1234.gdx.game.duel.util.state;

import pama1234.gdx.game.duel.Duel;
import pama1234.app.game.server.duel.util.state.ServerGameSystemState;
import pama1234.gdx.game.duel.ClientGameSystem;

public abstract class ClientGameSystemState extends ServerGameSystemState{
  public final Duel p;
  public ClientGameSystem system;
  //---
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