package pama1234.gdx.game.app;

import java.io.IOException;
import java.util.Arrays;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net.Protocol;
import com.badlogic.gdx.net.ServerSocket;
import com.badlogic.gdx.net.ServerSocketHints;
import com.badlogic.gdx.net.SocketHints;

import pama1234.data.ByteUtil;
import pama1234.gdx.game.net.SocketWrapper;
import pama1234.gdx.util.app.UtilScreen3D;
import pama1234.gdx.util.net.SocketData;
import pama1234.gdx.util.wrapper.Center;

public class Screen0006 extends UtilScreen3D{
  ServerSocket serverSocket;
  Center<SocketData> centerS;
  //---
  SocketData client;
  //---
  Thread serverT,acceptT,clientT;
  @Override
  public void setup() {
    centerS=new Center<>();
    //---
    SocketHints tsh=new SocketHints();
    tsh.keepAlive=true;
    tsh.performancePrefConnectionTime=0;
    tsh.performancePrefLatency=2;
    tsh.performancePrefBandwidth=1;
    //---
    ServerSocketHints tssh=new ServerSocketHints();
    serverSocket=Gdx.net.newServerSocket(Protocol.TCP,"127.0.0.1",12347,tssh);
    acceptT=new Thread(()-> {
      while(!stop) centerS.add.add(new SocketData(new SocketWrapper(serverSocket.accept(tsh))));
    });
    acceptT.start();
    //---
    serverT=new Thread(()-> {
      while(!stop) {
        synchronized(centerS.list) {
          for(SocketData i:centerS.list) {
            try {
              i.o.write(new byte[] {(byte)((frameCount>>24)&0xff),(byte)((frameCount>>16)&0xff),(byte)((frameCount>>8)&0xff),(byte)(frameCount&0xff)});
            }catch(IOException e) {
              e.printStackTrace();
            }
          }
        }
      }
    });
    serverT.start();
    //---
    client=new SocketData(new SocketWrapper(Gdx.net.newClientSocket(Protocol.TCP,"127.0.0.1",12347,tsh)));
    //---
    clientT=new Thread(()-> {
      byte[] td=new byte[4];
      while(!stop) {
        try {
          int ti=client.i.readNBytes(td,0,4);
          println(ti+" "+Arrays.toString(td)+" "+ByteUtil.byteToInt(td));
        }catch(Exception e) {
          e.printStackTrace();
        }
      }
    });
    clientT.start();
  }
  @Override
  public void update() {
    centerS.refresh();
  }
  @Override
  public void display() {}
  @Override
  public void frameResized() {}
  @Override
  public void dispose() {
    super.dispose();
    serverSocket.dispose();
    centerS.refresh();
    for(SocketData i:centerS.list) i.s.dispose();
    client.s.dispose();
  }
}