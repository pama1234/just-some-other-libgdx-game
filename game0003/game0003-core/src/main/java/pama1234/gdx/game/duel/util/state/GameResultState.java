package pama1234.gdx.game.duel.util.state;

import pama1234.gdx.game.duel.Duel;
import pama1234.gdx.game.duel.GameSystem;
import pama1234.gdx.game.duel.TextUtil;
import pama1234.math.UtilMath;

public final class GameResultState extends GameSystemState{
  public final String resultMessage;
  public final int durationFrameCount=UtilMath.floor(Duel.IDEAL_FRAME_RATE);
  public GameResultState(Duel duel,GameSystem system,String msg) {
    super(duel,system);
    resultMessage=msg;
  }
  @Override
  public void updateSystem() {
    system.myGroup.update();
    system.otherGroup.update();
    system.commonParticleSet.update();
  }
  @Override
  public void displaySystem() {
    duel.beginBlend();
    system.myGroup.displayPlayer();
    system.otherGroup.displayPlayer();
    system.commonParticleSet.display();
    duel.endBlend();
  }
  @Override
  public void displayMessage() {
    if(system.demoPlay) return;
    duel.setTextColor(0);
    duel.setTextScale(duel.pus);
    duel.drawText(resultMessage,(duel.width-duel.textWidth(resultMessage))/2f,(duel.height-duel.pu*2f)/2f);
    if(properFrameCount>durationFrameCount) duel.drawText(
      TextUtil.used.restart,
      (duel.width-TextUtil.used.restartTextWidth*duel.pus)/2f,
      (duel.height+duel.pu*1f)/2f);
  }
  @Override
  public void checkStateTransition() {
    if(system.demoPlay) {
      if(properFrameCount>durationFrameCount*3) {
        duel.newGame(true,system.showsInstructionWindow);
      }
    }else {
      if(properFrameCount>durationFrameCount&&duel.currentInput.isXPressed) {
        duel.newGame(true,true); // back to demoplay with instruction window
      }
    }
  }
}