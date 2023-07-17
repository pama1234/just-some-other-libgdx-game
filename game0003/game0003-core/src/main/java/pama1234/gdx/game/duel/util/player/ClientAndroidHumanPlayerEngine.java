package pama1234.gdx.game.duel.util.player;

import pama1234.app.game.server.duel.util.player.ServerPlayerActor;
import pama1234.gdx.game.duel.util.input.ClientInputData;

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