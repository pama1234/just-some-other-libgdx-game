package pama1234.gdx.game.duel.util.state;

import pama1234.gdx.game.duel.Duel;
import pama1234.gdx.game.duel.GameSystem;
import pama1234.math.UtilMath;

public final class GameResultState extends GameSystemState{
  public final String resultMessage;
  public final int durationFrameCount=UtilMath.floor(Duel.IDEAL_FRAME_RATE);
  public GameResultState(Duel duel,String msg) {
    super(duel);
    resultMessage=msg;
  }
  @Override
  public void updateSystem(GameSystem system) {
    system.myGroup.update();
    system.otherGroup.update();
    system.commonParticleSet.update();
  }
  @Override
  public void displaySystem(GameSystem system) {
    duel.beginBlend();
    system.myGroup.displayPlayer();
    system.otherGroup.displayPlayer();
    system.commonParticleSet.display();
    duel.endBlend();
  }
  @Override
  public void displayMessage(GameSystem system) {
    if(system.demoPlay) return;
    duel.setTextColor(0);
    duel.textScale(2);
    duel.drawText(resultMessage,-20,-8);
    if(properFrameCount>durationFrameCount) duel.drawText("按 X 键重新开始",-40,80);
  }
  @Override
  public void checkStateTransition(GameSystem system) {
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