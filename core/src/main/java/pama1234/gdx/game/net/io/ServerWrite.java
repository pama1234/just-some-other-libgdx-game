package pama1234.gdx.game.net.io;

import static pama1234.gdx.game.net.NetUtil.catchException;
import static pama1234.gdx.game.net.NetUtil.writeHeader;

import java.io.IOException;
import java.net.SocketException;

import pama1234.data.ByteUtil;
import pama1234.gdx.game.app.Screen0007;
import pama1234.gdx.game.net.SocketData;

public class ServerWrite extends Thread{
  public Screen0007 p;
  public SocketData s;
  // public boolean stop;
  public ServerWrite(Screen0007 p,SocketData dataSocket) {
    this.p=p;
    this.s=dataSocket;
  }
  @Override
  public void run() {
    byte[] data=new byte[20];
    while(!s.stop) {
      // p.socketCenter.refresh();
      // synchronized(p.socketCenter.list) {
      synchronized(p.group) {
        // for(SocketData e:p.socketCenter.list) {
        try {
          doF(s,data);
        }catch(SocketException e1) {
          catchException(e1,s);
        }catch(IOException e2) {
          catchException(e2,s);
        }
      }
    }
    p.serverWritePool.remove.add(this);
  }
  public void doF(SocketData e,byte[] outData) throws IOException {
    System.out.println("ServerWrite state="+e.state);
    // if(e.state==1)
    switch(e.state) {
      case Authentication: {
        // System.out.println("abc");
        // if(e.authCooling>0) {
        //   e.authCooling--;
        //   return;
        // }
        writeHeader(e,outData,4);
        e.o.write(ByteUtil.intToByte(1234,outData,0),0,4);
        e.o.flush();
        p.sleep(2000);
        // e.authCooling=20000;
        // p.sleep(200);
      }
        break;
      case DataTransfer: {
        writeHeader(e,outData,p.group.size);
        // p.println(1,p.group.size);
        for(int i=0;i<p.group.size;i++) {
          ByteUtil.intToByte(i,outData,0);
          ByteUtil.intToByte(p.group.type[i],outData,4);
          ByteUtil.floatToByte(p.group.x(i),outData,8);
          ByteUtil.floatToByte(p.group.y(i),outData,12);
          ByteUtil.floatToByte(p.group.z(i),outData,16);
          e.o.write(outData);
        }
        e.o.flush();
      }
        break;
      default:
        throw new RuntimeException("state err="+e.state);
    }
  }
  public void dispose() {
    s.stop=true;
  }
}