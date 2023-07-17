package pama1234.util.net;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

public class ServerSocketData{
  public ServerSocket server;
  public ServerSocketData(NetAddressInfo info) {
    init(info);
  }
  public void init(NetAddressInfo info) {
    try {
      initWithException(info);
    }catch(UnknownHostException e) {
      e.printStackTrace();
    }catch(IOException e) {
      e.printStackTrace();
    }
  }
  public void initWithException(NetAddressInfo info) throws IOException,UnknownHostException {
    server=new ServerSocket(info.port,50,InetAddress.getByName(info.addr));//TODO
  }
  public Socket accept() throws IOException {
    return server.accept();
  }
  public void close() {
    try {
      server.close();
    }catch(IOException e) {
      e.printStackTrace();
    }
  }
}
