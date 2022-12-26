package pama1234.gdx.game.state.state0002;

import static com.badlogic.gdx.Input.Keys.ESCAPE;
import static pama1234.gdx.game.ui.generator.InfoGenerator.info0002;

import pama1234.gdx.game.app.Screen0012;
import pama1234.gdx.game.state.state0002.StateGenerator0002.StateEntity0002;
import pama1234.gdx.game.ui.generator.ButtonGenerator0002;
import pama1234.gdx.game.ui.util.Button;

public class Announcement extends StateEntity0002{
  public Button<?>[] buttons;
  public Announcement(Screen0012 p) {
    super(p);
    buttons=ButtonGenerator0002.genButtons_0001(p);
  }
  @Override
  public void from(State0002 in) {
    p.backgroundColor(0);
    // p.cam.noGrab();
    p.cam.point.set(128,64,0);
    for(Button<?> e:buttons) p.centerScreen.add.add(e);
  }
  @Override
  public void to(State0002 in) {
    for(Button<?> e:buttons) p.centerScreen.remove.add(e);
  }
  @Override
  public void displayCam() {
    for(int i=0;i<info0002.length;i++) p.text(info0002[i],0,18*i);
  }
  @Override
  public void keyReleased(char key,int keyCode) {
    if(keyCode==ESCAPE) p.state(State0002.StartMenu);
  }
}