package pama1234.game.app.server.game.net;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import pama1234.game.app.server.game.net.state.ClientState;
import pama1234.game.app.server.game.net.state.ServerState;
import pama1234.util.net.SocketInterface;

public class SocketData{
  // public int authCooling;//TODO server only
  //---
  public boolean stop;
  public ClientState clientState=ClientState.ClientAuthentication;
  public ServerState serverState=ServerState.ServerAuthentication;//TODO why avoiding state 0???
  public String name;//TODO replace with FullToken data class
  //---
  public SocketInterface s;
  public InputStream i;
  public OutputStream o;
  public SocketData(String name,SocketInterface s) {
    this.name=name;
    this.s=s;
    i=s.getInputStream();
    o=s.getOutputStream();
  }
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