package pama1234.game.app.server.game;

import pama1234.game.app.server.game.net.SocketData;
import pama1234.game.app.server.game.net.data.ServerCore;
import pama1234.game.app.server.game.net.io.ServerRead;
import pama1234.game.app.server.game.net.io.ServerWrite;
import pama1234.game.app.server.game.particle.CellGroup3D;
import pama1234.game.app.server.game.particle.CellGroupGenerator3D;
import pama1234.util.UtilServer;
import pama1234.util.net.ServerInfo;
import pama1234.util.net.ServerSocketData;
import pama1234.util.net.SocketWrapper;
import pama1234.util.wrapper.Center;

public class Server3D extends UtilServer{
  public CellGroup3D group;
  public boolean doUpdate=true;
  public Thread updateCell;
  //---
  public ServerPlayerCenter3D playerCenter;
  //---
  public ServerInfo dataServerInfo;
  public ServerSocketData serverSocket;
  public Center<SocketData> socketCenter;
  public Center<ServerRead> serverReadPool;
  public Center<ServerWrite> serverWritePool;
  public Thread acceptSocket;
  public ServerCore serverCore;
  //---
  // public Scanner scanner;
  public ScannerThread scannerThread;
  @Override
  public void init() {
    CellGroupGenerator3D gen=new CellGroupGenerator3D(0,0);
    // group=gen.randomGenerate();
    group=gen.GenerateFromMiniCore();
    //---
    updateCell=new Thread("UpdateCell") {
      @Override
      public void run() {
        while(!stop) {
          if(doUpdate) group.update();
          else try {
            sleep(1000);
          }catch(InterruptedException e) {
            e.printStackTrace();
          }
        }
      }
    };
    updateCell.start();
    //---
    playerCenter=new ServerPlayerCenter3D();
    //---
    dataServerInfo=new ServerInfo("192.168.2.105",12347);
    serverSocket=new ServerSocketData(dataServerInfo);
    socketCenter=new Center<>();
    serverReadPool=new Center<>();
    serverWritePool=new Center<>();
    serverCore=new ServerCore(socketCenter,serverReadPool,serverWritePool,group,playerCenter);
    acceptSocket=new Thread(()-> {
      while(!stop) {
        // synchronized(centerSocket.add) {
        SocketData socketData=new SocketData(new SocketWrapper(serverSocket.accept()));
        // System.out.println(socketData.s.getRemoteAddress());
        socketCenter.add.add(socketData);
        //---
        ServerWrite serverWrite=new ServerWrite(serverCore,socketData);
        serverWrite.start();
        serverWritePool.add.add(serverWrite);
        //---
        ServerRead serverRead=new ServerRead(serverCore,socketData);
        serverRead.start();
        serverReadPool.add.add(serverRead);
        // }
      }
    },"AcceptSocket");
    acceptSocket.start();
    //---
    scannerThread=new ScannerThread(this);
    scannerThread.start();
  }
  @Override
  public void update() {
    // group.update();
    // if(doUpdate) group.update();
    playerCenter.update();
  }
  @Override
  public void dispose() {
    group.dispose();
    playerCenter.dispose();
  }
  public static void main(String[] args) {
    new Server3D().run();
  }
}
