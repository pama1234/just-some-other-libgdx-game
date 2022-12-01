package pama1234.gdx.game.app.server.game;

import pama1234.gdx.game.app.server.game.net.PlayerInfo3D;
import pama1234.gdx.util.entity.ServerPoint3DEntity;
import pama1234.math.physics.PathPoint3D;

public class ServerPlayer3D extends ServerPoint3DEntity<PathPoint3D>{
  public String name;
  public PlayerInfo3D info;
  // public ServerPlayer3D(ServerCore p,String name,PathPoint3D in) {
  public ServerPlayer3D(String name,PathPoint3D in) {//TODO
    super(in);
    this.name=name;
  }
  // public ServerPlayer3D(ServerCore p,String name,float x,float y,float z) {
  public ServerPlayer3D(String name,float x,float y,float z) {
    this(name,new PathPoint3D(x,y,z));
  }
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
