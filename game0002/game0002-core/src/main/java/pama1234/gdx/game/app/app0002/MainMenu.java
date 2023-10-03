package pama1234.gdx.game.app.app0002;

import pama1234.gdx.game.ui.util.TextButton;
import pama1234.gdx.game.util.ScreenCenter;
import pama1234.gdx.launcher.MainApp;
import pama1234.gdx.util.app.UtilScreen;
import pama1234.gdx.util.launcher.MainAppBase;
import pama1234.gdx.util.launcher.ScreenMenu;
import pama1234.gdx.util.listener.StateChanger;
import pama1234.util.Annotations.ScreenDescription;

@ScreenDescription("菜单")
public class MainMenu extends ScreenMenu implements StateChanger<UtilScreen>{
  public MainAppBase root;
  public UtilScreen state;
  public ScreenCenter sCenter;
  public TextButton<?>[] buttons;
  public UtilScreen realGame,screen0001;
  {
    root=MainApp.instance;
  }
  @Override
  public void setup() {
    // noStroke();
    sCenter=new ScreenCenter();
    noStroke();
    backgroundColor(243);
    drawCursorWhenGrab=true;
    
    createAndAddMenuButtons();
    // sCenter.list.add(realGame=new RealGame(this));
    // sCenter.list.add(screen0001=new Screen0001(this));
    // for(UtilScreen e:sCenter.list) e.show();
    // buttons=UiGenerator.genButtons_0002(this);
    // for(TextButton<?> e:buttons) centerScreen.add.add(e);
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
  // public UtilScreen state(UtilScreen in) {
  //   UtilScreen out=state;
  //   state=in;
  //   if(out!=null) {
  //     sCenter.remove.add(out);
  //     out.pause();
  //   }
  //   if(in!=null) {
  //     in.resume();
  //     sCenter.add.add(in);
  //   }
  //   return out;
  // }
}
