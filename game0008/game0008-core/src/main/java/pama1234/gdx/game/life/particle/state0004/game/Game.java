package pama1234.gdx.game.life.particle.state0004.game;

import pama1234.gdx.game.life.particle.app.Screen0045;
import pama1234.gdx.game.life.particle.life.particle.element.ParticleSandbox;
import pama1234.gdx.game.life.particle.state0004.State0004Util.StateEntity0004;
import pama1234.gdx.game.life.particle.ui.generator.CameraManager;
import pama1234.server.game.life.particle.net.message.RoomInfo;

public class Game extends StateEntity0004{
  // 实际游戏内容
  public ParticleSandbox sandbox;
  public Game(Screen0045 p) {
    super(p);
    initContainer();

    container.refreshAll();
  }
  @Override
  public void from(StateEntity0004 in) {
    // p.cam2d.minScale=1/24f;
    CameraManager.gameEnter(p);

    if(sandbox==null) createSandbox();

    super.from(in);
  }
  public void createSandbox() {
    sandbox=new ParticleSandbox(p,new RoomInfo().initDefault());
    sandbox.cellCenter.core.updateScore=true;
    container.centerCam.add.add(sandbox);
  }
  @Override
  public void to(StateEntity0004 in) {
    CameraManager.gameExit(p);

    super.to(in);
  }
}
