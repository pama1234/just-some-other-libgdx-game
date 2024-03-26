package pama1234.gdx.game.life.particle.state0004;

import pama1234.gdx.game.life.particle.app.Screen0045;
import pama1234.gdx.util.cam.CameraController2D;

public class Info extends State0004Util.StateEntity0004{

  public Info(Screen0045 p) {
    super(p);
    initContainer();

    // container.centerCamAddAll();
    container.refreshAll();
  }
  @Override
  public void displayCam() {
    p.text("感谢用户 https://space.bilibili.com/247250426 提供美术和音乐支持");
  }
  @Override
  public void from(State0004Util.StateEntity0004 in) {
    p.cam2d.pixelPerfect=CameraController2D.SMOOTH;
    p.cam2d.maxScale=32;
    p.cam2d.point.des.set(0,0);

    super.from(in);
  }
  @Override
  public void to(State0004Util.StateEntity0004 in) {
    p.cam2d.pixelPerfect=CameraController2D.NONE;
    p.cam2d.maxScale=8;

    super.to(in);
  }
}
