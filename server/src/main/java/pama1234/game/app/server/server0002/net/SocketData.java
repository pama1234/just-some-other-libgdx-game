package pama1234.game.app.server.server0002.net;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import pama1234.game.app.server.server0002.net.State0002.ClientState;
import pama1234.game.app.server.server0002.net.State0002.SceneState;
import pama1234.game.app.server.server0002.net.State0002.ServerState;
import pama1234.util.net.SocketInterface;

public class SocketData{
  public boolean stop;
  public ClientState clientState=ClientState.ClientProtocolVersion;
  public ServerState serverState=ServerState.ServerProtocolVersion;
  public SceneState sceneState=SceneState.TestWorld;
  public Token token;
  //---
  public SocketInterface s;
  public InputStream i;
  public OutputStream o;
  public SocketData(Token token,SocketInterface s) {
    this.token=token;
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
  public String name() {
    return token.name;
  }
  public static class Token{
    public String name;
    public Token(String name) {
      this.name=name;
    }
  }
}