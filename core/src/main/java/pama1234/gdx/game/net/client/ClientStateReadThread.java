package pama1234.gdx.game.net.client;

import static pama1234.gdx.game.net.NetUtil.intToState;
import static pama1234.gdx.game.net.NetUtil.readNBytes;

import java.io.IOException;

import pama1234.data.ByteUtil;
import pama1234.gdx.game.app.Screen0003;
import pama1234.gdx.game.net.NetUtil.NetState;
import pama1234.gdx.game.net.SocketData;

public class ClientStateReadThread extends Thread{
  public Screen0003 p;
  public SocketData stateSocket;
  public ClientStateReadThread(Screen0003 p,SocketData stateSocket) {
    this.p=p;
    this.stateSocket=stateSocket;
  }
  @Override
  public void run() {
    // byte[] td=new byte[4];
    byte[] inData=new byte[20];
    // int state;
    // int readSize;
    while(!p.stop) {
      try {
        // int ti=client.i.readNBytes(td,0,4);
        // println(ti+" "+Arrays.toString(td)+" "+ByteUtil.byteToInt(td));
        // synchronized(cellData) {
        // state=ByteUtil.byteToInt(readNBytes(inData,0,4),0);
        // readSize=ByteUtil.byteToInt(readNBytes(inData,0,4),0);
        doF(inData,
          intToState(ByteUtil.byteToInt(readNBytes(stateSocket,inData,0,4),0)),
          ByteUtil.byteToInt(readNBytes(stateSocket,inData,0,4),0));
        // doF(inData,state,readSize);
        // }
      }catch(Exception e) {
        e.printStackTrace();
      }
    }
  }
  public void doF(byte[] inData,NetState state,int readSize) throws IOException {
    System.out.println("ClientRead state="+state+" readSize="+readSize);
    // p.println(state,readSize);
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
}