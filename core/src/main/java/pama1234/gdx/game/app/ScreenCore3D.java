package pama1234.gdx.game.app;

import pama1234.gdx.game.ui.Button;
import pama1234.gdx.util.app.UtilScreen3D;
import pama1234.util.net.ServerInfo;

public abstract class ScreenCore3D extends UtilScreen3D{
  public ServerInfo dataServerInfo;
  public float multDist=1;
  public Button[] buttons;
  public int bu;
  public boolean fullSettings;
  public int getButtonUnitLength() {
    return bu;
  }
}
