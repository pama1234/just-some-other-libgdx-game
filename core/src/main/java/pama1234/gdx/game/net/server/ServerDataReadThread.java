package pama1234.gdx.game.net.server;

import static pama1234.gdx.game.net.NetUtil.intToState;
import static pama1234.gdx.game.net.NetUtil.readNBytes;
import static pama1234.gdx.game.net.NetUtil.NetState.DataTransfer;

import java.io.IOException;

import pama1234.data.ByteUtil;
import pama1234.gdx.game.app.Screen0007;
import pama1234.gdx.game.net.NetUtil.NetState;
import pama1234.gdx.game.net.SocketData;
import pama1234.gdx.game.util.ClientPlayer3D;

public class ServerDataReadThread extends Thread{
  public Screen0007 p;
  public SocketData dataSocket;
  public ServerDataReadThread(Screen0007 p,SocketData dataSocket) {
    this.p=p;
    this.dataSocket=dataSocket;
  }
  @Override
  public void run() {
    byte[] inData=new byte[12];
    while(!p.stop) {
      synchronized(p.socketCenter.list) {
        // synchronized(p.group) {
        try {
          doF(dataSocket,inData,
            intToState(ByteUtil.byteToInt(readNBytes(dataSocket,inData,0,4),0)),
            ByteUtil.byteToInt(readNBytes(dataSocket,inData,0,4),0));
        }catch(IOException e1) {
          e1.printStackTrace();
        }
      }
      // }
    }
  }
  public void doF(SocketData e,byte[] inData,NetState state,int readSize) throws IOException {
    System.out.println("ServerRead state="+state+" readSize="+readSize);
    if(state!=e.state) {
      System.out.println("state!=e.state "+state+" "+e.state);
      return;
    }
    switch(state) {
      case Authentication: {
        byte[] nameBytes=new byte[readSize];
        readNBytes(e,nameBytes,0,readSize);
        e.name=new String(nameBytes);
        System.out.println("e.name "+e.name);
        e.state=DataTransfer;
      }
        break;
      case DataTransfer: {
        readNBytes(e,inData,0,12);
        ClientPlayer3D tp=p.playerCenter.hashMap.get(e.name);
        tp.point.des.set(
          ByteUtil.byteToFloat(inData,0),
          ByteUtil.byteToFloat(inData,4),
          ByteUtil.byteToFloat(inData,8));
      }
        break;
      default:
        throw new RuntimeException("state err="+state);
    }
  }
}