package pama1234.gdx.game.app.app0002;

import pama1234.gdx.game.app.app0001.Screen0001;
import pama1234.gdx.game.ui.generator.UiGenerator;
import pama1234.gdx.game.ui.util.TextButton;
import pama1234.gdx.game.util.ScreenCenter;
import pama1234.gdx.launcher.MainApp;
import pama1234.gdx.util.app.ScreenCore2D;
import pama1234.gdx.util.app.UtilScreen;
import pama1234.gdx.util.listener.StateChanger;

public class MainMenu extends ScreenCore2D implements StateChanger<UtilScreen>{
  public MainApp root;
  public UtilScreen state;
  public ScreenCenter sCenter;
  public TextButton<?>[] buttons;
  public UtilScreen realGame,screen0001;
  public MainMenu(MainApp root) {
    this.root=root;
  }
  @Override
  public void setup() {
    sCenter=new ScreenCenter();
    sCenter.add.add(realGame=new RealGame(this));
    sCenter.add.add(screen0001=new Screen0001(this));
    buttons=UiGenerator.genButtons_0002(this);
  }
  @Override
  public void update() {}
  @Override
  public void display() {}
  @Override
  public void displayWithCam() {}
  @Override
  public void frameResized() {}
  public UtilScreen state(UtilScreen in) {
    UtilScreen out=state;
    state=in;
    if(out!=null) {
      sCenter.remove.add(out);
      out.pause();
    }
    if(in!=null) {
      in.resume();
      sCenter.add.add(in);
    }
    return out;
  }
}
