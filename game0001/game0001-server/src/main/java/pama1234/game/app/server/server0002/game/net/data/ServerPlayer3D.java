package pama1234.game.app.server.server0002.game.net.data;

import pama1234.math.gdx.temp.ServerFrustum;
import pama1234.math.physics.PathPoint3D;
import pama1234.util.entity.ServerPoint3DEntity;

public class ServerPlayer3D extends ServerPoint3DEntity<PathPoint3D>{
  // public String name;
  public PlayerInfo3D info;
  public ServerFrustum viewFrustum;
  // public ServerPlayer3D(ServerCore p,String name,PathPoint3D in) {
  public ServerPlayer3D(String name,PathPoint3D in) {//TODO
    super(in);
    info=new PlayerInfo3D(name,in);
  }
  // public ServerPlayer3D(ServerCore p,String name,float x,float y,float z) {
  public ServerPlayer3D(String name,float x,float y,float z) {
    this(name,new PathPoint3D(x,y,z));
  }
  @Override
  public void update() {
    // viewFrustum.
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
  public String name() {
    return info.name;
  }
}
