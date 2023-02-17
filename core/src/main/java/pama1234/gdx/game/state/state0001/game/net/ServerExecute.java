package pama1234.gdx.game.state.state0001.game.net;

import static pama1234.gdx.game.state.state0001.game.net.NetUtil.protocolVersion;
import static pama1234.gdx.game.state.state0001.game.net.NetUtil.readNBytes;
import static pama1234.gdx.game.state.state0001.game.net.NetUtil.writeServerHeader;

import java.io.IOException;

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
    return new ServerReadF[] {(p,s,inData,readSize)-> {},null,null,null,(p,s,inData,readSize)-> {
      byte[] nameBytes=new byte[readSize];
      readNBytes(s,nameBytes,0,readSize);
      String version=new String(nameBytes);
      if(!version.equals(protocolVersion)) throw new RuntimeException("!version.equals(protocolVersion)"+version+" "+protocolVersion);
      s.serverState=ServerState.ServerAuthentication;
    }};
  }
  public static ServerWriteF[] generateWriteF() {
    return new ServerWriteF[] {null,(p,s,outData)-> {
      // p.println(1,p.group.size);
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
