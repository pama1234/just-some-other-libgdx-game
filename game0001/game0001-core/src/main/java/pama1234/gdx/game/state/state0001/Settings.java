package pama1234.gdx.game.state.state0001;

import static com.badlogic.gdx.Input.Keys.ESCAPE;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

import pama1234.gdx.game.app.Screen0011;
import pama1234.gdx.game.state.state0001.StateGenerator0001.StateEntity0001;
import pama1234.gdx.game.state.state0001.settings.SettingsUtil;
import pama1234.gdx.game.ui.generator.UiGenerator;
import pama1234.gdx.game.ui.util.Button;
import pama1234.gdx.game.ui.util.Slider;
import pama1234.gdx.game.ui.util.TextButtonCam;
import pama1234.gdx.game.ui.util.TextField;
import pama1234.gdx.game.util.LocalizationData;
import pama1234.gdx.launcher.MainApp;
import pama1234.util.localization.LocalBundleCenter;
import pama1234.util.localization.Localization;

public class Settings extends StateEntity0001{
  public static final Localization localization=new Localization();
  public static LocalBundleCenter bundleCenter;
  public static LocalizationData ld;
  public String[] typeName;
  public Button<?>[] buttons;
  public TextButtonCam<?>[] buttonsCam;
  public Slider<?>[] sliders;
  public TextField[] camTextFields;
  public int tx,ty;
  public int sensorAvailableTextPosX;
  public Settings(Screen0011 p) {
    super(p);
    FileHandle langYaml=Gdx.files.internal("lang/human/"+p.settings.langType+".yaml");
    if(!langYaml.exists()) langYaml=Gdx.files.internal("lang/human/"+(p.settings.langType="zh_CN")+".yaml");
    bundleCenter=new LocalBundleCenter(localization.yaml.load(
      langYaml.readString("UTF-8")));
    ld=localization.load(bundleCenter.get(localization,"空想世界1/游戏设置"),LocalizationData.class);
    typeName=new String[] {ld.main,ld.taptap,ld.pico};
    Gdx.graphics.setTitle(bundleCenter.get(localization,"空想世界1/系统").data[0]);
    //---
    sensorAvailableTextPosX=(int)-p.textWidth(ld.canUseGyroscope)-16;
    // System.out.println(sensorAvailableTextPosX);
    //---
    sliders=new Slider[4];
    buttons=UiGenerator.genButtons_0004(p);
    buttonsCam=SettingsUtil.genButtons_0006(p,this);
    initSliders();
    camTextFields=SettingsUtil.genTextFields_0002(p);
  }
  public void initSliders() {
    sliders[0].pos=p.settings.volume;
    sliders[1].pos=p.settings.gyroscopeSensitivity;
    sliders[2].pos=p.settings.gyroscopeSensitivity;
    sliders[3].pos=p.settings.gConst;
  }
  @Override
  public void from(State0001 in) {
    p.backgroundColor(0);
    p.cam2d.minScale=p.isAndroid?0.5f:1f;
    p.cam2d.testScale();
    for(Button<?> e:buttons) p.centerScreen.add.add(e);
    for(Button<?> e:buttonsCam) p.centerCam.add.add(e);
    for(TextField e:camTextFields) p.camStage.addActor(e);
  }
  @Override
  public void to(State0001 in) {
    for(Button<?> e:buttons) p.centerScreen.remove.add(e);
    for(Button<?> e:buttonsCam) p.centerCam.remove.add(e);
    for(TextField e:camTextFields) e.remove();
    p.cam2d.minScale=1;
    p.cam2d.testScale();
  }
  @Override
  public void update() {}
  @Override
  public void displayCam() {
    tx=sensorAvailableTextPosX;
    ty=0;
    text(p.gyroscopeAvailable
      ?ld.canUseGyroscope
      :ld.canNotUseGyroscope);
    text(p.compassAvailable
      ?ld.canUseCompass
      :ld.canNotUseCompass);
    text(p.accelerometerAvailable
      ?ld.canUseAccelerometer
      :ld.canNotUseAccelerometer);
    p.text(ld.needRestart,192,280);
    if(p.localHost!=null) p.text(ld.thisIsIpAddr+p.localHost.toString(),0,-60);
    p.text(ld.releaseVersion+typeName[MainApp.type],0,-80);
    p.text(ld.languageSettings,88,-20);
    if(p.settings.debugInfo) debugText();
    if(p.settings.showLog) {
      tx=-512;
      drawLogText(p,p.logText,tx,ty);
      line();
    }
  }
  public static void drawLogText(Screen0011 p,String logText,float x,float y) {
    p.font.setColor(p.textColor);
    p.fullText(logText,x,y);
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
  public void debugText() {
    tx=-256;
    if(p.gyroscopeAvailable) {
      line();
      text(ld.gyroscopeX+Gdx.input.getGyroscopeX());
      text(ld.gyroscopeY+Gdx.input.getGyroscopeY());
      text(ld.gyroscopeZ+Gdx.input.getGyroscopeZ());
    }
    if(p.compassAvailable) {
      line();
      text(ld.compassX+Gdx.input.getPitch());
      text(ld.compassY+Gdx.input.getRoll());
      text(ld.compassZ+Gdx.input.getAzimuth());
      line();
      text(ld.gravityX+p.gVel.x);
      text(ld.gravityY+p.gVel.y);
      text(ld.gravityZ+p.gVel.z);
    }
    if(p.accelerometerAvailable) {
      line();
      text(ld.accelerometerX+Gdx.input.getAccelerometerX());
      text(ld.accelerometerY+Gdx.input.getAccelerometerY());
      text(ld.accelerometerZ+Gdx.input.getAccelerometerZ());
    }
  }
}