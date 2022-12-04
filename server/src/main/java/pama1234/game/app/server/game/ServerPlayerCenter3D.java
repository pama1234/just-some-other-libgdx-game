package pama1234.game.app.server.game;

import java.util.HashMap;

import pama1234.util.wrapper.ServerEntityCenter;

public class ServerPlayerCenter3D extends ServerEntityCenter<ServerPlayer3D>{
  // public HashMap<TokenData,ClientPlayer3D> hashMap=new HashMap<>();
  public HashMap<String,ServerPlayer3D> hashMap=new HashMap<>();//TODO
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
      if(hashMap.get(e.name())==null) hashMap.put(e.name(),e);
      else remove.add(e);
    }
    super.refresh();//TODO
    for(ServerPlayer3D e:remove) hashMap.remove(e.name());
    // for(ClientPlayer3D e:add) hashMap.put(e.token,e);
    // for(ClientPlayer3D e:remove) hashMap.remove(e.token);
    super.refresh();
    if(hashMap.size()!=list.size()) throw new RuntimeException("hashMap.size()!=list.size() "+hashMap.size()+" "+list.size());
  }
  // public static class TokenData{
  //   public String name;
  //   public TokenData(String name) {
  //     this.name=name;
  //   }
  // }
  public void remove(String name) {
    ServerPlayer3D tp=hashMap.get(name);
    if(tp!=null) {
      hashMap.remove(name);
      remove.add(tp);
    }else System.out.println("ClientPlayerCenter3D tp==null name="+name);
  }
}
