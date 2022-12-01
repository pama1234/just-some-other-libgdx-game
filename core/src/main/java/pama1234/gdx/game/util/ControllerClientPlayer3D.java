package pama1234.gdx.game.util;

import pama1234.gdx.game.app.server.game.ServerPlayer3D;
import pama1234.gdx.util.app.UtilScreen;

public class ControllerClientPlayer3D extends ClientPlayer3D{
  public ControllerClientPlayer3D(UtilScreen p,ServerPlayer3D data) {
    super(p,data);
  }
  // public ControllerClientPlayer3D(UtilScreen p,String name,PathPoint3D in) {
  //   super(p,name,in);
  // }
  // public ControllerClientPlayer3D(UtilScreen p,String name,float x,float y,float z) {
  //   super(p,name,x,y,z);
  // }
  @Override
  public void update() {
    // super.update();
    data.update();
    // p.cam.point.set(x(),y(),z());
  }
  @Override
  public void display() {}
}
