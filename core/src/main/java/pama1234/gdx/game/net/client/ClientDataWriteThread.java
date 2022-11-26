package pama1234.gdx.game.net.client;

import static pama1234.gdx.game.net.NetUtil.intToState;
import static pama1234.gdx.game.net.NetUtil.readNBytes;
import static pama1234.gdx.game.net.NetUtil.writeHeader;
import static pama1234.gdx.game.net.NetUtil.NetState.DataTransfer;

import java.io.IOException;

import pama1234.data.ByteUtil;
import pama1234.gdx.game.app.Screen0003;
import pama1234.gdx.game.net.SocketData;
import pama1234.gdx.game.net.NetUtil.NetState;

public class ClientDataWriteThread extends Thread{
  public Screen0003 p;
  public SocketData stateSocket,dataSocket;
  public ClientDataWriteThread(Screen0003 p,SocketData stateSocket,SocketData dataSocket) {
    this.p=p;
    this.stateSocket=stateSocket;
    this.dataSocket=dataSocket;
  }
  @Override
  public void run() {
    byte[] data=new byte[12];
    while(!p.stop) {
      try {
        doF(stateSocket,data,
          intToState(ByteUtil.byteToInt(readNBytes(dataSocket,data,0,4),0)),
          ByteUtil.byteToInt(readNBytes(dataSocket,data,0,4),0));
        doF(data);
      }catch(Exception e) {
        e.printStackTrace();
      }
    }
  }
  public void doF(SocketData stateSocket,byte[] data,NetState state,int size) {
    switch(state) {
      case FinishedProcessing: {
        // readNBytes(stateSocket,inData,0,readSize);
        // stateSocket.state=state;
      }
        break;
      default:
        // int ti=stateSocket.state;
        // stateSocket.state=1;
        throw new RuntimeException("state err="+state);
    }
  }
  public void doF(byte[] outData) throws IOException {
    System.out.println("ClientWrite state="+dataSocket.state);
    switch(dataSocket.state) {
      case Authentication: {
        // e.name
        // e.o.write(ByteUtil.intToByte(e.state,outData,0),0,4);
        byte[] nameBytes=dataSocket.name.getBytes();
        writeHeader(dataSocket,outData,nameBytes.length);
        dataSocket.o.write(nameBytes);
        dataSocket.o.flush();
        dataSocket.state=DataTransfer;
        // p.println(Arrays.toString(nameBytes));
      }
        break;
      case DataTransfer: {
        // e.o.write(ByteUtil.intToByte(e.state,outData,0),0,4);
        // e.o.write(ByteUtil.intToByte(12,outData,0),0,4);
        writeHeader(dataSocket,outData,12);
        ByteUtil.floatToByte(p.yourself.x(),outData,0);
        ByteUtil.floatToByte(p.yourself.y(),outData,4);
        ByteUtil.floatToByte(p.yourself.z(),outData,8);
        // ByteUtil.floatToByte(p.yourself.x(),outData);
        dataSocket.o.write(outData,0,12);
        dataSocket.o.flush();
        p.sleep(40);
      }
        break;
      default:
        throw new RuntimeException("state err="+dataSocket.state);
    }
  }
}