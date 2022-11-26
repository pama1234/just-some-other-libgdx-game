package pama1234.gdx.game.util;

import pama1234.gdx.util.app.UtilScreen;
import pama1234.math.physics.PathPoint3D;

public class ControllerClientPlayer3D extends ClientPlayer3D{
  public ControllerClientPlayer3D(UtilScreen p,String name,PathPoint3D in) {
    super(p,name,in);
  }
  public ControllerClientPlayer3D(UtilScreen p,String name,float x,float y,float z) {
    super(p,name,x,y,z);
  }
  @Override
  public void update() {
    super.update();
    // p.cam.point.set(x(),y(),z());
  }
  @Override
  public void display() {}
}
