package pama1234.gdx.game.app;

import pama1234.gdx.game.ui.Button;
import pama1234.gdx.util.app.UtilScreen3D;
import pama1234.gdx.util.listener.EntityListener;
import pama1234.util.net.ServerInfo;

public abstract class ScreenCore3D extends UtilScreen3D{
  public ServerInfo dataServerInfo;
  public float multDist=1;
  public Button[] buttons;
  public int bu;
  public boolean fullSettings;
  @Override
  public void init() {
    center.list.add(new EntityListener() {
      @Override
      public void frameResized(int w,int h) {
        bu=pus*24;
        // System.out.println("ScreenCore3D.init().new EntityListener() {...}.frameResized()");
        // System.out.println(bu);
      }
    });
  }
  public int getButtonUnitLength() {
    return bu;
  }
  // @Override
  // public void frameResized() {
  //   bu=pus*24;
  // }
}
