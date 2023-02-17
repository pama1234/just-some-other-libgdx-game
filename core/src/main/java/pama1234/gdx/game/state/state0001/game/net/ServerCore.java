package pama1234.gdx.game.state.state0001.game.net;

import static pama1234.gdx.game.state.state0001.game.net.NetUtil.catchException;
import static pama1234.gdx.game.state.state0001.game.net.NetUtil.debug;
import static pama1234.gdx.game.state.state0001.game.net.NetUtil.readNBytes;

import java.io.IOException;
import java.net.SocketException;

import pama1234.data.ByteUtil;
import pama1234.gdx.game.state.state0001.game.net.NetState0002.ServerState;
import pama1234.gdx.game.state.state0001.game.net.ServerExecute.ServerReadF;
import pama1234.gdx.game.state.state0001.game.net.ServerExecute.ServerWriteF;

public class ServerCore{
  public static class ServerRead extends Thread{
    public ServerReadF[] fArray;
    public ServerData p;
    public SocketData s;
    public ServerRead(ServerData pIn,SocketData dataSocket) {
      super("ServerRead "+dataSocket.s.getRemoteAddress());
      this.p=pIn;
      this.s=dataSocket;
      fArray=ServerExecute.generateReadF();
    }
    @Override
    public void run() {
      byte[] data=new byte[128];
      while(!s.stop) {
        synchronized(p.socketCenter.list) {
          try {
            int stateInt=ByteUtil.byteToInt(readNBytes(s,data,0,4),0);
            doF(s,data,stateInt,
              ByteUtil.byteToInt(readNBytes(s,data,0,4),0));
          }catch(SocketException e1) {
            catchException(e1,s);
          }catch(IOException e2) {
            catchException(e2,s);
          }
        }
      }
      // p.serverReadPool.remove.add(this);
    }
    public void doF(SocketData s,byte[] inData,int stateInt,int readSize) throws IOException {
      if(debug) System.out.println("ServerRead state="+stateInt+" readSize="+readSize);
      ServerReadF serverReadF=fArray[stateInt];
      if(serverReadF!=null) serverReadF.execute(p,s,inData,readSize);
    }
    public void dispose() {
      s.stop=true;
    }
  }
  public static class ServerWrite extends Thread{
    public ServerWriteF[] fArray;
    public ServerData p;
    public SocketData s;
    public ServerWrite(ServerData p,SocketData dataSocket) {
      super("ServerWrite "+dataSocket.s.getRemoteAddress());
      this.p=p;
      this.s=dataSocket;
    }
    @Override
    public void run() {
      byte[] data=new byte[20];
      while(!s.stop) {
        synchronized(p.group) {
          try {
            doF(s,data);
          }catch(SocketException e1) {
            catchException(e1,s);
          }catch(IOException e2) {
            catchException(e2,s);
          }
        }
      }
      // p.serverWritePool.remove.add(this);
    }
    public void doF(SocketData s,byte[] outData) throws IOException {
      if(debug) System.out.println("ServerWrite state="+s.serverState);
      ServerWriteF serverWriteF=fArray[ServerState.stateToInt(s.serverState)];
      if(serverWriteF!=null) serverWriteF.execute(p,s,outData);
    }
    public void dispose() {
      s.stop=true;
    }
  }
}