package pama1234.gdx.game.net;

import java.io.InputStream;
import java.io.OutputStream;

import com.badlogic.gdx.net.Socket;

public class SocketData{
  public Socket s;
  public InputStream i;
  public OutputStream o;
  public SocketData(Socket s) {
    this.s=s;
    i=s.getInputStream();
    o=s.getOutputStream();
  }
}