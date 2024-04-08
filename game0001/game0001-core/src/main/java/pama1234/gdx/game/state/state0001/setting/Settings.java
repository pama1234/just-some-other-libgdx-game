package pama1234.gdx.game.state.state0001.setting;

import static com.badlogic.gdx.Input.Keys.ESCAPE;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Array;

import pama1234.gdx.game.app.Screen0011;
import pama1234.gdx.game.state.state0001.State0001Util.StateEntity0001;
import pama1234.gdx.game.ui.TextButtonCamPage;
import pama1234.gdx.game.ui.element.Button;
import pama1234.gdx.game.ui.element.Slider;
import pama1234.gdx.game.ui.element.TextButtonCam;
import pama1234.gdx.game.ui.element.TextField;
import pama1234.gdx.game.util.LocalizationData;
import pama1234.gdx.launcher.MainApp;
import pama1234.yaml.LocalBundleCenter;
import pama1234.yaml.UtilYaml;

/** 设置页面 */
public class Settings extends StateEntity0001{
  public static void initLocalization(Screen0011 p) {
    FileHandle langYaml=Gdx.files.internal("lang/human/"+p.settings.langType+".yaml");
    if(!langYaml.exists()) langYaml=Gdx.files.internal("lang/human/"+(p.settings.langType="zh_CN")+".yaml");
    bundleCenter=new LocalBundleCenter(localization.yaml.load(
      langYaml.readString("UTF-8")));
    ld=localization.load(bundleCenter.get(localization,"空想世界1/游戏"),LocalizationData.class);
    typeName=new String[] {ld.main,ld.taptap,ld.pico};
    Gdx.graphics.setTitle(bundleCenter.get(localization,"空想世界1/系统").data[0]);
    if(p.state!=null) {
      Settings settings=p.stateCenter.settings;
      settings.refreshText();
      for(TextButtonCam<?> i:settings.buttonsCam) i.updateText();
    }
  }

  public static final UtilYaml localization=new UtilYaml();
  public static LocalBundleCenter bundleCenter;
  public static LocalizationData ld;
  public Array<Button<?>> buttons=new Array<>();
  public Array<TextButtonCam<?>> buttonsCam=new Array<>();
  public Array<TextField> camTextFields=new Array<>();

  public SectorCenter0011 sec=new SectorCenter0011(this);

  public void clearUiComp() {
    buttons.clear();
    buttonsCam.clear();
    camTextFields.clear();
  }
  public static String[] typeName;
  public Slider<?>[] sliders;
  public TextButtonCamPage<?>[] buttonsCamPages;
  public int tx,ty;
  public int sensorAvailableTextPosX,needRestartTextPosX;
  public Settings(Screen0011 p,int id) {
    super(p,id);
    refreshText();

    sec.init();
  }
  public void refreshText() {
    sensorAvailableTextPosX=(int)-p.textWidthNoScale(ld.canUseGyroscope)-16;
    needRestartTextPosX=(int)p.textWidthNoScale(ld.setDebugPlatformType+ld.yes)+24;
  }

  @Override
  public void displayCam() {
    tx=sensorAvailableTextPosX;
    ty=-120;
    p.textColor(204);
    text(p.gyroscopeAvailable
      ?ld.canUseGyroscope
      :ld.canNotUseGyroscope);
    text(p.compassAvailable
      ?ld.canUseCompass
      :ld.canNotUseCompass);
    text(p.accelerometerAvailable
      ?ld.canUseAccelerometer
      :ld.canNotUseAccelerometer);
    if(p.localHost!=null) p.text(ld.thisIsIpAddr+p.localHost.toString(),0,-60);
    p.text(ld.releaseVersion+typeName[MainApp.type],0,-80);
    if(p.settings.debugInfo) debugText();
    if(p.settings.showLog) {
      tx=-512;
      drawLogText(p,p.logText,tx,ty);
      newLine();
    }
    p.beginBlend();
    p.fill(255,204);
    p.rect(-5,-120,2,800);
    if(sec.curr!=null) drawIndexPointer();
  }
  public void drawIndexPointer() {
    float tx=sec.currSecPoint.x(),
      ty=sec.currSecPoint.y();
    p.triangle(tx,ty-6,tx,ty+6,tx+10,ty);
  }
  @Override
  public void from(StateEntity0001 in) {
    p.backgroundColor(0);
    p.cam2d.minScale=p.isAndroid?0.5f:1f;
    p.cam2d.testScale();
    sec.addAll(sec.main);
    // for(Button<?> e:buttons) p.centerScreen.add.add(e);
    // for(Button<?> e:buttonsCam) p.centerCam.add.add(e);
    // for(TextField e:camTextFields) p.camStage.addActor(e);
  }
  @Override
  public void to(StateEntity0001 in) {
    for(Button<?> e:buttons) p.centerScreen.remove.add(e);
    for(Button<?> e:buttonsCam) p.centerCam.remove.add(e);
    for(TextField e:camTextFields) e.remove();
    sec.switchSetting(null);
    sec.removeAll(sec.main);
    p.cam2d.minScale=1;
    p.cam2d.testScale();
  }
  @Override
  public void update() {
    sec.update();
  }
  public static void drawLogText(Screen0011 p,String logText,float x,float y) {
    p.font.setColor(p.textColor);
    p.fullText(logText,x,y);
  }
  public void text(String in) {
    p.text(in,tx,ty);
    newLine();
  }
  public void newLine() {
    ty+=18;
  }
  @Override
  public void keyReleased(char key,int keyCode) {
    if(keyCode==ESCAPE) p.state(p.stateCenter.startMenu);
  }
  public void debugText() {
    tx=-256;
    if(p.gyroscopeAvailable) {
      newLine();
      text(ld.gyroscopeX+Gdx.input.getGyroscopeX());
      text(ld.gyroscopeY+Gdx.input.getGyroscopeY());
      text(ld.gyroscopeZ+Gdx.input.getGyroscopeZ());
    }
    if(p.compassAvailable) {
      newLine();
      text(ld.compassX+Gdx.input.getPitch());
      text(ld.compassY+Gdx.input.getRoll());
      text(ld.compassZ+Gdx.input.getAzimuth());
      newLine();
      text(ld.gravityX+p.gVel.x);
      text(ld.gravityY+p.gVel.y);
      text(ld.gravityZ+p.gVel.z);
    }
    if(p.accelerometerAvailable) {
      newLine();
      text(ld.accelerometerX+Gdx.input.getAccelerometerX());
      text(ld.accelerometerY+Gdx.input.getAccelerometerY());
      text(ld.accelerometerZ+Gdx.input.getAccelerometerZ());
    }
  }
}
