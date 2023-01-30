package pama1234.game.app.server.server0002.net;

import static pama1234.game.app.server.server0002.net.NetUtil.catchException;
import static pama1234.game.app.server.server0002.net.NetUtil.debug;
import static pama1234.game.app.server.server0002.net.NetUtil.protocolVersion;
import static pama1234.game.app.server.server0002.net.NetUtil.readNBytes;
import static pama1234.game.app.server.server0002.net.NetUtil.writeClientHeader;
import static pama1234.game.app.server.server0002.net.State0002.ClientState.ClientAuthentication;

import java.io.IOException;
import java.net.SocketException;
import java.util.Arrays;

import pama1234.data.ByteUtil;
import pama1234.game.app.server.server0001.game.net.data.Client0001Core;
import pama1234.game.app.server.server0002.net.State0002.ClientState;
import pama1234.game.app.server.server0002.net.State0002.ServerState;

public class ClientCore{
  public static class ClientRead extends Thread{
    public Client0001Core p;
    public SocketData s;
    public ClientRead(Client0001Core p,SocketData dataSocket) {
      super("ClientRead "+dataSocket.s.getRemoteAddress());
      this.p=p;
      this.s=dataSocket;
    }
    @Override
    public void run() {
      byte[] data=new byte[128];
      while(!s.stop) {
        try {
          doF(data,
            s.serverState=ServerState.intToState(ByteUtil.byteToInt(readNBytes(s,data,0,4),0)),
            ByteUtil.byteToInt(readNBytes(s,data,0,4),0));
        }catch(SocketException e1) {
          catchException(e1,s);
        }catch(IOException e2) {
          catchException(e2,s);
        }
      }
    }
    public void doF(byte[] inData,ServerState state,int readSize) throws IOException {
      if(debug) System.out.println("ClientRead state="+state+" readSize="+readSize);
      switch(state) {
        case ServerAuthentication: {
          if(readSize!=4) throw new RuntimeException("state ServerAuthentication readSize!=4 "+readSize);//TODO
          readNBytes(s,inData,0,readSize);
          s.clientState=ClientAuthentication;
        }
          break;
        case ServerDataTransfer: {
          s.clientState=ClientState.ClientDataTransfer;
          if(readSize!=p.cellData.length) throw new RuntimeException("state DataTransfer readSize!=p.cellData.length "+readSize+" "+p.cellData.length+" "+Arrays.toString(readNBytes(s,inData,0,inData.length)));//TODO
          for(int i=0;i<readSize;i++) {
            readNBytes(s,inData,0,4*5);
            p.cellData[i].id=ByteUtil.byteToInt(inData,0);
            p.cellData[i].type=ByteUtil.byteToInt(inData,4);
            p.cellData[i].x=ByteUtil.byteToFloat(inData,8);
            p.cellData[i].y=ByteUtil.byteToFloat(inData,12);
            p.cellData[i].z=ByteUtil.byteToFloat(inData,16);
          }
        }
          break;
        case ServerException: {}
          break;
        case ServerFinishedProcessing: {}
          break;
        case ServerProcessing: {}
          break;
        case ServerProtocolVersion: {
          byte[] nameBytes=new byte[readSize];
          readNBytes(s,nameBytes,0,readSize);
          String version=new String(nameBytes);
          if(!version.equals(protocolVersion)) throw new RuntimeException("!version.equals(protocolVersion)"+version+" "+protocolVersion);
          s.clientState=ClientState.ClientAuthentication;
        }
          break;
        case ServerSendSceneChange: {}
          break;
        case ServerSendStringMessage: {}
          break;
        default:
          throw new RuntimeException("state err="+state);
      }
    }
    public void dispose() {
      s.stop=true;
    }
  }
  public static class ClientWrite extends Thread{
    public Client0001Core p;
    public SocketData s;
    public ClientWrite(Client0001Core p,SocketData dataSocket) {
      super("ClientWrite "+dataSocket.s.getRemoteAddress());
      this.p=p;
      this.s=dataSocket;
    }
    @Override
    public void run() {
      byte[] data=new byte[128];
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
      switch(s.clientState) {
        case ClientAuthentication: {
          byte[] nameBytes=s.name().getBytes();
          writeClientHeader(s,outData,nameBytes.length);
          s.o.write(nameBytes);
          s.o.flush();
          p.sleep(1000);
        }
          break;
        case ClientDataTransfer: {
          writeClientHeader(s,outData,12);
          ByteUtil.floatToByte(p.yourself.x(),outData,0);
          ByteUtil.floatToByte(p.yourself.y(),outData,4);
          ByteUtil.floatToByte(p.yourself.z(),outData,8);
          s.o.write(outData,0,12);
          s.o.flush();
          p.sleep(40);
        }
          break;
        case ClientException: {}
          break;
        case ClientFinishedProcessing: {}
          break;
        case ClientProcessing: {}
          break;
        case ClientProtocolVersion: {
          byte[] nameBytes=protocolVersion.getBytes();
          writeClientHeader(s,outData,nameBytes.length);
          s.o.write(nameBytes);
          s.o.flush();
          p.sleep(1000);
        }
          break;
        case ClientSendStringMessage: {}
          break;
        default:
          throw new RuntimeException("state err="+s.serverState);
      }
    }
    public void dispose() {
      s.stop=true;
    }
  }
}