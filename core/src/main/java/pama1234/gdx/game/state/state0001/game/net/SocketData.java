package pama1234.gdx.game.state.state0001.game.net;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import pama1234.gdx.game.state.state0001.game.net.NetState.ClientState;
import pama1234.gdx.game.state.state0001.game.net.NetState.SceneState;
import pama1234.gdx.game.state.state0001.game.net.NetState.ServerState;
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
    @Override
    public int hashCode() {
      final int prime=31;
      int result=1;
      result=prime*result+((name==null)?0:name.hashCode());
      return result;
    }
    @Override
    public boolean equals(Object obj) {
      if(this==obj) return true;
      if(obj==null) return false;
      if(getClass()!=obj.getClass()) return false;
      Token other=(Token)obj;
      if(name==null) {
        if(other.name!=null) return false;
      }else if(!name.equals(other.name)) return false;
      return true;
    }
  }
}