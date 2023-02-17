package pama1234.gdx.game.state.state0001.game.net;

import pama1234.gdx.game.state.state0001.Game;
import pama1234.gdx.game.state.state0001.game.world.World0001;
import pama1234.util.net.SocketData;

public class ClientCore{
  public Game game;
  public World0001 world;
  //---
  public SocketData socketData;
  public ClientRead clientRead;
  public ClientWrite clientWrite;
  public Thread connectThread;
  public ClientCore(Game game,World0001 world,SocketData socketData) {
    this.game=game;
    this.world=world;
    this.socketData=socketData;
    connectThread=new Thread(()-> {
      while(!socketData.s.isConnected()) game.p.sleep(200);
      //TODO
      (clientRead=new ClientRead(this)).start();
      (clientWrite=new ClientWrite(this)).start();
    },"ConnectThread");
  }
  public void start() {}
  public static class ClientRead extends Thread{
    public ClientCore p;
    public ClientRead(ClientCore p) {
      this.p=p;
    }
  }
  public static class ClientWrite extends Thread{
    public ClientCore p;
    public ClientWrite(ClientCore p) {
      this.p=p;
    }
  }
}