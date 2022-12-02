package pama1234.util.net;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

public class ServerSocketData{
  public ServerSocket server;
  public ServerSocketData(ServerInfo info) {
    try {//TODO try-catch???
      server=new ServerSocket(info.port,50,InetAddress.getByName(info.addr));//TODO
    }catch(UnknownHostException e) {
      e.printStackTrace();
    }catch(IOException e) {
      e.printStackTrace();
    }
  }
  public Socket accept() {
    try {
      return server.accept();
    }catch(IOException e) {
      e.printStackTrace();
      throw new RuntimeException(e);
    }
  }
}
