package pama1234.gdx.game.net;

import java.io.IOException;

import pama1234.data.ByteUtil;
import pama1234.gdx.game.app.Screen0003;

public class ClientWriteThread extends Thread{
  public Screen0003 p;
  public ClientWriteThread(Screen0003 p) {
    this.p=p;
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
    System.out.println("ClientWrite state="+p.client.state);
    switch(p.client.state) {
      case 1: {
        // p.client.name
        // p.client.o.write(ByteUtil.intToByte(p.client.state,outData,0),0,4);
        byte[] nameBytes=p.client.name.getBytes();
        writeHeader(outData,p.client.state,nameBytes.length);
        p.client.o.write(nameBytes);
        p.client.o.flush();
        p.client.state=2;
        // p.println(Arrays.toString(nameBytes));
      }
        break;
      case 2: {
        // p.client.o.write(ByteUtil.intToByte(p.client.state,outData,0),0,4);
        // p.client.o.write(ByteUtil.intToByte(12,outData,0),0,4);
        writeHeader(outData,p.client.state,12);
        ByteUtil.floatToByte(p.yourself.x(),outData,0);
        ByteUtil.floatToByte(p.yourself.y(),outData,4);
        ByteUtil.floatToByte(p.yourself.z(),outData,8);
        // ByteUtil.floatToByte(p.yourself.x(),outData);
        p.client.o.write(outData,0,12);
        p.client.o.flush();
        p.sleep(40);
      }
        break;
      default:
        int ti=p.client.state;
        p.client.state=1;
        throw new RuntimeException("state err="+ti);
    }
  }
  public void writeHeader(byte[] outData,int state,int size) throws IOException {
    p.client.o.write(ByteUtil.intToByte(state,outData,0),0,4);
    p.client.o.write(ByteUtil.intToByte(size,outData,0),0,4);
    p.client.o.flush();
  }
  public void writeHeader(byte[] outData,int size) throws IOException {
    writeHeader(outData,p.client.state,4);
  }
}