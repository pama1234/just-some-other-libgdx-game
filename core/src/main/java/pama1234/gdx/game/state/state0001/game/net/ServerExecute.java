package pama1234.gdx.game.state.state0001.game.net;

import static pama1234.gdx.game.state.state0001.game.net.NetState.ServerState.ServerDataTransfer;
import static pama1234.gdx.game.state.state0001.game.net.NetUtil.protocolVersion;
import static pama1234.gdx.game.state.state0001.game.net.NetUtil.readNBytes;
import static pama1234.gdx.game.state.state0001.game.net.NetUtil.writeServerHeader;

import java.io.IOException;

import pama1234.data.ByteUtil;
import pama1234.game.app.server.server0001.game.ServerPlayer3D;
import pama1234.gdx.game.state.state0001.game.net.NetState.ClientState;
import pama1234.gdx.game.state.state0001.game.net.NetState.ServerState;

public class ServerExecute{
  @FunctionalInterface
  public interface ServerReadF{
    public void execute(ServerData p,SocketData s,byte[] inData,int readSize) throws IOException;
  }
  @FunctionalInterface
  public interface ServerWriteF{
    public void execute(ServerData p,SocketData s,byte[] outData) throws IOException;
  }
  public static ServerReadF[] generateReadF() {
    return new ServerReadF[] {(p,s,inData,readSize)-> {
      byte[] nameBytes=new byte[readSize];
      readNBytes(s,nameBytes,0,readSize);
      s.name(new String(nameBytes));
      s.serverState=ServerDataTransfer;
      p.playerCenter.add.add(new ServerPlayer3D(s.name(),0,0,0));//TODO ?
      p.playerCenter.refresh();
      System.out.println("Auth "+s.name());
    },(p,s,inData,readSize)-> {
      readNBytes(s,inData,0,4*3);
      ServerPlayer3D tp=p.playerCenter.hashMap.get(s.name());
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
  public static ServerWriteF[] generateWriteF() {
    return new ServerWriteF[] {null,(p,s,outData)-> {
      writeServerHeader(s,outData,p.group.size);
      // p.println(1,p.group.size);
      for(int i=0;i<p.group.size;i++) {
        ByteUtil.intToByte(i,outData,0);
        ByteUtil.intToByte(p.group.type[i],outData,4);
        ByteUtil.floatToByte(p.group.x(i),outData,8);
        ByteUtil.floatToByte(p.group.y(i),outData,12);
        ByteUtil.floatToByte(p.group.z(i),outData,16);
        s.o.write(outData);
      }
      s.o.flush();
    },null,null,null,(p,s,outData)-> {
      byte[] nameBytes=protocolVersion.getBytes();
      writeServerHeader(s,outData,nameBytes.length);
      s.o.write(nameBytes);
      s.o.flush();
      p.sleep(1000);
    }};
  }
}
