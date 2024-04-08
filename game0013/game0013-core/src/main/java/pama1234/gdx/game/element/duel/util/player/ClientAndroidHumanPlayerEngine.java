package pama1234.gdx.game.element.duel.util.player;

import pama1234.gdx.game.element.duel.server.util.player.ServerPlayerActor;
import pama1234.gdx.game.element.duel.util.input.ClientInputData;

public class ClientAndroidHumanPlayerEngine extends ClientHumanPlayerEngine{
  public ClientAndroidHumanPlayerEngine(ClientInputData keyInput) {
    super(keyInput);
  }
  @Override
  public void run(ServerPlayerActor player) {
    inputDevice.operateMove(currentInput.dx,currentInput.dy);
    inputDevice.operateShotButton(currentInput.isZPressed);
    inputDevice.operateLongShotButton(currentInput.isXPressed);
    inputDevice.operateTeleportButton(currentInput.isCPressed);
  }
}