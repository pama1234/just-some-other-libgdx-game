package pama1234.gdx.game.state.state0001.game.net;

import pama1234.util.net.ServerSocketData;
import pama1234.util.wrapper.Center;

public class ServerCore{
  public ServerSocketData serverSocket;
  public Center<SocketData> socketCenter;
  // public Center<ServerRead> serverReadPool;
  // public Center<ServerWrite> serverWritePool;
  public ServerData serverData;
  public Thread acceptSocket;
  
}