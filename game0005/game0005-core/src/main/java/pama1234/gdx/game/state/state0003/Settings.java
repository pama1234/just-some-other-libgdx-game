package pama1234.gdx.game.state.state0003;

import static com.badlogic.gdx.Input.Keys.ESCAPE;

import pama1234.gdx.game.app.app0004.Screen0016;
import pama1234.gdx.game.state.state0003.StateGenerator0003.StateEntity0003;
import pama1234.gdx.game.ui.generator.UiGenerator0003;
import pama1234.gdx.game.ui.util.Button;
import pama1234.gdx.game.ui.util.Slider;
import pama1234.gdx.game.ui.util.TextButtonCam;

public class Settings extends StateEntity0003{
  public Button<?>[] buttons;
  public TextButtonCam<?>[] buttonsCam;
  public Slider<?> volumeSlider;
  public Settings(Screen0016 p) {
    super(p);
    buttons=UiGenerator0003.genButtons_0001(p);
    buttonsCam=UiGenerator0003.genButtons_0006(p,this);
  }
  @Override
  public void from(State0003 in) {
    p.backgroundColor(0);
    // p.cam.noGrab();
    for(Button<?> e:buttons) p.centerScreen.add.add(e);
    for(Button<?> e:buttonsCam) p.centerCam.add.add(e);
  }
  @Override
  public void to(State0003 in) {
    for(Button<?> e:buttons) p.centerScreen.remove.add(e);
    for(Button<?> e:buttonsCam) p.centerCam.remove.add(e);
  }
  @Override
  public void displayCam() {}
  @Override
  public void keyReleased(char key,int keyCode) {
    if(keyCode==ESCAPE) p.state(State0003.StartMenu);
  }
}