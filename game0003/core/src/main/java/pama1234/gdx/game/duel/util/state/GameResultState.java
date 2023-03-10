package pama1234.processing.game.duel.util.state;

import pama1234.processing.game.duel.Duel;
import pama1234.processing.game.duel.GameSystem;
import processing.core.PApplet;

public final class GameResultState extends GameSystemState{
  public final String resultMessage;
  public final int durationFrameCount=PApplet.parseInt(Duel.IDEAL_FRAME_RATE);
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
    system.myGroup.displayPlayer();
    system.otherGroup.displayPlayer();
    system.commonParticleSet.display();
  }
  @Override
  public void displayMessage(GameSystem system) {
    if(system.demoPlay) return;
    duel.fill(0.0f);
    duel.text(resultMessage,0.0f,0.0f);
    if(properFrameCount>durationFrameCount) {
      duel.pushStyle();
      duel.textFont(duel.smallFont,duel.smallFontSize);
      duel.text("Press X key to reset.",0.0f,80.0f);
      duel.popStyle();
    }
  }
  @Override
  public void checkStateTransition(GameSystem system) {
    if(system.demoPlay) {
      if(properFrameCount>durationFrameCount*3) {
        duel.newGame(true,system.showsInstructionWindow);
      }
    }else {
      if(properFrameCount>durationFrameCount&&duel.currentKeyInput.isXPressed) {
        duel.newGame(true,true); // back to demoplay with instruction window
      }
    }
  }
}