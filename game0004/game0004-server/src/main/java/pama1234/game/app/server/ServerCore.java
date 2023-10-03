package pama1234.game.app.server;

import pama1234.game.app.server.server0001.game.net.SocketData0001;
import pama1234.util.UtilServer;
import pama1234.util.net.NetAddressInfo;
import pama1234.util.net.ServerSocketData;
import pama1234.util.wrapper.Center;

public abstract class ServerCore extends UtilServer{
  public boolean doUpdate=true;
  
  public NetAddressInfo serverInfo;
  public ServerSocketData serverSocket;
  public Center<SocketData0001> socketCenter;
}
