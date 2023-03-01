package pama1234.gdx.game.state.state0001;

import static com.badlogic.gdx.Input.Keys.ESCAPE;

import java.io.OutputStream;
import java.io.PrintStream;

import com.badlogic.gdx.Gdx;

import pama1234.gdx.game.app.Screen0011;
import pama1234.gdx.game.state.state0001.StateGenerator0001.StateEntity0001;
import pama1234.gdx.game.state.state0001.settings.SettingsUtil;
import pama1234.gdx.game.ui.generator.UiGenerator;
import pama1234.gdx.game.ui.util.Button;
import pama1234.gdx.game.ui.util.Slider;
import pama1234.gdx.game.ui.util.TextButtonCam;
import pama1234.gdx.game.ui.util.TextField;

public class Settings extends StateEntity0001{
  public Button<?>[] buttons;
  public TextButtonCam<?>[] buttonsCam;
  public Slider<?>[] sliders;
  public TextField[] camTextFields;
  public int tx,ty;
  public boolean logUpdate;
  public String logText;
  public StringBuffer logBuffer;
  public int logMaxLength=1024;
  public PrintStream stdout=System.out;
  public PrintStream logOut;
  public Settings(Screen0011 p) {
    super(p);
    sliders=new Slider[4];
    buttons=UiGenerator.genButtons_0004(p);
    buttonsCam=SettingsUtil.genButtons_0006(p,this);
    initSliders();
    camTextFields=SettingsUtil.genTextFields_0002(p);
    logOut=new PrintStream(new OutputStream() {
      @Override
      public void write(int b) {
        char a=(char)b;
        stdout.append(a);
        logBuffer.append(a);
        logUpdate=true;
      }
    });
    checkNeedLog();
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
  public void checkNeedLog() {
    if(p.settings.showLog) {
      System.setOut(logOut);
      if(logBuffer==null) logBuffer=new StringBuffer();
    }else {
      System.setOut(stdout);
      logText=null;
      if(logBuffer!=null) logBuffer.setLength(0);
    }
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
  public void update() {
    if(logUpdate) {
      logUpdate=false;
      int length=logBuffer.length();
      if(length>logMaxLength) logBuffer.delete(0,length-logMaxLength);
      logText=logBuffer.toString();
    }
  }
  @Override
  public void displayCam() {
    tx=-128;
    ty=0;
    text(p.gyroscopeAvailable?"陀螺仪：  可用":"陀螺仪：不可用");
    text(p.compassAvailable?"指南针：  可用":"指南针：不可用");
    text(p.accelerometerAvailable?"加速计：  可用":"加速计：不可用");
    p.text("重启后生效",192,280);
    if(p.localHost!=null) p.text("本设备的名称与内网IP地址："+p.localHost.toString(),0,-40);
    if(p.settings.debugInfo) debugText();
    if(p.settings.showLog) {
      tx=-512;
      drawLogText();
      line();
    }
  }
  public void drawLogText() {
    p.fontBatch.begin();
    p.font.setColor(1,1,1,1);
    p.font.draw(p.fontBatch,logText==null?"null":logText,tx,ty);
    p.fontBatch.end();
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