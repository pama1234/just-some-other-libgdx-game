package pama1234.gdx.game.life.particle.life.particle.net;

import pama1234.gdx.game.life.particle.state0004.net.GameClient;
import pama1234.gdx.game.life.particle.util.net.ClientCore0045;

public class GameClientCore extends ClientCore0045{
  public GameClient p;

  {
    // receiveThreadName="GameClientCore";
  }

  public GameClientCore(GameClient p) {
    this.p=p;
  }

  @Override
  public UtilClientHandler<GameClientCore> createHandler() {
    return new GameClientHandler(p,this);
  }

  // @Override
  // public void clientStopped() {
  //   p.p.stateCenter.netGameMenu.message("无法连接服务端");
  //   if(p.p.state==p) p.p.stateBack();
  // }
}