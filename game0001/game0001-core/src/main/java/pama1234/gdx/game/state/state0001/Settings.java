package pama1234.gdx.game.state.state0001;

import static com.badlogic.gdx.Input.Keys.ESCAPE;
import static pama1234.gdx.game.util.LocalizationUtil.canNotUseAccelerometer;
import static pama1234.gdx.game.util.LocalizationUtil.canNotUseCompass;
import static pama1234.gdx.game.util.LocalizationUtil.canNotUseGyroscope;
import static pama1234.gdx.game.util.LocalizationUtil.canUseAccelerometer;
import static pama1234.gdx.game.util.LocalizationUtil.canUseCompass;
import static pama1234.gdx.game.util.LocalizationUtil.canUseGyroscope;
import static pama1234.gdx.game.util.LocalizationUtil.needRestart;
import static pama1234.gdx.game.util.LocalizationUtil.thisIsIpAddr;

import com.badlogic.gdx.Gdx;

import pama1234.gdx.game.app.Screen0011;
import pama1234.gdx.game.state.state0001.StateGenerator0001.StateEntity0001;
import pama1234.gdx.game.state.state0001.settings.SettingsUtil;
import pama1234.gdx.game.ui.generator.UiGenerator;
import pama1234.gdx.game.ui.util.Button;
import pama1234.gdx.game.ui.util.Slider;
import pama1234.gdx.game.ui.util.TextButtonCam;
import pama1234.gdx.game.ui.util.TextField;
import pama1234.gdx.game.util.LocalizationUtil.LocalizationData;
import pama1234.gdx.launcher.MainApp;
import pama1234.util.localization.LocalBundle;
import pama1234.util.localization.LocalBundleCenter;
import pama1234.util.localization.Localization;

public class Settings extends StateEntity0001{
  public static final Localization localization=new Localization();
  public static LocalBundleCenter bundleCenter;
  public static LocalBundle bd;
  public static LocalizationData ld;
  public String[] typeName;
  public Button<?>[] buttons;
  public TextButtonCam<?>[] buttonsCam;
  public Slider<?>[] sliders;
  public TextField[] camTextFields;
  public int tx,ty;
  public Settings(Screen0011 p) {
    super(p);
    bundleCenter=new LocalBundleCenter(localization.yaml.load(
      Gdx.files.internal("lang/human/"+p.settings.langType+".yaml").readString("UTF-8")));
    bd=bundleCenter.get(localization,"空想世界1/游戏设置");
    typeName=bd.data;
    Gdx.graphics.setTitle(bundleCenter.get(localization,"空想世界1/系统").data[0]);
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
    tx=-128;
    ty=0;
    text(p.gyroscopeAvailable
      ?bd.data[canUseGyroscope]
      :bd.data[canNotUseGyroscope]);
    text(p.compassAvailable
      ?bd.data[canUseCompass]
      :bd.data[canNotUseCompass]);
    text(p.accelerometerAvailable
      ?bd.data[canUseAccelerometer]
      :bd.data[canNotUseAccelerometer]);
    p.text(bd.data[needRestart],192,280);
    if(p.localHost!=null) p.text(bd.data[thisIsIpAddr]+p.localHost.toString(),0,-40);
    p.text("发行版本："+typeName[MainApp.type],0,-60);
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
      text("陀螺仪 X: "+Gdx.input.getGyroscopeX());
      text("陀螺仪 Y: "+Gdx.input.getGyroscopeY());
      text("陀螺仪 Z: "+Gdx.input.getGyroscopeZ());
    }
    if(p.compassAvailable) {
      line();
      text("指南针 X: "+Gdx.input.getPitch());
      text("指南针 Y: "+Gdx.input.getRoll());
      text("指南针 Z: "+Gdx.input.getAzimuth());
      line();
      text("重力   X: "+p.gVel.x);
      text("重力   Y: "+p.gVel.y);
      text("重力   Z: "+p.gVel.z);
    }
    if(p.accelerometerAvailable) {
      line();
      text("加速计 X: "+Gdx.input.getAccelerometerX());
      text("加速计 Y: "+Gdx.input.getAccelerometerY());
      text("加速计 Z: "+Gdx.input.getAccelerometerZ());
    }
  }
}