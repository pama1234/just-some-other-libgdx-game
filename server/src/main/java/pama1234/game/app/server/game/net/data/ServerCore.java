package pama1234.game.app.server.game.net.data;

import pama1234.game.app.server.game.ServerPlayerCenter3D;
import pama1234.game.app.server.game.net.SocketData;
import pama1234.game.app.server.game.net.io.ServerRead;
import pama1234.game.app.server.game.net.io.ServerWrite;
import pama1234.game.app.server.game.particle.CellGroup3D;
import pama1234.util.wrapper.Center;

public class ServerCore{
  public Center<SocketData> socketCenter;
  public Center<ServerRead> serverReadPool;
  public Center<ServerWrite> serverWritePool;
  //---
  public CellGroup3D group;
  public ServerPlayerCenter3D playerCenter;
  public ServerCore(Center<SocketData> socketCenter,Center<ServerRead> serverReadPool,Center<ServerWrite> serverWritePool,CellGroup3D group,ServerPlayerCenter3D playerCenter) {
    this.socketCenter=socketCenter;
    this.serverReadPool=serverReadPool;
    this.serverWritePool=serverWritePool;
    this.group=group;
    this.playerCenter=playerCenter;
  }
}
