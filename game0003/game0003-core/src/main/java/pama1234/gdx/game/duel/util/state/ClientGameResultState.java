package pama1234.gdx.game.duel.util.state;

import pama1234.gdx.game.duel.Duel;
import pama1234.app.game.server.duel.ServerGameSystem;
import pama1234.app.game.server.duel.util.Const;
import pama1234.gdx.game.duel.ClientGameSystem;
import pama1234.gdx.game.duel.TextUtil;
import pama1234.gdx.game.duel.TextUtil.TextWithWidth;
import pama1234.gdx.game.state.state0002.Game;
import pama1234.math.UtilMath;

public final class ClientGameResultState extends ClientGameSystemState{
  public final TextWithWidth resultMessage;
  public final int durationFrameCount=UtilMath.floor(Const.IDEAL_FRAME_RATE);
  //---
  public int winGroupe;
  public ClientGameResultState(Duel duel,ClientGameSystem system,int winGroupe,TextWithWidth msg) {
    super(duel,system);
    system.stateIndex=ServerGameSystem.result;
    this.winGroupe=winGroupe;
    resultMessage=msg;
  }
  @Override
  public void updateSystem() {
    for(var group:system.groupCenter.list) group.update();
    system.commonParticleSet.update();
  }
  @Override
  public void displaySystem() {
    p.beginBlend();
    for(var group:system.groupCenter.list) group.displayPlayer();
    system.commonParticleSet.display();
    p.endBlend();
  }
  @Override
  public void displayMessage() {
    if(system.demoPlay) return;
    p.setTextColor(p.theme.text);
    p.setTextScale(p.pus);
    p.fullText(resultMessage.text,(p.width-resultMessage.width*p.pus)/2f,(p.height-p.pu*2f)/2f);
    if(properFrameCount>durationFrameCount) p.fullText(
      TextUtil.used.restart.text,
      (p.width-TextUtil.used.restart.width*p.pus)/2f,
      (p.height+p.pu*1f)/2f);
  }
  @Override
  public void checkStateTransition() {
    Game game=p.game();
    if(system.demoPlay) {
      if(properFrameCount>durationFrameCount*3) game.newGame(true,system.showsInstructionWindow);
    }else {
      if(properFrameCount>durationFrameCount&&
        game.currentInput.isXPressed) game.newGame(true,true); // back to demoplay with instruction window
    }
  }
  @Override
  public float getScore(int group) {
    return winGroupe==group?1:0;
  }
}