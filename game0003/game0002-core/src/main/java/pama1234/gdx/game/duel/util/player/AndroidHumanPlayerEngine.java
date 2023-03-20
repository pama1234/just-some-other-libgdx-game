package pama1234.gdx.game.duel.util.player;

import pama1234.gdx.game.duel.util.actor.PlayerActor;
import pama1234.gdx.game.duel.util.input.InputData;

public class AndroidHumanPlayerEngine extends HumanPlayerEngine{
  public AndroidHumanPlayerEngine(InputData _keyInput) {
    super(_keyInput);
  }
  @Override
  public void run(PlayerActor player) {
    controllingInputDevice.operateMove(currentInput.dx,currentInput.dy);
    controllingInputDevice.operateShotButton(currentInput.isZPressed);
    controllingInputDevice.operateLongShotButton(currentInput.isXPressed);
  }
}