package pama1234.game.app.server;

import java.io.IOException;

import pama1234.game.app.server.server0002.net.NetState0002.ClientState;
import pama1234.game.app.server.server0002.net.NetState0002.SceneState;
import pama1234.game.app.server.server0002.net.NetState0002.ServerState;
import pama1234.util.net.SocketData;
import pama1234.util.net.SocketInterface;

public class SocketData0001 extends SocketData{
  public boolean stop;
  public ClientState clientState=ClientState.ClientProtocolVersion;
  public ServerState serverState=ServerState.ServerProtocolVersion;
  public SceneState sceneState=SceneState.JustParticleSystem;
  public Token token;
  public SocketData0001(Token token,SocketInterface s) {
    super(s);
    this.token=token;
  }
  public SocketData0001(SocketInterface s) {
    super(s);
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