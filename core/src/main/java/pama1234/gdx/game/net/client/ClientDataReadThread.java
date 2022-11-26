package pama1234.gdx.game.net.client;

import static pama1234.gdx.game.net.NetUtil.intToState;
import static pama1234.gdx.game.net.NetUtil.readNBytes;
import static pama1234.gdx.game.net.NetUtil.writeHeader;
import static pama1234.gdx.game.net.NetUtil.NetState.DataTransfer;

import java.io.IOException;

import pama1234.data.ByteUtil;
import pama1234.gdx.game.app.Screen0003;
import pama1234.gdx.game.net.NetUtil.NetState;
import pama1234.gdx.game.net.SocketData;

public class ClientDataReadThread extends Thread{
  public Screen0003 p;
  public SocketData stateSocket,dataSocket;
  public ClientDataReadThread(Screen0003 p,SocketData stateSocket,SocketData dataSocket) {
    this.p=p;
    this.stateSocket=stateSocket;
    this.dataSocket=dataSocket;
  }
  @Override
  public void run() {
    // byte[] td=new byte[4];
    byte[] data=new byte[20];
    // int state;
    // int readSize;
    while(!p.stop) {
      try {
        // int ti=client.i.readNBytes(td,0,4);
        // println(ti+" "+Arrays.toString(td)+" "+ByteUtil.byteToInt(td));
        // synchronized(cellData) {
        // state=ByteUtil.byteToInt(readNBytes(inData,0,4),0);
        // readSize=ByteUtil.byteToInt(readNBytes(inData,0,4),0);
        doF(data,
          intToState(ByteUtil.byteToInt(readNBytes(dataSocket,data,0,4),0)),
          ByteUtil.byteToInt(readNBytes(dataSocket,data,0,4),0));
        stateSocket.state=NetState.FinishedProcessing;
        writeHeader(stateSocket,data,0);
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
      case Authentication: {
        if(readSize!=4) throw new RuntimeException("state 0 readSize!=4 "+readSize);//TODO
        readNBytes(dataSocket,inData,0,readSize);
        // System.out.println(ByteUtil.byteToInt(inData,0));
        dataSocket.state=DataTransfer;
        // p.println("e.state=0");//TODO
        // p.println(inData);
      }
        break;
      case DataTransfer: {
        if(readSize!=p.cellData.length) throw new RuntimeException("state 1 readSize!=p.cellData.length "+readSize+" "+p.cellData.length);//TODO
        for(int i=0;i<readSize;i++) {
          readNBytes(dataSocket,inData,0,inData.length);
          p.cellData[i].id=ByteUtil.byteToInt(inData,0);
          p.cellData[i].type=ByteUtil.byteToInt(inData,4);
          p.cellData[i].x=ByteUtil.byteToFloat(inData,8);
          p.cellData[i].y=ByteUtil.byteToFloat(inData,12);
          p.cellData[i].z=ByteUtil.byteToFloat(inData,16);
        }
      }
        break;
      default:
        throw new RuntimeException("state err="+state);
    }
  }
}