package pama1234.util.net;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class SocketData{
  public SocketInterface s;
  public InputStream i;
  public OutputStream o;
  public SocketData(SocketInterface s) {
    this.s=s;
    i=s.getInputStream();
    o=s.getOutputStream();
  }
  public void dispose() {
    try {
      i.close();
      o.flush();
      o.close();
    }catch(IOException e) {
      e.printStackTrace();
    }finally {
      s.dispose();
    }
  }
}
