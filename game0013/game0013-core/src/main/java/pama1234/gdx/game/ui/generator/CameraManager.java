package pama1234.gdx.game.ui.generator;

import pama1234.gdx.game.app.app0002.Screen0052;

public class CameraManager{
  public static void gameEnter(Screen0052 p) {
    p.cam.point.f=0.1f;
    // p.activeActrl(true);
    p.cam2d.scale.des=2;
  }
  public static void gameExit(Screen0052 p) {
    p.cam.point.f=0.2f;
    // p.activeActrl(false);
    // p.setupCamera();
  }
}
