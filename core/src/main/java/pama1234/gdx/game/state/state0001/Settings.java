package pama1234.gdx.game.state.state0001;

import static com.badlogic.gdx.Input.Keys.ESCAPE;

import com.badlogic.gdx.Gdx;

import pama1234.gdx.game.app.Screen0011;
import pama1234.gdx.game.state.state0001.StateGenerator0001.StateEntity0001;
import pama1234.gdx.game.ui.generator.UiGenerator;
import pama1234.gdx.game.ui.util.Button;
import pama1234.gdx.game.ui.util.Slider;
import pama1234.gdx.game.ui.util.TextButtonCam;
import pama1234.gdx.game.ui.util.TextField;
import pama1234.util.net.ServerInfo;

public class Settings extends StateEntity0001{
  public Button<?>[] buttons;
  public TextButtonCam<?>[] buttonsCam;
  // public Slider<?> volumeSlider;
  public Slider<?>[] sliders;
  public TextField[] screenTextFields,camTextFields;
  public int tx,ty;
  public Settings(Screen0011 p) {
    super(p);
    sliders=new Slider[3];
    buttons=UiGenerator.genButtons_0004(p);
    buttonsCam=UiGenerator.genButtons_0006(p,this);
    sliders[0].pos=p.settings.volume;
    sliders[1].pos=p.settings.gyroscopeSensitivity;
    sliders[2].pos=p.settings.gyroscopeSensitivity;
    screenTextFields=UiGenerator.genTextFields_0001(p);
    camTextFields=UiGenerator.genTextFields_0002(p);
  }
  @Override
  public void from(State0001 in) {
    if(p.settings.serverInfo==null) p.settings.serverInfo=new ServerInfo("127.0.0.1",12347);
    camTextFields[0].setText(p.settings.serverInfo.toString());
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
    p.settings.serverInfo.setFromString(camTextFields[0].getText(),12347);
  }
  @Override
  public void displayCam() {
    tx=-128;
    ty=0;
    text(p.gyroscope?"陀螺仪：  可用":"陀螺仪：不可用");
    text(p.compass?"指南针：  可用":"指南针：不可用");
    text(p.accelerometer?"加速计：  可用":"加速计：不可用");
    tx=-256;
    if(p.gyroscope) {
      line();
      text("陀螺仪 X: "+Gdx.input.getGyroscopeX());
      text("陀螺仪 Y: "+Gdx.input.getGyroscopeY());
      text("陀螺仪 Z: "+Gdx.input.getGyroscopeZ());
    }
    if(p.compass) {
      line();
      text("指南针 X: "+Gdx.input.getAzimuth());
      text("指南针 Y: "+Gdx.input.getPitch());
      text("指南针 Z: "+Gdx.input.getRoll());
    }
    if(p.accelerometer) {
      line();
      text("加速计 X: "+Gdx.input.getAccelerometerX());
      text("加速计 Y: "+Gdx.input.getAccelerometerY());
      text("加速计 Z: "+Gdx.input.getAccelerometerZ());
    }
  }
  public void text(String in) {
    p.text(in,tx,ty);
    line();
  }
  public void line() {
    ty+=18;
  }
  @Override
  public void keyReleased(char key,int keyCode) {
    if(keyCode==ESCAPE) p.state(State0001.StartMenu);
  }
}