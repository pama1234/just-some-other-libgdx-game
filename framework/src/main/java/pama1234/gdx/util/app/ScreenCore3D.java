package pama1234.gdx.util.app;

import pama1234.gdx.game.ui.util.Button;
import pama1234.gdx.util.cam.CameraMisc;

public abstract class ScreenCore3D extends UtilScreen3D{
  // public NetAddressInfo dataServerInfo;
  public void addAndroidCam3DButtons() {
    buttons=CameraMisc.cam3dButtons(this);
    for(Button<?> e:buttons) centerScreen.add.add(e);
  }
}