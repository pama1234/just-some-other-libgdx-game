package pama1234.gdx.game.app;

import java.util.LinkedList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net.Protocol;
import com.badlogic.gdx.net.ServerSocket;
import com.badlogic.gdx.net.ServerSocketHints;
import com.badlogic.gdx.net.SocketHints;

import pama1234.gdx.game.app.server.with3d.particle.CellGroup3D;
import pama1234.gdx.game.app.server.with3d.particle.CellGroupGenerator3D;
import pama1234.gdx.game.net.ServerInfo;
import pama1234.gdx.game.net.SocketData;
import pama1234.gdx.game.net.server.ServerDataReadThread;
import pama1234.gdx.game.net.server.ServerDataWriteThread;
import pama1234.gdx.game.net.server.ServerStateReadThread;
import pama1234.gdx.game.net.server.ServerStateWriteThread;
import pama1234.gdx.game.util.ClientPlayerCenter3D;
import pama1234.gdx.util.app.UtilScreen3D;
import pama1234.gdx.util.wrapper.Center;

public class Screen0007 extends UtilScreen3D{
  public ServerInfo dataServerInfo,stateServerInfo;
  //---
  public ServerSocket serverDataSocket,serverStateSocket;
  public Center<SocketData> dataSocketCenter,stateSocketCenter;
  public Thread acceptDataThread,acceptStateThread;
  public LinkedList<Thread> serverReadT,serverWriteT;
  //---
  public CellGroup3D group;
  public boolean doUpdate;
  public Thread updateCell;
  //---
  public ClientPlayerCenter3D playerCenter;
  @Override
  public void setup() {
    dataServerInfo=new ServerInfo("192.168.2.105",12347);
    stateServerInfo=new ServerInfo("192.168.2.105",12346);
    // serverInfo=new ServerInfo("127.0.0.1",12347);
    //---
    CellGroupGenerator3D gen=new CellGroupGenerator3D(0,0);
    // group=gen.randomGenerate();
    group=gen.GenerateFromMiniCore();
    //---
    playerCenter=new ClientPlayerCenter3D(this);
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
    serverDataSocket=Gdx.net.newServerSocket(Protocol.TCP,dataServerInfo.addr,dataServerInfo.port,tssh);
    serverStateSocket=Gdx.net.newServerSocket(Protocol.TCP,stateServerInfo.addr,stateServerInfo.port,tssh);
    dataSocketCenter=new Center<>();
    // sleep(10000);
    acceptStateThread=new Thread(()-> {
      while(!stop) {
        // synchronized(centerSocket.add) {
        SocketData tsd=new SocketData(serverStateSocket.accept(tsh));
        dataSocketCenter.add.add(tsd);
        //---
        ServerStateWriteThread tswt=new ServerStateWriteThread(Screen0007.this,tsd);
        tswt.start();
        serverWriteT.add(tswt);
        //---
        ServerStateReadThread tsrt=new ServerStateReadThread(Screen0007.this,tsd);
        tsrt.start();
        serverReadT.add(tsrt);
        // }
      }
    });
    //---
    acceptDataThread=new Thread(()-> {
      while(!stop) {
        // synchronized(centerSocket.add) {
        SocketData tsd=new SocketData(serverDataSocket.accept(tsh));
        dataSocketCenter.add.add(tsd);
        //---
        ServerDataWriteThread tswt=new ServerDataWriteThread(Screen0007.this,null,tsd);
        tswt.start();
        serverWriteT.add(tswt);
        //---
        ServerDataReadThread tsrt=new ServerDataReadThread(Screen0007.this,null,tsd);
        tsrt.start();
        serverReadT.add(tsrt);
        // }
      }
    });
    acceptDataThread.start();
    //---
    updateCell=new Thread() {
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
  public void update() {}
  @Override
  public void display() {}
  @Override
  public void frameResized() {}
  @Override
  public void dispose() {
    super.dispose();
    updateCell.interrupt();
    if((!updateCell.isInterrupted())||(updateCell.isAlive())) updateCell.stop();
    serverDataSocket.dispose();
    dataSocketCenter.refresh();
    for(SocketData i:dataSocketCenter.list) i.s.dispose();
  }
}