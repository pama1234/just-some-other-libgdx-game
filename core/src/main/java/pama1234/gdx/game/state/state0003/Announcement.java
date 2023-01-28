package pama1234.gdx.game.state.state0003;

import static com.badlogic.gdx.Input.Keys.ESCAPE;
import static pama1234.gdx.game.ui.generator.InfoGenerator.info0003;

import pama1234.gdx.game.app.Screen0016;
import pama1234.gdx.game.state.state0003.StateGenerator0003.StateEntity0003;
import pama1234.gdx.game.ui.generator.UiGenerator0003;
import pama1234.gdx.game.ui.util.Button;

public class Announcement extends StateEntity0003{
  public Button<?>[] buttons;
  public Announcement(Screen0016 p) {
    super(p);
    buttons=UiGenerator0003.genButtons_0001(p);
  }
  @Override
  public void from(State0003 in) {
    p.backgroundColor(0);
    // p.cam.noGrab();
    p.cam.point.set(128,64,0);
    for(Button<?> e:buttons) p.centerScreen.add.add(e);
  }
  @Override
  public void to(State0003 in) {
    for(Button<?> e:buttons) p.centerScreen.remove.add(e);
  }
  @Override
  public void displayCam() {
    for(int i=0;i<info0003.length;i++) p.text(info0003[i],0,18*i);
  }
  @Override
  public void keyReleased(char key,int keyCode) {
    if(keyCode==ESCAPE) p.state(State0003.StartMenu);
  }
}