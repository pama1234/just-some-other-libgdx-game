package pama1234.game.app.server.server0001.game;

import java.util.HashMap;

import pama1234.game.app.server.server0001.game.net.SocketData0001.Token;
import pama1234.util.wrapper.ServerEntityCenter;

public class ServerPlayerCenter3D extends ServerEntityCenter<ServerPlayer3D>{
  // public HashMap<TokenData,ClientPlayer3D> hashMap=new HashMap<>();
  public HashMap<Token,ServerPlayer3D> hashMap=new HashMap<>();//TODO
  public ServerPlayerCenter3D() {
    super();
  }
  public ServerPlayerCenter3D(ServerPlayer3D in) {
    super(in);
  }
  public ServerPlayerCenter3D(ServerPlayer3D[] in) {
    super(in);
  }
  @Override
  public void refresh() {
    for(ServerPlayer3D e:add) {
      Token key=new Token(e.name());//TODO
      if(hashMap.get(key)==null) {
        hashMap.put(key,e);
      }else remove.add(e);
    }
    super.refresh();//TODO
    for(ServerPlayer3D e:remove) hashMap.values().removeIf(i->e.name().equals(i.name()));//TODO
    // for(ClientPlayer3D e:add) hashMap.put(e.token,e);
    // for(ClientPlayer3D e:remove) hashMap.remove(e.token);
    super.refresh();
    if(hashMap.size()!=list.size()) throw new RuntimeException("hashMap.size()!=list.size() "+hashMap.size()+" "+list.size());
  }
  public void remove(String name) {
    Token key=new Token(name);
    ServerPlayer3D tp=hashMap.get(key);
    if(tp!=null) {
      hashMap.remove(key);
      remove.add(tp);
    }else System.out.println("ClientPlayerCenter3D tp==null name="+name);
  }
}
