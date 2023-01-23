package pama1234.gdx.game.state.state0001;

import static com.badlogic.gdx.Input.Keys.ESCAPE;

import pama1234.gdx.game.app.Screen0011;
import pama1234.gdx.game.state.state0001.StateGenerator0001.StateEntity0001;
import pama1234.gdx.game.ui.generator.UiGenerator;
import pama1234.gdx.game.ui.util.Button;
import pama1234.gdx.game.ui.util.Slider;
import pama1234.gdx.game.ui.util.TextButtonCam;
import pama1234.gdx.game.ui.util.TextField;

public class Settings extends StateEntity0001{
  public Button<?>[] buttons;
  public TextButtonCam<?>[] buttonsCam;
  public Slider<?> volumeSlider;
  public TextField[] screenTextFields,camTextFields;
  public Settings(Screen0011 p) {
    super(p);
    buttons=UiGenerator.genButtons_0004(p);
    buttonsCam=UiGenerator.genButtons_0006(p,this);
    screenTextFields=UiGenerator.genTextFields_0001(p);
    camTextFields=UiGenerator.genTextFields_0002(p);
  }
  @Override
  public void from(State0001 in) {
    p.backgroundColor(0);
    p.cam2d.minScale=p.isAndroid?0.5f:1f;
    p.cam2d.testScale();
    for(Button<?> e:buttons) p.centerScreen.add.add(e);
    for(Button<?> e:buttonsCam) p.centerCam.add.add(e);
    for(TextField e:screenTextFields) p.screenStage.addActor(e);
    for(TextField e:camTextFields) p.camStage.addActor(e);
  }
  @Override
  public void to(State0001 in) {
    for(Button<?> e:buttons) p.centerScreen.remove.add(e);
    for(Button<?> e:buttonsCam) p.centerCam.remove.add(e);
    for(TextField e:screenTextFields) e.remove();
    for(TextField e:camTextFields) e.remove();
    p.cam2d.minScale=1;
    p.cam2d.testScale();
  }
  @Override
  public void displayCam() {}
  @Override
  public void keyReleased(char key,int keyCode) {
    if(keyCode==ESCAPE) p.state(State0001.StartMenu);
  }
}