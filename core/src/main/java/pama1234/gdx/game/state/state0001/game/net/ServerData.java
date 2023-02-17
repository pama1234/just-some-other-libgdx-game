package pama1234.gdx.game.state.state0001.game.net;

import pama1234.game.app.server.server0001.game.ServerPlayerCenter3D;
import pama1234.game.app.server.server0001.game.particle.CellGroup3D;
import pama1234.gdx.game.state.state0001.game.net.ServerCore.ServerRead;
import pama1234.gdx.game.state.state0001.game.net.ServerCore.ServerWrite;
import pama1234.util.wrapper.Center;

public class ServerData{
  public Center<SocketData> socketCenter;
  public Center<ServerRead> serverReadPool;
  public Center<ServerWrite> serverWritePool;
  //---
  public CellGroup3D group;
  public ServerPlayerCenter3D playerCenter;
  public ServerData(Center<SocketData> socketCenter,Center<ServerRead> serverReadPool,Center<ServerWrite> serverWritePool,CellGroup3D group,ServerPlayerCenter3D playerCenter) {
    this.socketCenter=socketCenter;
    this.serverReadPool=serverReadPool;
    this.serverWritePool=serverWritePool;
    this.group=group;
    this.playerCenter=playerCenter;
  }
  public void sleep(long in) {
    try {
      Thread.sleep(in);
    }catch(InterruptedException e) {
      e.printStackTrace();
      throw new RuntimeException(e);//TODO
    }
  }
}
