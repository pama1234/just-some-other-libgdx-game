package pama1234.gdx.game.state.state0001;

import static com.badlogic.gdx.Input.Keys.ESCAPE;

import pama1234.gdx.game.app.Screen0011;
import pama1234.gdx.game.state.state0001.StateGenerator0001.StateEntity0001;
import pama1234.gdx.game.ui.ButtonGenerator;
import pama1234.gdx.game.ui.util.Button;
import pama1234.gdx.game.ui.util.Slider;
import pama1234.gdx.game.ui.util.TextButtonCam;

public class Settings extends StateEntity0001{
  public Button<?>[] buttons;
  public TextButtonCam<?>[] buttonsCam;
  public Slider<?> volumeSlider;
  public Settings(Screen0011 p) {
    super(p);
    buttons=ButtonGenerator.genButtons_0004(p);
    buttonsCam=ButtonGenerator.genButtons_0006(p,this);
  }
  @Override
  public void from(State0001 in) {
    p.backgroundColor(0);
    p.cam.noGrab();
    for(Button<?> e:buttons) p.centerScreen.add.add(e);
    for(Button<?> e:buttonsCam) p.centerCam.add.add(e);
  }
  @Override
  public void to(State0001 in) {
    for(Button<?> e:buttons) p.centerScreen.remove.add(e);
    for(Button<?> e:buttonsCam) p.centerCam.remove.add(e);
  }
  @Override
  public void displayCam() {}
  @Override
  public void keyReleased(char key,int keyCode) {
    if(keyCode==ESCAPE) p.state(State0001.StartMenu);
  }
}