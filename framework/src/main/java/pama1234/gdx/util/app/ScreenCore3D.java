package pama1234.gdx.util.app;

import pama1234.gdx.game.ui.element.Button;
import pama1234.gdx.game.ui.element.TextButton;
import pama1234.gdx.util.cam.CameraMisc;

public abstract class ScreenCore3D extends UtilScreen3D{
  public TextButton<?>[] androidCam3DButtons() {
    return CameraMisc.cam3dButtons(this);
  }
  public TextButton<?>[] addAndroidCam3DButtons() {
    var buttons=androidCam3DButtons();
    for(Button<?> e:buttons) centerScreen.add.add(e);
    return buttons;
  }
}