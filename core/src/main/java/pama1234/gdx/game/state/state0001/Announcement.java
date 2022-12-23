package pama1234.gdx.game.state.state0001;

import static com.badlogic.gdx.Input.Keys.ESCAPE;
import static pama1234.gdx.game.ui.InfoGenerator.info0002;

import pama1234.gdx.game.app.Screen0011;
import pama1234.gdx.game.state.state0001.StateGenerator0001.StateEntity0001;
import pama1234.gdx.game.ui.ButtonGenerator;
import pama1234.gdx.game.ui.util.Button;

public class Announcement extends StateEntity0001{
  public Button<?>[] buttons;
  public Announcement(Screen0011 p) {
    super(p);
    buttons=ButtonGenerator.genButtons_0004(p);
  }
  @Override
  public void from(State0001 in) {
    p.backgroundColor(0);
    p.cam.noGrab();
    p.cam.point.set(128,64,0);
    for(Button<?> e:buttons) p.centerScreen.add.add(e);
  }
  @Override
  public void to(State0001 in) {
    for(Button<?> e:buttons) p.centerScreen.remove.add(e);
  }
  @Override
  public void displayCam() {
    for(int i=0;i<info0002.length;i++) p.text(info0002[i],0,18*i);
  }
  @Override
  public void keyReleased(char key,int keyCode) {
    if(keyCode==ESCAPE) p.state(State0001.StartMenu);
  }
}