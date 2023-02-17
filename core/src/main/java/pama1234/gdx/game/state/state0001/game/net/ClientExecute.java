package pama1234.gdx.game.state.state0001.game.net;

import static pama1234.gdx.game.state.state0001.game.net.NetState.ClientState.ClientAuthentication;
import static pama1234.gdx.game.state.state0001.game.net.NetUtil.protocolVersion;
import static pama1234.gdx.game.state.state0001.game.net.NetUtil.readNBytes;
import static pama1234.gdx.game.state.state0001.game.net.NetUtil.writeClientHeader;

import java.io.IOException;
import java.util.Arrays;

import pama1234.data.ByteUtil;
import pama1234.gdx.game.state.state0001.game.net.NetState.ClientState;

public class ClientExecute{
  @FunctionalInterface
  public interface ClientReadF{
    public void execute(ClientData p,SocketData s,byte[] inData,int stateInt,int readSize) throws IOException;
  }
  @FunctionalInterface
  public interface ClientWriteF{
    public void execute(ClientData p,SocketData s,byte[] outData) throws IOException;
  }
  public static ClientReadF[] generateReadF() {
    return new ClientReadF[] {(p,s,inData,stateInt,readSize)-> {
      if(readSize!=4) throw new RuntimeException("state ServerAuthentication readSize!=4 "+readSize);//TODO
      readNBytes(s,inData,0,readSize);
      s.clientState=ClientAuthentication;
    },
      (p,s,inData,stateInt,readSize)-> {
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
      },null,null,null,
      (p,s,inData,stateInt,readSize)-> {
        byte[] nameBytes=new byte[readSize];
        readNBytes(s,nameBytes,0,readSize);
        String version=new String(nameBytes);
        if(!version.equals(protocolVersion)) throw new RuntimeException("!version.equals(protocolVersion)"+version+" "+protocolVersion);
        s.clientState=ClientState.ClientAuthentication;
      }};
  }
  public static ClientWriteF[] generateWriteF() {
    return new ClientWriteF[] {null,(p,s,outData)-> {
      byte[] nameBytes=s.name().getBytes();
      writeClientHeader(s,outData,nameBytes.length);
      s.o.write(nameBytes);
      s.o.flush();
      p.sleep(1000);
    },(p,s,outData)-> {
      writeClientHeader(s,outData,12);
      ByteUtil.floatToByte(p.yourself.x(),outData,0);
      ByteUtil.floatToByte(p.yourself.y(),outData,4);
      ByteUtil.floatToByte(p.yourself.z(),outData,8);
      s.o.write(outData,0,12);
      s.o.flush();
      p.sleep(40);
    },null,null,null,(p,s,outData)-> {
      byte[] nameBytes=protocolVersion.getBytes();
      writeClientHeader(s,outData,nameBytes.length);
      s.o.write(nameBytes);
      s.o.flush();
      p.sleep(1000);
    }};
  }
}
