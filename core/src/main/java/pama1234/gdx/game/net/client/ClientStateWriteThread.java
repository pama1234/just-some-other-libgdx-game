package pama1234.gdx.game.net.client;

import static pama1234.gdx.game.net.NetUtil.writeHeader;

import java.io.IOException;

import pama1234.data.ByteUtil;
import pama1234.gdx.game.app.Screen0003;
import pama1234.gdx.game.net.SocketData;

public class ClientStateWriteThread extends Thread{
  public Screen0003 p;
  public SocketData stateSocket;
  public ClientStateWriteThread(Screen0003 p,SocketData stateSocket) {
    this.p=p;
    this.stateSocket=stateSocket;
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
    System.out.println("ClientWrite state="+stateSocket.state);
    switch(stateSocket.state) {
      case 1: {
        // e.name
        // e.o.write(ByteUtil.intToByte(e.state,outData,0),0,4);
        byte[] nameBytes=stateSocket.name.getBytes();
        writeHeader(stateSocket,outData,nameBytes.length);
        stateSocket.o.write(nameBytes);
        stateSocket.o.flush();
        stateSocket.state=2;
        // p.println(Arrays.toString(nameBytes));
      }
        break;
      case 2: {
        // e.o.write(ByteUtil.intToByte(e.state,outData,0),0,4);
        // e.o.write(ByteUtil.intToByte(12,outData,0),0,4);
        writeHeader(stateSocket,outData,12);
        ByteUtil.floatToByte(p.yourself.x(),outData,0);
        ByteUtil.floatToByte(p.yourself.y(),outData,4);
        ByteUtil.floatToByte(p.yourself.z(),outData,8);
        // ByteUtil.floatToByte(p.yourself.x(),outData);
        stateSocket.o.write(outData,0,12);
        stateSocket.o.flush();
        p.sleep(40);
      }
        break;
      default:
        int ti=stateSocket.state;
        stateSocket.state=1;
        throw new RuntimeException("state err="+ti);
    }
  }
}