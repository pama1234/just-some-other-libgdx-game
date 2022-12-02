package pama1234.util.net;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class SocketWrapper implements SocketInterface{
  public Socket data;
  public SocketWrapper(Socket data) {
    this.data=data;
  }
  @Override
  public boolean isConnected() {
    return data.isConnected();
  }
  @Override
  public InputStream getInputStream() {
    try {
      return data.getInputStream();
    }catch(IOException e) {
      e.printStackTrace();
      throw new RuntimeException(e);
    }
  }
  @Override
  public OutputStream getOutputStream() {
    try {
      return data.getOutputStream();
    }catch(IOException e) {
      e.printStackTrace();
      throw new RuntimeException(e);
    }
  }
  @Override
  public String getRemoteAddress() {
    return data.getInetAddress().getHostAddress();
  }
  @Override
  public void dispose() {//TODO
  }
}
