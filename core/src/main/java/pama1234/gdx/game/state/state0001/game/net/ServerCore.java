package pama1234.gdx.game.state.state0001.game.net;

import pama1234.gdx.game.state.state0001.Game;
import pama1234.gdx.game.state.state0001.game.net.ServerIO.ServerRead;
import pama1234.gdx.game.state.state0001.game.net.ServerIO.ServerWrite;
import pama1234.gdx.game.state.state0001.game.world.World0001;
import pama1234.util.net.ServerSocketData;
import pama1234.util.net.SocketWrapper;
import pama1234.util.wrapper.Center;

public class ServerCore{
  public Game pg;
  public World0001 pw;
  //---
  public ServerSocketData serverSocket;
  public Center<SocketData> socketCenter;
  public Center<ServerRead> serverReadPool;
  public Center<ServerWrite> serverWritePool;
  public ServerData serverData;
  public Thread acceptSocket;
  public ServerCore(Game pg,World0001 pw) {
    serverSocket=new ServerSocketData(pg.addrInfo);
    socketCenter=new Center<>();
    serverReadPool=new Center<>();
    serverWritePool=new Center<>();
    serverData=new ServerData(socketCenter,serverReadPool,serverWritePool,pg,pw);
    acceptSocket=new Thread(()-> {
      while(!pg.p.stop) {
        // synchronized(centerSocket.add) {
        SocketData socketData=new SocketData(new SocketWrapper(serverSocket.accept()));
        // System.out.println(socketData.s.getRemoteAddress());
        socketCenter.add.add(socketData);
        //---
        ServerWrite serverWrite=new ServerWrite(serverData,socketData);
        serverWrite.start();
        serverWritePool.add.add(serverWrite);
        //---
        ServerRead serverRead=new ServerRead(serverData,socketData);
        serverRead.start();
        serverReadPool.add.add(serverRead);
        // }
      }
    },"AcceptSocket");
  }
  public void start() {
    acceptSocket.start();
  }
}