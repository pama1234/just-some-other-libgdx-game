package pama1234.gdx.game.element.duel.server.util.player;

import pama1234.gdx.game.element.duel.server.util.input.ServerInputData;

public class ServerAndroidHumanPlayerEngine extends ServerHumanPlayerEngine{
  public ServerAndroidHumanPlayerEngine(ServerInputData keyInput) {
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
