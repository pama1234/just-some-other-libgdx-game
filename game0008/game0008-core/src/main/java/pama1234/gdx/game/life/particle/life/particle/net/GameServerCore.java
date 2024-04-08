package pama1234.gdx.game.life.particle.life.particle.net;

import pama1234.gdx.game.life.particle.state0004.net.GameServer;
import pama1234.gdx.game.life.particle.util.net.ServerCore0045;

public class GameServerCore extends ServerCore0045{
  public GameServer p;

  {
    // threadName="GameServerCore";
  }

  public GameServerCore(GameServer p) {
    this.p=p;
  }

  @Override
  public UtilServerHandler<GameServerCore> createHandler() {
    return new GameServerHandler(p,this);
  }

  // @Override
  // public void serverStopped() {
  //   p.p.stateCenter.netGameMenu.message("无法创建服务端");
  //   if(p.p.state==p) p.p.stateBack();
  // }
}