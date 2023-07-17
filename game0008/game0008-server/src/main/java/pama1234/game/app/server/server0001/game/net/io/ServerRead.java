package pama1234.game.app.server.server0001.game.net.io;

import static pama1234.game.app.server.server0001.game.net.NetUtil.catchException;
import static pama1234.game.app.server.server0001.game.net.NetUtil.debug;
import static pama1234.game.app.server.server0001.game.net.NetUtil.protocolVersion;
import static pama1234.game.app.server.server0001.game.net.NetUtil.readNBytes;
import static pama1234.game.app.server.server0001.game.net.state.ServerState.ServerDataTransfer;

import java.io.IOException;
import java.net.SocketException;

import pama1234.data.ByteUtil;
import pama1234.game.app.server.server0001.game.ServerPlayer3D;
import pama1234.game.app.server.server0001.game.net.SocketData0001;
import pama1234.game.app.server.server0001.game.net.SocketData0001.Token;
import pama1234.game.app.server.server0001.game.net.data.Server0001Core;
import pama1234.game.app.server.server0001.game.net.state.ClientState;
import pama1234.game.app.server.server0001.game.net.state.ServerState;

public class ServerRead extends Thread{
  public Server0001Core p;
  public SocketData0001 s;
  public ServerRead(Server0001Core p,SocketData0001 dataSocket) {
    super("ServerRead "+dataSocket.s.getRemoteAddress());
    this.p=p;
    this.s=dataSocket;
  }
  @Override
  public void run() {
    byte[] data=new byte[128];
    while(!s.stop) {
      synchronized(p.socketCenter.list) {
        try {
          doF(s,data,
            s.clientState=ClientState.intToState(ByteUtil.byteToInt(readNBytes(s,data,0,4),0)),
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
  public void doF(SocketData0001 e,byte[] inData,ClientState state,int readSize) throws IOException {
    if(debug) System.out.println("ServerRead state="+state+" readSize="+readSize);
    switch(state) {
      case ClientAuthentication: {
        byte[] nameBytes=new byte[readSize];
        readNBytes(e,nameBytes,0,readSize);
        e.token=new Token(new String(nameBytes));
        e.serverState=ServerDataTransfer;
        p.playerCenter.add.add(new ServerPlayer3D(e.name(),0,0,0));//TODO ?
        p.playerCenter.refresh();
        System.out.println("Auth "+e.name());
      }
        break;
      case ClientDataTransfer: {
        readNBytes(e,inData,0,4*3);
        ServerPlayer3D tp=p.playerCenter.hashMap.get(new Token(e.name()));
        if(tp==null) {
          e.clientState=ClientState.ClientAuthentication;
          return;
        }
        tp.point.des.set(
          ByteUtil.byteToFloat(inData,0),
          ByteUtil.byteToFloat(inData,4),
          ByteUtil.byteToFloat(inData,8));
      }
        break;
      case ClientException: {}
        break;
      case ClientFinishedProcessing: {}
        break;
      case ClientProcessing: {}
        break;
      case ClientProtocolVersion: {
        byte[] nameBytes=new byte[readSize];
        readNBytes(s,nameBytes,0,readSize);
        String version=new String(nameBytes);
        if(!version.equals(protocolVersion)) throw new RuntimeException("!version.equals(protocolVersion)"+version+" "+protocolVersion);
        s.serverState=ServerState.ServerAuthentication;
      }
        break;
      case ClientSendStringMessage: {}
        break;
      default:
        throw new RuntimeException("state err="+state);
    }
  }
  public void dispose() {
    s.stop=true;
  }
}