package pama1234.gdx.game.duel.util.player;

import pama1234.gdx.game.duel.util.actor.PlayerActor;
import pama1234.gdx.game.duel.util.input.InputData;

public class HumanPlayerEngine extends PlayerEngine{
  public final InputData currentInput;
  public HumanPlayerEngine(InputData keyInput) {
    currentInput=keyInput;
  }
  @Override
  public void run(PlayerActor player) {
    final int intUp=currentInput.isUpPressed?-1:0;
    final int intDown=currentInput.isDownPressed?1:0;
    final int intLeft=currentInput.isLeftPressed?-1:0;
    final int intRight=currentInput.isRightPressed?1:0;
    inputDevice.operateMoveButton(intLeft+intRight,intUp+intDown);
    inputDevice.operateShotButton(currentInput.isZPressed);
    inputDevice.operateLongShotButton(currentInput.isXPressed);
  }
}