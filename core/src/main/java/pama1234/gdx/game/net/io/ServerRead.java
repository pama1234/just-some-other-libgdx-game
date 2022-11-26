package pama1234.gdx.game.net.io;

import static pama1234.gdx.game.net.NetUtil.catchException;
import static pama1234.gdx.game.net.NetUtil.intToState;
import static pama1234.gdx.game.net.NetUtil.readNBytes;
import static pama1234.gdx.game.net.NetUtil.NetState.DataTransfer;

import java.io.IOException;
import java.net.SocketException;

import pama1234.data.ByteUtil;
import pama1234.gdx.game.app.Screen0007;
import pama1234.gdx.game.net.NetUtil.NetState;
import pama1234.gdx.game.net.SocketData;
import pama1234.gdx.game.util.ClientPlayer3D;

public class ServerRead extends Thread{
  public Screen0007 p;
  public SocketData s;
  // public boolean stop;
  public ServerRead(Screen0007 p,SocketData dataSocket) {
    this.p=p;
    this.s=dataSocket;
  }
  @Override
  public void run() {
    byte[] data=new byte[12];
    while(!s.stop) {
      synchronized(p.socketCenter.list) {
        // synchronized(p.group) {
        try {
          doF(s,data,
            intToState(ByteUtil.byteToInt(readNBytes(s,data,0,4),0)),
            ByteUtil.byteToInt(readNBytes(s,data,0,4),0));
        }catch(SocketException e1) {
          catchException(e1,s);
        }catch(IOException e2) {
          catchException(e2,s);
        }
      }
    }
    p.serverReadPool.remove.add(this);
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
        // System.out.println("e.name "+e.name);
        e.state=DataTransfer;
        p.playerCenter.add.add(new ClientPlayer3D(p,e.name,0,0,0));
        p.playerCenter.refresh();
        // System.out.println(p.playerCenter.list.getFirst());
        // System.out.println(p.playerCenter.hashMap.size());
        // System.out.println(p.playerCenter.hashMap.get(e.name));
      }
        break;
      case DataTransfer: {
        readNBytes(e,inData,0,12);
        ClientPlayer3D tp=p.playerCenter.hashMap.get(e.name);
        if(tp==null) {
          e.state=NetState.Authentication;
          return;
        }
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
  public void dispose() {
    s.stop=true;
  }
}