package pama1234.game.app.server.game.net.io;

import static pama1234.game.app.server.game.net.NetUtil.catchException;
import static pama1234.game.app.server.game.net.NetUtil.debug;
import static pama1234.game.app.server.game.net.NetUtil.readNBytes;
import static pama1234.game.app.server.game.net.state.ClientState.ClientAuthentication;

import java.io.IOException;
import java.net.SocketException;
import java.util.Arrays;

import pama1234.data.ByteUtil;
import pama1234.game.app.server.game.net.SocketData;
import pama1234.game.app.server.game.net.data.ClientCore;
import pama1234.game.app.server.game.net.state.ClientState;
import pama1234.game.app.server.game.net.state.ServerState;

public class ClientRead extends Thread{
  public ClientCore p;
  public SocketData s;
  public ClientRead(ClientCore p,SocketData dataSocket) {
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
      default:
        throw new RuntimeException("state err="+state);
    }
  }
  public void dispose() {
    s.stop=true;
  }
}