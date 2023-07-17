package pama1234.game.app.server.server0001.game.net;

import java.io.IOException;

import pama1234.data.ByteUtil;
import pama1234.game.app.server.server0001.game.net.state.ClientState;
import pama1234.game.app.server.server0001.game.net.state.ServerState;

public class NetUtil{
  // public static final boolean debug=true;
  public static final boolean debug=false;
  public static String protocolVersion="0.1";
  public static byte[] readNBytes(SocketData0001 e,byte[] out,int offset,int size) throws IOException {
    int ti=0;
    while(ti==0) {
      if(e.stop) return out;
      ti=e.i.readNBytes(out,offset,size);
    }
    if(ti!=size) throw new RuntimeException("ti!=size "+ti+" "+size);
    return out;
  }
  public static void writeClientHeader(SocketData0001 e,byte[] outData,ClientState state,int size) throws IOException {
    e.o.write(ByteUtil.intToByte(state.ordinal(),outData,0),0,4);
    e.o.write(ByteUtil.intToByte(size,outData,0),0,4);
    e.o.flush();
  }
  public static void writeClientHeader(SocketData0001 e,byte[] outData,int size) throws IOException {
    writeClientHeader(e,outData,e.clientState,size);
  }
  public static void writeServerHeader(SocketData0001 e,byte[] outData,ServerState state,int size) throws IOException {
    e.o.write(ByteUtil.intToByte(state.ordinal(),outData,0),0,4);
    e.o.write(ByteUtil.intToByte(size,outData,0),0,4);
    e.o.flush();
  }
  public static void writeServerHeader(SocketData0001 e,byte[] outData,int size) throws IOException {
    writeServerHeader(e,outData,e.serverState,size);
  }
  public static void catchException(Exception e,SocketData0001 s) {
    // e.printStackTrace();
    System.out.println(e);
    s.clientState=ClientState.ClientException;
    s.serverState=ServerState.ServerException;
    s.stop=true;
  }
}