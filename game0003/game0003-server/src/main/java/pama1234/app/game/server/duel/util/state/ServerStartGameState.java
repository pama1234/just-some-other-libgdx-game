package pama1234.app.game.server.duel.util.state;

import pama1234.app.game.server.duel.DuelServer;
import pama1234.app.game.server.duel.ServerGameSystem;
import pama1234.app.game.server.duel.util.Const;
import pama1234.math.UtilMath;

public class ServerStartGameState extends ServerGameSystemState{
  DuelServer duelServer;
  public ServerStartGameState(DuelServer duelServer,ServerGameSystem system) {
    super(system);
    this.duelServer=duelServer;
    system.stateIndex=ServerGameSystem.start;
  }
  public final int frameCountPerNumber=UtilMath.floor(Const.IDEAL_FRAME_RATE);
  public final float ringSize=200;
  public final float ringStrokeWeight=5.0f;
  public int displayNumber=3;
  @Override
  public void updateSystem() {
    system.myGroup.update();
    system.otherGroup.update();
  }
  @Override
  public void checkStateTransition() {
    if(properFrameCount>=frameCountPerNumber*3) {
      // system.commonParticleSet.particleList.add(newParticle);
      // system.currentState(new ClientPlayGameState(system));
    }
  }
  @Override
  public float getScore(int group) {
    return 0;
  }
}
