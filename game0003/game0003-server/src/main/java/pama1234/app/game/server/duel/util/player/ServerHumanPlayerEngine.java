package pama1234.app.game.server.duel.util.player;

import pama1234.app.game.server.duel.util.input.ServerInputData;

public class ServerHumanPlayerEngine extends PlayerEngine{
  public final ServerInputData currentInput;
  public ServerHumanPlayerEngine(ServerInputData keyInput) {
    currentInput=keyInput;
  }
  @Override
  public void run(ServerPlayerActor player) {
    final int intUp=currentInput.isUpPressed?-1:0;
    final int intDown=currentInput.isDownPressed?1:0;
    final int intLeft=currentInput.isLeftPressed?-1:0;
    final int intRight=currentInput.isRightPressed?1:0;
    inputDevice.operateMoveButton(intLeft+intRight,intUp+intDown);
    inputDevice.operateShotButton(currentInput.isZPressed);
    inputDevice.operateLongShotButton(currentInput.isXPressed);
  }
  @Override
  public void setScore(int scoreType,float score) {
    //TODO nop
  }
  @Override
  public float getScore(int index) {
    return 0; //TODO nop
  }
}
