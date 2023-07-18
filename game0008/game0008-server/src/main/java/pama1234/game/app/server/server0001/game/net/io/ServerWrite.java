package pama1234.game.app.server.server0001.game.net.io;

import static pama1234.game.app.server.server0001.game.net.NetUtil.debug;
import static pama1234.game.app.server.server0001.game.net.NetUtil.protocolVersion;
import static pama1234.game.app.server.server0001.game.net.NetUtil.writeServerHeader;

import java.io.IOException;

import pama1234.data.ByteUtil;
import pama1234.game.app.server.server0001.game.net.SocketData0001;
import pama1234.game.app.server.server0001.game.net.data.Server0001Core;

public class ServerWrite extends Thread{
  public Server0001Core p;
  public SocketData0001 s;
  public ServerWrite(Server0001Core p,SocketData0001 dataSocket) {
    super("ServerWrite "+dataSocket.s.getRemoteAddress());
    this.p=p;
    this.s=dataSocket;
  }
  @Override
  public void run() {
    // byte[] data=new byte[20];
    // while(!s.stop) {
    //   synchronized(p.group) {
    //     try {
    //       doF(s,data);
    //     }catch(SocketException e1) {
    //       catchException(e1,s);
    //     }catch(IOException e2) {
    //       catchException(e2,s);
    //     }
    //   }
    // }
    // p.serverWritePool.remove.add(this);
  }
  public void doF(SocketData0001 e,byte[] outData) throws IOException {
    if(debug) System.out.println("ServerWrite state="+e.serverState);
    // if(e.state==1)
    switch(e.serverState) {
      case ServerAuthentication: {
        writeServerHeader(e,outData,4);
        e.o.write(ByteUtil.intToByte(1234,outData,0),0,4);
        e.o.flush();
      }
        break;
      case ServerDataTransfer: {
        // writeServerHeader(e,outData,p.group.size);
        // // p.println(1,p.group.size);
        // for(int i=0;i<p.group.size;i++) {
        //   ByteUtil.intToByte(i,outData,0);
        //   ByteUtil.intToByte(p.group.type[i],outData,4);
        //   ByteUtil.floatToByte(p.group.x(i),outData,8);
        //   ByteUtil.floatToByte(p.group.y(i),outData,12);
        //   ByteUtil.floatToByte(p.group.z(i),outData,16);
        //   e.o.write(outData);
        // }
        // e.o.flush();
      }
        break;
      case ServerException: {}
        break;
      case ServerFinishedProcessing: {}
        break;
      case ServerProcessing: {}
        break;
      case ServerProtocolVersion: {
        byte[] nameBytes=protocolVersion.getBytes();
        writeServerHeader(s,outData,nameBytes.length);
        s.o.write(nameBytes);
        s.o.flush();
        p.sleep(1000);
      }
        break;
      case ServerSendSceneChange: {}
        break;
      case ServerSendStringMessage: {}
        break;
      default:
        throw new RuntimeException("state err="+e.clientState);
    }
  }
  public void dispose() {
    s.stop=true;
  }
}