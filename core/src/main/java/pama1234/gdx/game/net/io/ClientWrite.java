package pama1234.gdx.game.net.io;

import static pama1234.gdx.game.net.NetUtil.catchException;
import static pama1234.gdx.game.net.NetUtil.writeHeader;

import java.io.IOException;
import java.net.SocketException;

import pama1234.data.ByteUtil;
import pama1234.gdx.game.app.Screen0003;
import pama1234.gdx.game.net.SocketData;

public class ClientWrite extends Thread{
  public Screen0003 p;
  public SocketData s;
  // public boolean stop;
  public ClientWrite(Screen0003 p,SocketData dataSocket) {
    this.p=p;
    this.s=dataSocket;
  }
  @Override
  public void run() {
    byte[] data=new byte[12];
    while(!p.stop) {
      try {
        doF(data);
      }catch(SocketException e1) {
        catchException(e1,s);
      }catch(IOException e2) {
        catchException(e2,s);
      }
    }
  }
  public void doF(byte[] outData) throws IOException {
    System.out.println("ClientWrite state="+s.state);
    switch(s.state) {
      case Authentication: {
        // e.name
        // e.o.write(ByteUtil.intToByte(e.state,outData,0),0,4);
        byte[] nameBytes=s.name.getBytes();
        writeHeader(s,outData,nameBytes.length);
        s.o.write(nameBytes);
        s.o.flush();
        // s.state=DataTransfer;
        // p.println(Arrays.toString(nameBytes));
      }
        break;
      case DataTransfer: {
        // e.o.write(ByteUtil.intToByte(e.state,outData,0),0,4);
        // e.o.write(ByteUtil.intToByte(12,outData,0),0,4);
        writeHeader(s,outData,12);
        ByteUtil.floatToByte(p.yourself.x(),outData,0);
        ByteUtil.floatToByte(p.yourself.y(),outData,4);
        ByteUtil.floatToByte(p.yourself.z(),outData,8);
        // ByteUtil.floatToByte(p.yourself.x(),outData);
        s.o.write(outData,0,12);
        s.o.flush();
        p.sleep(40);
      }
        break;
      default:
        throw new RuntimeException("state err="+s.state);
    }
  }
  public void dispose() {
    s.stop=true;
  }
}