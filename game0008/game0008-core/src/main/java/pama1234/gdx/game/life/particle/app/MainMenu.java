package pama1234.gdx.game.life.particle.app;

import pama1234.gdx.game.life.particle.ui.generator.UiGenerator;
import pama1234.gdx.game.life.particle.util.ScreenCenter;
import pama1234.gdx.game.ui.element.TextButton;
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
    noStroke();
    sCenter=new ScreenCenter();
    sCenter.list.add(realGame=new Screen0045(this));
    // sCenter.list.add(screen0001=new Screen0033(this));
    for(UtilScreen e:sCenter.list) e.show();
    buttons=UiGenerator.genButtons_0002(this);
    for(TextButton<?> e:buttons) centerScreen.add.add(e);
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
