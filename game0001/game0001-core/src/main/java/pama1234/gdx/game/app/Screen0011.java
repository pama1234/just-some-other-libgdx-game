package pama1234.gdx.game.app;

import static pama1234.math.Tools.getFloatString;
import static pama1234.math.Tools.getMemory;
import static pama1234.math.Tools.getMillisString;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.InetAddress;
import java.net.UnknownHostException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Peripheral;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.profiling.GLErrorListener;
import com.badlogic.gdx.graphics.profiling.GLProfiler;
import com.esotericsoftware.kryo.Kryo;

import pama1234.gdx.game.asset.MusicAsset;
import pama1234.gdx.game.state.state0001.Game;
import pama1234.gdx.game.state.state0001.GameMenu.GameSettingsData;
import pama1234.gdx.game.state.state0001.Settings;
import pama1234.gdx.game.state.state0001.State0001Util;
import pama1234.gdx.game.state.state0001.State0001Util.StateCenter0001;
import pama1234.gdx.game.state.state0001.State0001Util.StateChanger0001;
import pama1234.gdx.game.state.state0001.State0001Util.StateEntity0001;
import pama1234.gdx.game.state.state0001.game.KryoUtil;
import pama1234.gdx.game.ui.generator.InfoUtil.InfoData;
import pama1234.gdx.game.ui.generator.InfoUtil.InfoSource;
import pama1234.gdx.game.ui.generator.UiGenerator;
import pama1234.gdx.game.ui.util.TextButton;
import pama1234.gdx.game.util.SettingsData;
import pama1234.gdx.launcher.MainApp;
import pama1234.gdx.util.app.ScreenCore2D;
import pama1234.gdx.util.info.MouseInfo;
import pama1234.math.Tools;
import pama1234.math.UtilMath;
import pama1234.math.vec.Vec3f;
import pama1234.util.net.NetAddressInfo;

public class Screen0011 extends ScreenCore2D implements StateChanger0001{
  public static final PrintStream stderr=System.err;
  public static final PrintStream stdout=System.out;
  public static final Logger logger=LogManager.getLogger(LogManager.ROOT_LOGGER_NAME);
  public static final Kryo kryo=new Kryo();
  static {
    System.setErr(new PrintStream(new OutputStream() {
      public StringBuffer builder=new StringBuffer();
      @Override
      public void write(int b) throws IOException {
        // stderr.write(b);
        char a=(char)b;
        if(a=='\n') {
          logger.error(builder.toString());
          builder.setLength(0);
        }else builder.append(a);
      }
    }));
    System.setOut(new PrintStream(new OutputStream() {
      public StringBuffer builder=new StringBuffer();
      @Override
      public void write(int b) throws IOException {
        stdout.write(b);
        char a=(char)b;
        if(a=='\n') {
          logger.info(builder.toString());
          builder.setLength(0);
        }else builder.append(a);
      }
    }));
    //---
    // kryo.setDefaultSerializer(TaggedFieldSerializer.class);
    kryo.register(SettingsData.class);
    kryo.register(NetAddressInfo.class);
    kryo.register(InfoSource.class);
    kryo.register(InfoSource[].class);
    kryo.register(InfoData.class);
    kryo.register(String[].class);
    kryo.register(GameSettingsData.class);
    kryo.register(int[][].class);
  }
  public StateCenter0001 stateCenter;
  //---
  public SettingsData settings;
  public FileHandle settingsFile=Gdx.files.local("data/settings.bin");
  public StateEntity0001 state;
  public boolean firstRun;
  public boolean pixelPerfectCache;
  //---
  public GLProfiler profiler;
  public long renderTime,updateTime;
  public float debugTextX,debugTextY,debugTextH,debugTextCountY;
  //---
  public TextButton<?>[] buttons;
  //---
  public boolean gyroscopeAvailable,accelerometerAvailable,compassAvailable;
  public Vec3f gVel;
  //---
  public InetAddress localHost;
  //---
  public PrintStream logOut;
  public boolean logUpdate;
  public String logText;
  public StringBuffer logBuffer;
  public int logMaxLength=1024;
  {
    logOut=new PrintStream(new OutputStream() {
      @Override
      public void write(int b) {
        char a=(char)b;
        stdout.append(a);
        logBuffer.append(a);
        logUpdate=true;
      }
    });
    // checkNeedLog();
  }
  public void checkNeedLog() {
    if(settings.showLog) {
      System.setOut(logOut);
      if(logBuffer==null) logBuffer=new StringBuffer();
    }else {
      System.setOut(stdout);
      logText=null;
      if(logBuffer!=null) logBuffer.setLength(0);
    }
  }
  public Screen0011() {
    loadSettings();
    checkNeedLog();
    if(settings.overridePlatform) isAndroid=settings.isAndroid;
  }
  public void debugInfoChange() {
    debugInfoChange(settings.debugInfo||settings.showLog);
  }
  public void debugInfoChange(boolean in) {
    if(in) {
      if(profiler==null) {
        profiler=new GLProfiler(Gdx.graphics);
        profiler.setListener(new GLErrorListener() {
          @Override
          public void onError(int error) {
            throw new UnsupportedOperationException("error="+error);
          }
        });
      }
      profiler.enable();
    }else {
      if(profiler!=null) profiler.disable();
    }
  }
  @Override
  public void setup() {
    Settings.initLocalization(this);
    noStroke();
    buttons=UiGenerator.genButtons_0008(this);
    if(settings.zoomButton) for(TextButton<?> e:buttons) centerScreen.add.add(e);
    stateCenter=new StateCenter0001(this);
    State0001Util.loadState0001(this,stateCenter);
    postSettings();
    firstRun=!Gdx.files.local("data/firstRun.txt").exists();
    if(MainApp.type!=MainApp.taptap) {
      gyroscopeAvailable=Gdx.input.isPeripheralAvailable(Peripheral.Gyroscope);
      accelerometerAvailable=Gdx.input.isPeripheralAvailable(Peripheral.Accelerometer);
      compassAvailable=Gdx.input.isPeripheralAvailable(Peripheral.Compass);
      gVel=new Vec3f();
    }
    // firstRun=true;
    if(firstRun) {
      MusicAsset.load_init();
      state(stateCenter.FirstRun);
      Gdx.files.local("data/firstRun.txt").writeString("1234",false);
    }else state(stateCenter.Loading);
  }
  public void enterGame() {
    pixelPerfectCache=cam2d.pixelPerfect;
    cam2d.pixelPerfect=settings.pixelPerfectIngame;
  }
  public void exitGame() {
    cam2d.pixelPerfect=pixelPerfectCache;
  }
  public void postSettings() {
    Game game=(Game)stateCenter.Game;
    if(settings.debugInfo) game.createDebugDisplay();
    refreshLocalHost();
    debugInfoChange(settings.debugInfo);
    // if(settings.pixelPerfectGlobal)
    cam2d.pixelPerfect=settings.pixelPerfectGlobal;
  }
  public void refreshLocalHost() {
    try {
      localHost=InetAddress.getLocalHost();
    }catch(UnknownHostException e) {
      e.printStackTrace();
    }
  }
  public void loadSettings() {
    settings=KryoUtil.load(kryo,settingsFile,SettingsData.class);
    if(settings==null) initSettings();
    // if(settings.serverInfo==null) settings.serverInfo=new ServerInfo("127.0.0.1",12347);
  }
  public void initSettings() {
    settings=new SettingsData();
    settings.langType="zh_CN";
    // settings.serverInfo=new ServerInfo("127.0.0.1",12347);
    settings.isAndroid=Gdx.app.getType()==ApplicationType.Android;
  }
  public void saveSettings() {
    KryoUtil.save(kryo,settingsFile,settings);
  }
  @Override
  public StateEntity0001 state(StateEntity0001 in) {
    StateEntity0001 out=state;
    state=in;
    if(out!=null) {
      centerScreen.remove.add(out);
      centerCam.remove.add(out.displayCam);
      out.to(in);
      out.pause();
    }
    if(in!=null) {
      in.resume();
      in.from(state);
      centerScreen.add.add(in);
      centerCam.add.add(in.displayCam);
    }
    return out;
  }
  public StateEntity0001 stateNull() {
    StateEntity0001 out=state;
    state=null;
    if(out!=null) {
      centerScreen.list.remove(out);
      centerCam.list.remove(out.displayCam);
      out.to(null);
      out.pause();
    }
    return out;
  }
  @Override
  public void update() {
    if(settings.useGyroscope&&cam2d.activeDrag) {
      cam.point.des.x-=Gdx.input.getGyroscopeX()*settings.gyroscopeSensitivity;
      cam.point.des.y+=Gdx.input.getGyroscopeY()*settings.gyroscopeSensitivity;
      // cam.point.des.z-=Gdx.input.getGyroscopeZ();
    }
    if(compassAvailable) {
      float tx=Gdx.input.getPitch(),
        ty=Gdx.input.getRoll(),
        tz=Gdx.input.getAzimuth();
      gVel.set(0,0,settings.gConst);
      gVel.rotateX(UtilMath.rad(tx));
      gVel.rotateY(UtilMath.rad(ty));
      gVel.rotateZ(UtilMath.rad(tz));
      // gVel.rotateZ(UtilMath.rad(tz)+UtilMath.HALF_PI);
    }
    if(settings.useAccelerometer&&cam2d.active()) {
      //---
      cam.point.des.x-=Gdx.input.getAccelerometerX()*settings.accelerometerSensitivity-gVel.x;
      cam.point.des.y+=Gdx.input.getAccelerometerY()*settings.accelerometerSensitivity-gVel.y;
      // cam.point.des.z-=Gdx.input.getAccelerometerZ();
      cam2d.scale.des+=Gdx.input.getAccelerometerZ()*settings.accelerometerSensitivity-gVel.z;
      cam2d.testScale();
    }
    //---
    if(logUpdate) {
      logUpdate=false;
      int length=logBuffer.length();
      if(length>logMaxLength) logBuffer.delete(0,length-logMaxLength);
      logText=logBuffer.toString();
    }
  }
  @Override
  public void mousePressed(MouseInfo info) {}
  @Override
  public void displayWithCam() {}
  public void drawCursor() {
    beginBlend();
    final int a=0,b=255;
    fill(mouse.left?a:b,mouse.center?a:b,mouse.right?a:b,127);
    rect(mouse.x-4,mouse.y-0.5f,8,1);
    rect(mouse.x-0.5f,mouse.y-4,1,8);
    endBlend();
  }
  @Override
  public void doDraw() {
    if(settings.debugInfo) {
      Tools.time();
      super.doDraw();
      renderTime=Tools.period();
    }else super.doDraw();
  }
  @Override
  public void doUpdate() {
    if(settings.debugInfo) {
      Tools.time();
      super.doUpdate();
      updateTime=Tools.period();
    }else super.doUpdate();
  }
  @Override
  public void display() {
    if(settings.debugInfo) {
      textColor(255,191);
      textScale(UtilMath.max((int)(pus/2f),1));
      initDebugText();
      debugText("Memory   ="+getMemory()+"Mb");
      float tf=1/frameRate;
      debugText("FrameRate="+(tf<999?getFloatString(tf,6):"???.??")+"fps "+getMillisString((int)(frameRate*1000))+"ms");
      debugText("Render   ="+getMillisString(renderTime)+"ms");
      debugText("Update   ="+getMillisString(updateTime)+"ms");
      debugText("CamScale ="+getFloatString(cam2d.scale.pos));
      debugText("DrawCalls="+getMillisString(profiler.getDrawCalls(),5));
      textScale(pus);
      profiler.reset();
    }
    if(cam.grabCursor) {
      withCam();
      drawCursor();
    }
  }
  public void initDebugText() {
    debugTextH=font.scale*font.defaultSize;
    debugTextX=debugTextH;
    debugTextY=bu*1.5f;
    debugTextCountY=0;
  }
  public void debugText(String in) {
    text(in,debugTextX,debugTextY+debugTextH*debugTextCountY);
    debugTextCountY+=1;
  }
  @Override
  public void frameResized() {}
  @Override
  public void dispose() {
    stateNull();
    super.dispose();
    stateCenter.dispose();
    saveSettings();
  }
  @Override
  public void pause() {
    super.pause();
    if(isAndroid) saveSettings();
  }
}