package pama1234.gdx.game.element.duel;

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
