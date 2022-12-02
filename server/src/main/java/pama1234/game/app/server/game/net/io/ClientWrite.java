package pama1234.game.app.server.game.net.io;

import static pama1234.game.app.server.game.net.NetUtil.catchException;
import static pama1234.game.app.server.game.net.NetUtil.debug;
import static pama1234.game.app.server.game.net.NetUtil.writeClientHeader;

import java.io.IOException;
import java.net.SocketException;

import pama1234.data.ByteUtil;
import pama1234.game.app.server.game.net.SocketData;
import pama1234.game.app.server.game.net.data.ClientCore;

public class ClientWrite extends Thread{
  public ClientCore p;
  public SocketData s;
  public ClientWrite(ClientCore p,SocketData dataSocket) {
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
        byte[] nameBytes=s.name.getBytes();
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
      default:
        throw new RuntimeException("state err="+s.serverState);
    }
  }
  public void dispose() {
    s.stop=true;
  }
}