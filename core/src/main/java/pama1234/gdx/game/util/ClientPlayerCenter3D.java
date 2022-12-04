package pama1234.gdx.game.util;

import java.util.HashMap;

import pama1234.gdx.util.app.UtilScreen;
import pama1234.gdx.util.wrapper.EntityCenter;

public class ClientPlayerCenter3D extends EntityCenter<ClientPlayer3D>{
  // public HashMap<TokenData,ClientPlayer3D> hashMap=new HashMap<>();
  public HashMap<String,ClientPlayer3D> hashMap=new HashMap<>();//TODO
  public ClientPlayerCenter3D(UtilScreen p) {
    super(p);
  }
  public ClientPlayerCenter3D(UtilScreen p,ClientPlayer3D in) {
    super(p,in);
  }
  public ClientPlayerCenter3D(UtilScreen p,ClientPlayer3D[] in) {
    super(p,in);
  }
  @Override
  public void refresh() {
    for(ClientPlayer3D e:add) {
      if(hashMap.get(e.data.name())==null) hashMap.put(e.data.name(),e);
      else remove.add(e);
    }
    super.refresh();//TODO
    for(ClientPlayer3D e:remove) hashMap.remove(e.data.name());
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
    ClientPlayer3D tp=hashMap.get(name);
    if(tp!=null) {
      hashMap.remove(name);
      remove.add(tp);
    }else System.out.println("ClientPlayerCenter3D tp==null name="+name);
  }
}
