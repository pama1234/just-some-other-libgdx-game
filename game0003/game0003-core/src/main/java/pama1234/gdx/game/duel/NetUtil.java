package pama1234.gdx.game.duel;

import pama1234.util.net.SocketData;
import pama1234.util.net.SocketInterface;

public class NetUtil{
  public static class GameClient{
    public SocketData socketData;
    public GameClient(SocketInterface s) {
      socketData=new SocketData(s);
    }
  }
}
