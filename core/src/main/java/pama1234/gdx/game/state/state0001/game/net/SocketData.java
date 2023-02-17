package pama1234.gdx.game.state.state0001.game.net;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import pama1234.gdx.game.state.state0001.game.net.ClientExecute.ClientState;
import pama1234.gdx.game.state.state0001.game.net.NetState.SceneState;
import pama1234.gdx.game.state.state0001.game.net.ServerExecute.ServerState;
import pama1234.util.net.SocketInterface;

public class SocketData{
  public boolean stop;
  public ClientState clientState=ClientState.ClientProtocolVersion;
  public ServerState serverState=ServerState.ServerProtocolVersion;
  public SceneState sceneState=SceneState.TestWorld;
  //---
  public SocketInterface s;
  public InputStream i;
  public OutputStream o;
  //---
  public String name;
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
    return name;
  }
  public void name(String in) {
    name=in;
  }
}