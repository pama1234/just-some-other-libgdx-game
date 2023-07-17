package pama1234.gdx.game.app.app0001;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net.Protocol;
import com.badlogic.gdx.net.ServerSocket;
import com.badlogic.gdx.net.ServerSocketHints;
import com.badlogic.gdx.net.SocketHints;

import pama1234.game.app.server.server0001.game.ServerPlayerCenter3D;
import pama1234.game.app.server.server0001.game.net.SocketData0001;
import pama1234.game.app.server.server0001.game.net.data.Server0001Core;
import pama1234.game.app.server.server0001.game.net.io.ServerRead;
import pama1234.game.app.server.server0001.game.net.io.ServerWrite;
import pama1234.game.app.server.server0001.game.particle.CellGroup3D;
import pama1234.game.app.server.server0001.game.particle.CellGroupGenerator3D;
import pama1234.gdx.util.app.UtilScreen3D;
import pama1234.gdx.util.net.SocketWrapperGDX;
import pama1234.util.net.NetAddressInfo;
import pama1234.util.wrapper.Center;

/**
 * 服务器调试libgdx窗口 未维护
 */
@Deprecated
public class Screen0004 extends UtilScreen3D{
  // public ServerInfo dataServerInfo,stateServerInfo;
  public NetAddressInfo dataServerInfo;
  //---
  public ServerSocket serverSocket;
  public Center<SocketData0001> socketCenter;
  public Thread acceptSocket;
  public Center<ServerRead> serverReadPool;
  public Center<ServerWrite> serverWritePool;
  public Server0001Core serverCore;
  //---
  public CellGroup3D group;
  public boolean doUpdate=true;
  public Thread updateCell;
  //---
  public ServerPlayerCenter3D playerCenter;
  @Override
  public void setup() {
    dataServerInfo=new NetAddressInfo("192.168.2.105",12347);
    // stateServerInfo=new ServerInfo("192.168.2.105",12346);
    // serverInfo=new ServerInfo("127.0.0.1",12347);
    //---
    CellGroupGenerator3D gen=new CellGroupGenerator3D(0,0);
    // group=gen.randomGenerate();
    group=gen.generateFromMiniCore();
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
    serverCore=new Server0001Core(socketCenter,serverReadPool,serverWritePool,group,playerCenter);
    acceptSocket=new Thread(()-> {
      while(!stop) {
        // synchronized(centerSocket.add) {
        SocketData0001 socketData=new SocketData0001(new SocketWrapperGDX(serverSocket.accept(tsh)));
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
    for(SocketData0001 i:socketCenter.list) {
      if(i.stop) {
        socketCenter.remove.add(i);
        playerCenter.remove(i.name());
        System.out.println("Disconnect "+i.name());
        i.dispose();
      }
    }
    socketCenter.refresh();
  }
  @Override
  public void displayWithCam() {}
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
    for(SocketData0001 i:socketCenter.list) i.dispose();
    for(ServerRead i:serverReadPool.list) i.dispose();
    for(ServerWrite i:serverWritePool.list) i.dispose();
  }
}