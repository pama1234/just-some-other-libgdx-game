package pama1234.gdx.game.duel.util.player;

import pama1234.gdx.game.duel.util.actor.PlayerActor;
import pama1234.gdx.game.duel.util.input.InputData;

public class AndroidHumanPlayerEngine extends HumanPlayerEngine{
  public AndroidHumanPlayerEngine(InputData _keyInput) {
    super(_keyInput);
  }
  @Override
  public void run(PlayerActor player) {
    inputDevice.operateMove(currentInput.dx,currentInput.dy);
    inputDevice.operateShotButton(currentInput.isZPressed);
    inputDevice.operateLongShotButton(currentInput.isXPressed);
  }
}