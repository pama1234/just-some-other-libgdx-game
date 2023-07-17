package pama1234.gdx.game.state.state0001.game.net;

import pama1234.gdx.game.state.state0001.Game;
import pama1234.gdx.game.state.state0001.game.world.WorldBase2D;
import pama1234.util.net.SocketData;

public class ClientCore{
  public boolean stop;
  public Game game;
  public WorldBase2D<?> world;
  //---
  public SocketData socketData;
  public ClientRead clientRead;
  public ClientWrite clientWrite;
  public Thread connectThread;
  public ClientCore(Game game,WorldBase2D<?> world,SocketData socketData) {
    this.game=game;
    this.world=world;
    this.socketData=socketData;
    connectThread=new Thread(()-> {
      while(!(stop||socketData.s.isConnected())) game.p.sleep(200);
      if(stop) return;
      (clientRead=new ClientRead(this)).start();
      (clientWrite=new ClientWrite(this)).start();
    },"ConnectThread");
  }
  public void start() {
    connectThread.start();
  }
  public void stop() {
    stop=true;
    socketData.dispose();
    connectThread.interrupt();
  }
}