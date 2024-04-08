package pama1234.app.game.server.duel;

import pama1234.util.net.NetAddressInfo;
import pama1234.util.net.ServerSocketData;

public class NetUtil{
  public static class GameServer{
    public ServerSocketData socketData;
    // public Thread acceptThread;
    public GameServer(NetAddressInfo info) {
      socketData=new ServerSocketData(info);
    }
  }
  public static class LoginInfo{
    public String userName;
    public byte[] token;
  }
}
