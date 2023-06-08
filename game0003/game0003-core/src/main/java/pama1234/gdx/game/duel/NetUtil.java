package pama1234.gdx.game.duel;

import pama1234.util.net.SocketData;

public class NetUtil{
  public class GameClient{
    // public DatagramSocket socket;
    public SocketData socketData;
  }
  public class ClientConfig{
    public String serverAddr;
    public int port;
  }
  public class LoginInfo{
    public String userName;
    public byte[] token;
  }
}
