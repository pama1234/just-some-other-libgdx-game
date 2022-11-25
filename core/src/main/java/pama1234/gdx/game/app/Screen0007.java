package pama1234.gdx.game.app;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net.Protocol;
import com.badlogic.gdx.net.ServerSocket;
import com.badlogic.gdx.net.ServerSocketHints;
import com.badlogic.gdx.net.SocketHints;

import pama1234.gdx.game.app.server.with3d.particle.CellGroup3D;
import pama1234.gdx.game.app.server.with3d.particle.CellGroupGenerator3D;
import pama1234.gdx.game.net.ServerInfo;
import pama1234.gdx.game.net.ServerReadThread;
import pama1234.gdx.game.net.ServerWriteThread;
import pama1234.gdx.game.net.SocketData;
import pama1234.gdx.game.util.ClientPlayerCenter3D;
import pama1234.gdx.util.app.UtilScreen3D;
import pama1234.gdx.util.wrapper.Center;

public class Screen0007 extends UtilScreen3D{
  public ServerInfo serverInfo;
  //---
  public ServerSocket serverSocket;
  public Center<SocketData> socketCenter;
  public Thread acceptT,serverReadT,serverWriteT;
  //---
  public CellGroup3D group;
  public boolean doUpdate;
  public Thread updateCell;
  //---
  public ClientPlayerCenter3D playerCenter;
  @Override
  public void setup() {
    serverInfo=new ServerInfo("192.168.2.105",12347);
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
    serverSocket=Gdx.net.newServerSocket(Protocol.TCP,serverInfo.addr,serverInfo.port,tssh);
    socketCenter=new Center<>();
    // sleep(10000);
    acceptT=new Thread(()-> {
      while(!stop) {
        // synchronized(centerSocket.add) {
        SocketData tsd=new SocketData(serverSocket.accept(tsh));
        socketCenter.add.add(tsd);
        // }
      }
    });
    acceptT.start();
    (serverWriteT=new ServerWriteThread(Screen0007.this)).start();
    (serverReadT=new ServerReadThread(Screen0007.this)).start();
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
    serverSocket.dispose();
    socketCenter.refresh();
    for(SocketData i:socketCenter.list) i.s.dispose();
  }
}