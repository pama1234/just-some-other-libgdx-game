package pama1234.gdx.game.net;

public class ServerInfo{
  public String addr;
  public int port;
  public ServerInfo(String ip,int port) {
    this.addr=ip;
    this.port=port;
  }
  @Override
  public String toString() {
    return addr+":"+port;
  }
}
