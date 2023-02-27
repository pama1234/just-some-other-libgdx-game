package pama1234.gdx.game.state.state0001.game.net;

import java.io.IOException;

import pama1234.game.app.server.server0002.net.SocketData;
import pama1234.gdx.game.state.state0001.Game;
import pama1234.gdx.game.state.state0001.game.player.Player;
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
    socketCenter=new Center<>();
    serverReadPool=new Center<>();
    serverWritePool=new Center<>();
    acceptThread=new Thread(()-> {
      while(!stop) {
        SocketData socketData;
        try {
          socketData=new SocketData(new SocketWrapper(serverSocketData.accept()));
          socketCenter.add.add(socketData);
          //---
          ClientLink link=createLink(socketData);
          link.init();
          ServerWrite serverWrite=new ServerWrite(link,this);
          serverWrite.start();
          serverWritePool.add.add(serverWrite);
          //---
          ServerRead serverRead=new ServerRead(link,this);
          serverRead.start();
          serverReadPool.add.add(serverRead);
        }catch(IOException e) {
          e.printStackTrace();
          stop=true;
        }
      }
    },"AcceptSocket");
  }
  public ClientLink createLink(SocketData socketData) {
    return new ClientLink(this,socketData);
  }
  public void start() {
    acceptThread.start();
  }
  public void stop() {
    stop=true;
    serverSocketData.close();
    acceptThread.interrupt();
  }
  public static class ClientLink{
    public SocketData socketData;
    public ServerCore p;
    //---
    public Player player;
    public ClientLink(ServerCore p,SocketData socketData) {
      this.p=p;
      this.socketData=socketData;
    }
    public void init() {
      player=new Player(p.game.p,p.world,0,0,p.world.metaEntitys.player);
      player.init();
      player.innerInit();
    }
  }
}