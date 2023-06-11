package pama1234.gdx.game.duel.util.state;

import pama1234.gdx.game.duel.Duel;
import pama1234.app.game.server.duel.util.Const;
import pama1234.gdx.game.duel.ClientGameSystem;
import pama1234.gdx.game.duel.TextUtil;
import pama1234.gdx.game.duel.TextUtil.TextWithWidth;
import pama1234.math.UtilMath;

public final class ClientGameResultState extends ClientGameSystemState{
  public final TextWithWidth resultMessage;
  public final int durationFrameCount=UtilMath.floor(Const.IDEAL_FRAME_RATE);
  //---
  public int winGroupe;
  public ClientGameResultState(Duel duel,ClientGameSystem system,int winGroupe,TextWithWidth msg) {
    super(duel,system);
    system.stateIndex=ClientGameSystem.result;
    this.winGroupe=winGroupe;
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
    duel.fullText(resultMessage.text,(duel.width-resultMessage.width*duel.pus)/2f,(duel.height-duel.pu*2f)/2f);
    if(properFrameCount>durationFrameCount) duel.fullText(
      TextUtil.used.restart.text,
      (duel.width-TextUtil.used.restart.width*duel.pus)/2f,
      (duel.height+duel.pu*1f)/2f);
  }
  @Override
  public void checkStateTransition() {
    if(system.demoPlay) {
      if(properFrameCount>durationFrameCount*3) duel.stateCenter.game.newGame(true,system.showsInstructionWindow);
    }else {
      if(properFrameCount>durationFrameCount&&
        duel.stateCenter.game.currentInput.isXPressed) duel.stateCenter.game.newGame(true,true); // back to demoplay with instruction window
    }
  }
  @Override
  public float getScore(int group) {
    return winGroupe==group?1:0;
  }
}