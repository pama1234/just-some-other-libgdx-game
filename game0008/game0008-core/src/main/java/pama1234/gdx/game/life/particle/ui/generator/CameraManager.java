package pama1234.gdx.game.life.particle.ui.generator;

import pama1234.gdx.game.life.particle.app.Screen0045;

public class CameraManager{
  public static void gameEnter(Screen0045 p) {
    p.cam.point.f=0.1f;
    p.activeACtrl(true);
    p.cam2d.scale.des=2;
  }
  public static void gameExit(Screen0045 p) {
    p.cam.point.f=0.2f;
    p.activeACtrl(false);
    p.setupCamera();
  }
}
