package pama1234.gdx.game.app;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net.Protocol;
import com.badlogic.gdx.net.ServerSocket;
import com.badlogic.gdx.net.ServerSocketHints;
import com.badlogic.gdx.net.SocketHints;

import pama1234.game.app.server.game.ServerPlayerCenter3D;
import pama1234.game.app.server.game.net.SocketData;
import pama1234.game.app.server.game.net.data.ServerCore;
import pama1234.game.app.server.game.net.io.ServerRead;
import pama1234.game.app.server.game.net.io.ServerWrite;
import pama1234.game.app.server.game.particle.CellGroup3D;
import pama1234.game.app.server.game.particle.CellGroupGenerator3D;
import pama1234.gdx.game.net.SocketWrapperGDX;
import pama1234.gdx.util.app.UtilScreen3D;
import pama1234.util.net.ServerInfo;
import pama1234.util.wrapper.Center;

/**
 * 服务器调试libgdx窗口 未维护
 */
@Deprecated
public class Screen0007 extends UtilScreen3D{
  // public ServerInfo dataServerInfo,stateServerInfo;
  public ServerInfo dataServerInfo;
  //---
  public ServerSocket serverSocket;
  public Center<SocketData> socketCenter;
  public Thread acceptSocket;
  // public Thread acceptSocket,removeSocket;//TODO
  public Center<ServerRead> serverReadPool;
  public Center<ServerWrite> serverWritePool;
  public ServerCore serverCore;
  //---
  public CellGroup3D group;
  public boolean doUpdate=true;
  public Thread updateCell;
  //---
  public ServerPlayerCenter3D playerCenter;
  @Override
  public void setup() {
    dataServerInfo=new ServerInfo("192.168.2.105",12347);
    // stateServerInfo=new ServerInfo("192.168.2.105",12346);
    // serverInfo=new ServerInfo("127.0.0.1",12347);
    //---
    CellGroupGenerator3D gen=new CellGroupGenerator3D(0,0);
    // group=gen.randomGenerate();
    group=gen.GenerateFromMiniCore();
    //---
    playerCenter=new ServerPlayerCenter3D();
    //---
    SocketHints tsh=new SocketHints();
    tsh.connectTimeout=10000;
    tsh.socketTimeout=5000;
    tsh.keepAlive=true;
    tsh.performancePrefConnectionTime=0;
    tsh.performancePrefLatency=2;
    tsh.performancePrefBandwidth=1;
    //---
    ServerSocketHints tssh=new ServerSocketHints();//TODO
    tssh.acceptTimeout=0;
    tssh.performancePrefConnectionTime=0;
    tssh.performancePrefLatency=2;
    tssh.performancePrefBandwidth=1;
    serverSocket=Gdx.net.newServerSocket(Protocol.TCP,dataServerInfo.addr,dataServerInfo.port,tssh);
    // serverStateSocket=Gdx.net.newServerSocket(Protocol.TCP,stateServerInfo.addr,stateServerInfo.port,tssh);
    socketCenter=new Center<>();
    serverReadPool=new Center<>();
    serverWritePool=new Center<>();
    serverCore=new ServerCore(socketCenter,serverReadPool,serverWritePool,group,playerCenter);
    acceptSocket=new Thread(()-> {
      while(!stop) {
        // synchronized(centerSocket.add) {
        SocketData socketData=new SocketData(new SocketWrapperGDX(serverSocket.accept(tsh)));
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
    // removeSocket=new Thread(()-> {
    //   while(!stop) {
    //     for(ServerRead i:serverReadPool.list) if(i.stop=true) serverReadPool.remove.add(i);
    //     serverReadPool.refresh();
    //     for(ServerWrite i:serverWritePool.list) if(i.stop=true) serverWritePool.remove.add(i);
    //     serverWritePool.refresh();
    //   }
    // });
    // removeSocket.start();
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
  }
  @Override
  public void update() {
    serverReadPool.refresh();
    serverWritePool.refresh();
    //---
    for(SocketData i:socketCenter.list) {
      if(i.stop) {
        socketCenter.remove.add(i);
        playerCenter.remove(i.name);
        System.out.println("Disconnect "+i.name);
        i.dispose();
      }
    }
    socketCenter.refresh();
  }
  @Override
  public void display() {}
  @Override
  public void frameResized() {}
  @Override
  public void dispose() {
    super.dispose();
    updateCell.interrupt();
    if((!updateCell.isInterrupted())||(updateCell.isAlive())) updateCell.stop();
    serverSocket.dispose();
    socketCenter.refresh();
    for(SocketData i:socketCenter.list) i.dispose();
    for(ServerRead i:serverReadPool.list) i.dispose();
    for(ServerWrite i:serverWritePool.list) i.dispose();
  }
}