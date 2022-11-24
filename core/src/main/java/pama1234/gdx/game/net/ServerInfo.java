package pama1234.gdx.game.net;

public class ServerInfo{
  public String ip;
  public int port;
  public ServerInfo(String ip,int port) {
    this.ip=ip;
    this.port=port;
  }
  @Override
  public String toString() {
    return ip+":"+port;
  }
}
