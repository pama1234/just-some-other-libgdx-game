package pama1234.gdx.game.duel.util.player;

import pama1234.app.game.server.duel.util.player.ServerHumanPlayerEngine;
import pama1234.gdx.game.duel.util.input.ClientInputData;

public class ClientHumanPlayerEngine extends ServerHumanPlayerEngine{
  public final ClientInputData currentInput;
  public ClientHumanPlayerEngine(ClientInputData keyInput) {
    super(keyInput);
    currentInput=keyInput;
  }
}