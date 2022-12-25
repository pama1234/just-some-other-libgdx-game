package pama1234.gdx.game.state.state0002;

import static com.badlogic.gdx.Input.Keys.ESCAPE;

import pama1234.gdx.game.app.Screen0012;
import pama1234.gdx.game.state.state0002.StateGenerator0002.StateEntity0002;
import pama1234.gdx.game.ui.generator.ButtonGenerator0002;
import pama1234.gdx.game.ui.util.Button;
import pama1234.gdx.game.ui.util.Slider;
import pama1234.gdx.game.ui.util.TextButtonCam;

public class Settings extends StateEntity0002{
  public Button<?>[] buttons;
  public TextButtonCam<?>[] buttonsCam;
  public Slider<?> volumeSlider;
  public Settings(Screen0012 p) {
    super(p);
    buttons=ButtonGenerator0002.genButtons_0001(p);
    buttonsCam=ButtonGenerator0002.genButtons_0006(p,this);
  }
  @Override
  public void from(State0002 in) {
    p.backgroundColor(0);
    p.cam.noGrab();
    for(Button<?> e:buttons) p.centerScreen.add.add(e);
    for(Button<?> e:buttonsCam) p.centerCam.add.add(e);
  }
  @Override
  public void to(State0002 in) {
    for(Button<?> e:buttons) p.centerScreen.remove.add(e);
    for(Button<?> e:buttonsCam) p.centerCam.remove.add(e);
  }
  @Override
  public void displayCam() {}
  @Override
  public void keyReleased(char key,int keyCode) {
    if(keyCode==ESCAPE) p.state(State0002.StartMenu);
  }
}