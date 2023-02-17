package pama1234.gdx.game.state.state0001.game.net;

import static pama1234.gdx.game.state.state0001.game.net.NetUtil.catchException;
import static pama1234.gdx.game.state.state0001.game.net.NetUtil.debug;
import static pama1234.gdx.game.state.state0001.game.net.NetUtil.readNBytes;

import java.io.IOException;
import java.net.SocketException;

import pama1234.data.ByteUtil;
import pama1234.gdx.game.state.state0001.game.net.ClientExecute.ClientReadF;
import pama1234.gdx.game.state.state0001.game.net.ClientExecute.ClientState;
import pama1234.gdx.game.state.state0001.game.net.ClientExecute.ClientWriteF;

public class ClientIO{
  public static class ClientRead extends Thread{
    public ClientReadF[] fArray;
    public ClientData p;
    public SocketData s;
    public byte[] data=new byte[128];
    public ClientRead(ClientData p,SocketData dataSocket) {
      super("ClientRead "+dataSocket.s.getRemoteAddress());
      this.p=p;
      this.s=dataSocket;
      fArray=ClientExecute.generateReadF();
    }
    @Override
    public void run() {
      while(!s.stop) {
        try {
          int stateInt=ByteUtil.byteToInt(readNBytes(s,data,0,4),0);
          doF(data,
            stateInt,
            ByteUtil.byteToInt(readNBytes(s,data,0,4),0));
        }catch(SocketException e1) {
          catchException(e1,s);
        }catch(IOException e2) {
          catchException(e2,s);
        }
      }
    }
    public void doF(byte[] inData,int stateInt,int readSize) throws IOException {
      if(debug) System.out.println("ClientRead state="+stateInt+" readSize="+readSize);
      ClientReadF clientReadF=fArray[stateInt];
      if(clientReadF!=null) clientReadF.execute(p,s,inData,stateInt,readSize);
    }
    public void dispose() {
      s.stop=true;
    }
  }
  public static class ClientWrite extends Thread{
    public ClientWriteF[] fArray;
    public ClientData p;
    public SocketData s;
    public byte[] data=new byte[128];
    public ClientWrite(ClientData p,SocketData dataSocket) {
      super("ClientWrite "+dataSocket.s.getRemoteAddress());
      this.p=p;
      this.s=dataSocket;
      fArray=ClientExecute.generateWriteF();
    }
    @Override
    public void run() {
      while(!s.stop) {
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
      if(debug) System.out.println("ClientWrite state="+s.clientState);
      ClientWriteF clientWriteF=fArray[ClientState.stateToInt(s.clientState)];
      if(clientWriteF!=null) clientWriteF.execute(p,s,outData);
    }
    public void dispose() {
      s.stop=true;
    }
  }
}