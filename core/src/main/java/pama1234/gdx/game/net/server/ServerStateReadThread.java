package pama1234.gdx.game.net.server;

import static pama1234.gdx.game.net.NetUtil.intToState;
import static pama1234.gdx.game.net.NetUtil.readNBytes;

import java.io.IOException;

import pama1234.data.ByteUtil;
import pama1234.gdx.game.app.Screen0007;
import pama1234.gdx.game.net.NetUtil.NetState;
import pama1234.gdx.game.net.SocketData;

public class ServerStateReadThread extends Thread{
  public Screen0007 p;
  public SocketData stateSocket;
  public ServerStateReadThread(Screen0007 p,SocketData stateSocket) {
    this.p=p;
    this.stateSocket=stateSocket;
  }
  @Override
  public void run() {
    byte[] inData=new byte[12];
    while(!p.stop) {
      synchronized(p.socketCenter.list) {
        // synchronized(p.group) {
        try {
          doF(stateSocket,inData,
            intToState(ByteUtil.byteToInt(readNBytes(stateSocket,inData,0,4),0)),
            ByteUtil.byteToInt(readNBytes(stateSocket,inData,0,4),0));
        }catch(IOException e1) {
          e1.printStackTrace();
        }
      }
      // }
    }
  }
  // public void doF(byte[] inData,int state,int readSize) throws IOException {
  public void doF(SocketData e,byte[] inData,NetState state,int readSize) throws IOException {
    System.out.println("ServerRead state="+state+" readSize="+readSize);
    if(state!=e.state) {
      System.out.println("state!=e.state "+state+" "+e.state);
      return;
    }
    switch(state) {
      case FinishedProcessing: {}
        break;
      default:
        throw new RuntimeException("state err="+state);
    }
  }
}