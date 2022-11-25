package pama1234.gdx.game.net;

import static pama1234.gdx.game.net.NetUtil.writeHeader;

import java.io.IOException;

import pama1234.data.ByteUtil;
import pama1234.gdx.game.app.Screen0007;

public class ServerDataWriteThread extends Thread{
  public Screen0007 p;
  public SocketData e;
  public ServerDataWriteThread(Screen0007 p,SocketData e) {
    this.p=p;
    this.e=e;
  }
  @Override
  public void run() {
    byte[] outData=new byte[20];
    while(!p.stop) {
      p.socketCenter.refresh();
      synchronized(p.socketCenter.list) {
        synchronized(p.group) {
          for(SocketData e:p.socketCenter.list) {
            try {
              doF(e,outData);
            }catch(IOException exception) {
              exception.printStackTrace();
            }
          }
        }
      }
    }
  }
  public void doF(SocketData e,byte[] outData) throws IOException {
    System.out.println("ServerWrite state="+e.state);
    // if(e.state==1)
    switch(e.state) {
      case 1: {
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
      case 2: {
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
        int ti=e.state;
        e.state=1;
        throw new RuntimeException("state err="+ti);
    }
  }
  // public void writeHeader(SocketData e,byte[] outData,int state,int size) throws IOException {
  //   e.o.write(ByteUtil.intToByte(state,outData,0),0,4);
  //   e.o.write(ByteUtil.intToByte(size,outData,0),0,4);
  //   e.o.flush();
  // }
  // public void writeHeader(SocketData e,byte[] outData,int size) throws IOException {
  //   writeHeader(e,outData,e.state,4);
  // }
}