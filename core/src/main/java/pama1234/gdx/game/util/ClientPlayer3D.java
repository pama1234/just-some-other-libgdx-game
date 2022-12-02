package pama1234.gdx.game.util;

import pama1234.game.app.server.game.ServerPlayer3D;
import pama1234.gdx.util.app.UtilScreen;
import pama1234.gdx.util.entity.Entity;

public class ClientPlayer3D extends Entity<UtilScreen>{
  public ServerPlayer3D data;
  public ClientPlayer3D(UtilScreen p,ServerPlayer3D data) {
    super(p);
    this.data=data;
  }
  // public TokenData token;
  // public String name;
  // public PlayerInfo3D info;
  // public ClientPlayer3D(UtilScreen p,String name,PathPoint3D in) {
  //   super(p,in);
  //   this.name=name;
  // }
  // public ClientPlayer3D(UtilScreen p,String name,float x,float y,float z) {
  //   this(p,name,new PathPoint3D(x,y,z));
  // }
  @Override
  public void display() {}
  @Override
  public void dispose() {}
  @Override
  public void init() {}
  @Override
  public void pause() {}
  @Override
  public void resume() {}
}
