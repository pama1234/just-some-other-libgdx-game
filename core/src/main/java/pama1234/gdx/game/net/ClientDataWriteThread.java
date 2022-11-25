package pama1234.gdx.game.net;

import static pama1234.gdx.game.net.NetUtil.writeHeader;

import java.io.IOException;

import pama1234.data.ByteUtil;
import pama1234.gdx.game.app.Screen0003;

public class ClientDataWriteThread extends Thread{
  public Screen0003 p;
  public SocketData e;
  public ClientDataWriteThread(Screen0003 p,SocketData e) {
    this.p=p;
    this.e=e;
  }
  @Override
  public void run() {
    byte[] outData=new byte[12];
    while(!p.stop) {
      try {
        doF(outData);
      }catch(Exception e) {
        e.printStackTrace();
      }
    }
  }
  public void doF(byte[] outData) throws IOException {
    System.out.println("ClientWrite state="+e.state);
    switch(e.state) {
      case 1: {
        // e.name
        // e.o.write(ByteUtil.intToByte(e.state,outData,0),0,4);
        byte[] nameBytes=e.name.getBytes();
        writeHeader(e,outData,nameBytes.length);
        e.o.write(nameBytes);
        e.o.flush();
        e.state=2;
        // p.println(Arrays.toString(nameBytes));
      }
        break;
      case 2: {
        // e.o.write(ByteUtil.intToByte(e.state,outData,0),0,4);
        // e.o.write(ByteUtil.intToByte(12,outData,0),0,4);
        writeHeader(e,outData,12);
        ByteUtil.floatToByte(p.yourself.x(),outData,0);
        ByteUtil.floatToByte(p.yourself.y(),outData,4);
        ByteUtil.floatToByte(p.yourself.z(),outData,8);
        // ByteUtil.floatToByte(p.yourself.x(),outData);
        e.o.write(outData,0,12);
        e.o.flush();
        p.sleep(40);
      }
        break;
      default:
        int ti=e.state;
        e.state=1;
        throw new RuntimeException("state err="+ti);
    }
  }
  // public void writeHeader(byte[] outData,int state,int size) throws IOException {
  //   e.o.write(ByteUtil.intToByte(state,outData,0),0,4);
  //   e.o.write(ByteUtil.intToByte(size,outData,0),0,4);
  //   e.o.flush();
  // }
  // public void writeHeader(byte[] outData,int size) throws IOException {
  //   writeHeader(outData,e.state,4);
  // }
}