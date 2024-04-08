package pama1234.gdx.game.element.duel.util.player;

import pama1234.gdx.game.element.duel.server.util.player.ServerHumanPlayerEngine;
import pama1234.gdx.game.element.duel.util.input.ClientInputData;

public class ClientHumanPlayerEngine extends ServerHumanPlayerEngine{
  public final ClientInputData currentInput;
  public ClientHumanPlayerEngine(ClientInputData keyInput) {
    super(keyInput);
    currentInput=keyInput;
  }
}