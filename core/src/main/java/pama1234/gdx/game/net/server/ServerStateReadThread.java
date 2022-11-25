package pama1234.gdx.game.net.server;

import static pama1234.gdx.game.net.NetUtil.readNBytes;

import java.io.IOException;

import pama1234.data.ByteUtil;
import pama1234.gdx.game.app.Screen0007;
import pama1234.gdx.game.net.SocketData;
import pama1234.gdx.game.util.ClientPlayer3D;

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
            ByteUtil.byteToInt(readNBytes(stateSocket,inData,0,4),0),
            ByteUtil.byteToInt(readNBytes(stateSocket,inData,0,4),0));
        }catch(IOException e1) {
          e1.printStackTrace();
        }
      }
      // }
    }
  }
  // public void doF(byte[] inData,int state,int readSize) throws IOException {
  public void doF(SocketData e,byte[] inData,int state,int readSize) throws IOException {
    System.out.println("ServerRead state="+state+" readSize="+readSize);
    if(state!=e.state) {
      System.out.println("state!=e.state "+state+" "+e.state);
      return;
    }
    switch(state) {
      case 1: {
        byte[] nameBytes=new byte[readSize];
        readNBytes(e,nameBytes,0,readSize);
        e.name=new String(nameBytes);
        System.out.println("e.name "+e.name);
        e.state=2;
      }
        break;
      case 2: {
        readNBytes(e,inData,0,12);
        ClientPlayer3D tp=p.playerCenter.hashMap.get(e.name);
        tp.point.des.set(
          ByteUtil.byteToFloat(inData,0),
          ByteUtil.byteToFloat(inData,4),
          ByteUtil.byteToFloat(inData,8));
      }
        break;
      default:
        int ti=e.state;
        e.state=1;
        throw new RuntimeException("state err="+ti);
    }
  }
}