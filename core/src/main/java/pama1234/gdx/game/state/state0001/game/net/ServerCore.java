package pama1234.gdx.game.state.state0001.game.net;

import pama1234.game.app.server.server0002.net.SocketData;
import pama1234.gdx.game.state.state0001.Game;
import pama1234.gdx.game.state.state0001.game.world.World0001;
import pama1234.util.net.ServerSocketData;
import pama1234.util.net.SocketWrapper;
import pama1234.util.wrapper.Center;

public class ServerCore{
  public boolean stop;
  public Game game;
  public World0001 world;
  //---
  public ServerSocketData serverSocketData;
  public Center<SocketData> socketCenter;
  public Center<ServerRead> serverReadPool;
  public Center<ServerWrite> serverWritePool;
  public Thread acceptThread;
  public ServerCore(Game game,World0001 world,ServerSocketData serverSocketData) {
    this.game=game;
    this.world=world;
    this.serverSocketData=serverSocketData;
    acceptThread=new Thread(()-> {
      while(!stop) {
        SocketData socketData=new SocketData(new SocketWrapper(serverSocketData.accept()));
        socketCenter.add.add(socketData);
        //---
        ServerWrite serverWrite=new ServerWrite(this);
        serverWrite.start();
        serverWritePool.add.add(serverWrite);
        //---
        ServerRead serverRead=new ServerRead(this);
        serverRead.start();
        serverReadPool.add.add(serverRead);
      }
    },"AcceptSocket");
  }
  public void start() {
    acceptThread.start();
  }
  public void stop() {
    stop=true;
    serverSocketData.close();
    acceptThread.interrupt();
  }
  public static class ServerWrite extends Thread{
    ServerCore p;
    public ServerWrite(ServerCore p) {
      this.p=p;
    }
  }
  public static class ServerRead extends Thread{
    ServerCore p;
    public ServerRead(ServerCore p) {
      this.p=p;
    }
  }
}