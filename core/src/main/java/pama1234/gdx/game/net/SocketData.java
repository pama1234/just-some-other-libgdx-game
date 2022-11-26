package pama1234.gdx.game.net;

import java.io.InputStream;
import java.io.OutputStream;

import com.badlogic.gdx.net.Socket;

import pama1234.gdx.game.net.NetUtil.NetState;

public class SocketData{
  // public int authCooling;//TODO server only
  //---
  public NetState state=NetState.Authentication;//TODO why avoiding state 0???
  public String name;//TODO replace with FullToken data class
  //---
  public Socket s;
  public InputStream i;
  public OutputStream o;
  public SocketData(String name,Socket s) {
    this.name=name;
    this.s=s;
    i=s.getInputStream();
    o=s.getOutputStream();
  }
  public SocketData(Socket s) {
    this.s=s;
    i=s.getInputStream();
    o=s.getOutputStream();
  }
}