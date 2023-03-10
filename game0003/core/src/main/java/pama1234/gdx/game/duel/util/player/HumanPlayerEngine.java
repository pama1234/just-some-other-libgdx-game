package pama1234.processing.game.duel.util.player;

import pama1234.processing.game.duel.util.actor.PlayerActor;
import pama1234.processing.game.duel.util.ai.PlayerEngine;
import pama1234.processing.game.duel.util.input.KeyInput;

public final class HumanPlayerEngine extends PlayerEngine{
  public final KeyInput currentKeyInput;
  public HumanPlayerEngine(KeyInput _keyInput) {
    currentKeyInput=_keyInput;
  }
  @Override
  public void run(PlayerActor player) {
    final int intUp=currentKeyInput.isUpPressed?-1:0;
    final int intDown=currentKeyInput.isDownPressed?1:0;
    final int intLeft=currentKeyInput.isLeftPressed?-1:0;
    final int intRight=currentKeyInput.isRightPressed?1:0;
    controllingInputDevice.operateMoveButton(intLeft+intRight,intUp+intDown);
    controllingInputDevice.operateShotButton(currentKeyInput.isZPressed);
    controllingInputDevice.operateLongShotButton(currentKeyInput.isXPressed);
  }
}