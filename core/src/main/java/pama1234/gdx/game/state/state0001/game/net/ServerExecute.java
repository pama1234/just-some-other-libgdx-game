package pama1234.gdx.game.state.state0001.game.net;

import static pama1234.gdx.game.state.state0001.game.net.NetState0002.ServerState.ServerDataTransfer;
import static pama1234.gdx.game.state.state0001.game.net.NetUtil.protocolVersion;
import static pama1234.gdx.game.state.state0001.game.net.NetUtil.readNBytes;

import java.io.IOException;

import pama1234.data.ByteUtil;
import pama1234.game.app.server.server0001.game.ServerPlayer3D;
import pama1234.gdx.game.state.state0001.game.net.NetState0002.ClientState;
import pama1234.gdx.game.state.state0001.game.net.NetState0002.ServerState;
import pama1234.gdx.game.state.state0001.game.net.SocketData.Token;

public class ServerExecute{
  @FunctionalInterface
  public interface ServerReadF{
    public void execute(ServerData p,SocketData s,byte[] inData,int readSize) throws IOException;
  }
  public static ServerReadF[] generateReadF() {
    return new ServerReadF[] {(p,s,inData,readSize)-> {
      byte[] nameBytes=new byte[readSize];
      readNBytes(s,nameBytes,0,readSize);
      s.token.name=new String(nameBytes);
      s.serverState=ServerDataTransfer;
      p.playerCenter.add.add(new ServerPlayer3D(s.name(),0,0,0));//TODO ?
      p.playerCenter.refresh();
      System.out.println("Auth "+s.name());
    },(p,s,inData,readSize)-> {
      readNBytes(s,inData,0,4*3);
      ServerPlayer3D tp=p.playerCenter.hashMap.get(new Token(s.name()));
      if(tp==null) {
        s.clientState=ClientState.ClientAuthentication;
        return;
      }
      tp.point.des.set(
        ByteUtil.byteToFloat(inData,0),
        ByteUtil.byteToFloat(inData,4),
        ByteUtil.byteToFloat(inData,8));
    },null,null,null,(p,s,inData,readSize)-> {
      byte[] nameBytes=new byte[readSize];
      readNBytes(s,nameBytes,0,readSize);
      String version=new String(nameBytes);
      if(!version.equals(protocolVersion)) throw new RuntimeException("!version.equals(protocolVersion)"+version+" "+protocolVersion);
      s.serverState=ServerState.ServerAuthentication;
    }};
  }
}
