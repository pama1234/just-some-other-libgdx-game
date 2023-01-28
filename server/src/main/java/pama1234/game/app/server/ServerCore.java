package pama1234.game.app.server;

import pama1234.game.app.server.server0001.game.net.SocketData;
import pama1234.util.UtilServer;
import pama1234.util.net.ServerInfo;
import pama1234.util.net.ServerSocketData;
import pama1234.util.wrapper.Center;

public abstract class ServerCore extends UtilServer{
  public ServerInfo serverInfo;
  public ServerSocketData serverSocket;
  public Center<SocketData> socketCenter;
}
