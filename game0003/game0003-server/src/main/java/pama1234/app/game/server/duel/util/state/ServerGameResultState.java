package pama1234.app.game.server.duel.util.state;

import pama1234.app.game.server.duel.ServerGameSystem;
import pama1234.app.game.server.duel.util.Const;
import pama1234.math.UtilMath;

public class ServerGameResultState extends ServerGameSystemState{
  public final int durationFrameCount=UtilMath.floor(Const.IDEAL_FRAME_RATE);
  //---
  public int winGroupe;
  public ServerGameResultState(ServerGameSystem system,int winGroupe) {
    super(system);
    system.stateIndex=ServerGameSystem.result;
    this.winGroupe=winGroupe;
  }
  @Override
  public void updateSystem() {
    for(var group:system.groupCenter.list) group.update();
  }
  @Override
  public void checkStateTransition() {
    if(system.demoPlay) {
      if(properFrameCount>durationFrameCount*3) system.duelServer.newGame(true);
    }else {
      if(properFrameCount>durationFrameCount&&
        system.duelServer.input_a.inputData.isXPressed&&
        system.duelServer.input_b.inputData.isXPressed) system.duelServer.newGame(true); // back to demoplay with instruction window
    }
  }
  @Override
  public float getScore(int group) {
    return winGroupe==group?1:0;
  }
}
