package pama1234.gdx.game.app;

import static pama1234.math.Tools.getFloatString;
import static pama1234.math.Tools.getMemory;
import static pama1234.math.Tools.getMillisString;

import java.net.InetAddress;
import java.net.UnknownHostException;

import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Peripheral;
import com.badlogic.gdx.files.FileHandle;
import com.esotericsoftware.kryo.Kryo;

import pama1234.gdx.game.asset.MusicAsset;
import pama1234.gdx.game.state.state0001.Game;
import pama1234.gdx.game.state.state0001.State0001;
import pama1234.gdx.game.state.state0001.State0001.StateChanger;
import pama1234.gdx.game.state.state0001.StateGenerator0001;
import pama1234.gdx.game.state.state0001.game.KryoUtil;
import pama1234.gdx.game.ui.generator.InfoUtil.InfoData;
import pama1234.gdx.game.ui.generator.InfoUtil.InfoSource;
import pama1234.gdx.game.ui.generator.UiGenerator;
import pama1234.gdx.game.ui.util.TextButton;
import pama1234.gdx.util.app.ScreenCore2D;
import pama1234.gdx.util.info.MouseInfo;
import pama1234.math.Tools;
import pama1234.math.UtilMath;
import pama1234.math.vec.Vec3f;
import pama1234.util.net.ServerInfo;

public class Screen0011 extends ScreenCore2D implements StateChanger{
  public static final Kryo kryo=new Kryo();
  static {
    // kryo.setDefaultSerializer(TaggedFieldSerializer.class);
    kryo.register(SettingsData.class);
    kryo.register(ServerInfo.class);
    kryo.register(InfoSource.class);
    kryo.register(InfoSource[].class);
    kryo.register(InfoData.class);
    kryo.register(String[].class);
  }
  public static class SettingsData{
    public boolean showEarth=true;
    public boolean debugInfo;
    public boolean mute;
    public float volume=1;
    public ServerInfo serverInfo;
    public boolean zoomButton;
    public boolean useGyroscope,useAccelerometer;
    public float gyroscopeSensitivity=1,accelerometerSensitivity=1;
    public float gConst=9.81f;
    // public boolean useCompass;
    public boolean overridePlatform;
    public boolean isAndroid;
    // public boolean multiplayer;
  }
  public SettingsData settings;
  public FileHandle settingsFile=Gdx.files.local("data/settings.bin");
  public State0001 state;
  public boolean firstRun;
  //---
  public long renderTime,updateTime;
  public float debugTextX,debugTextY,debugTextH,debugTextCountY;
  //---
  public TextButton<?>[] buttons;
  //---
  public boolean gyroscopeAvailable,accelerometerAvailable,compassAvailable;
  public Vec3f gVel;
  //---
  public InetAddress localHost;
  public Screen0011() {
    loadSettings();
    if(settings.overridePlatform) isAndroid=settings.isAndroid;
  }
  @Override
  public void setup() {
    noStroke();
    buttons=UiGenerator.genButtons_0008(this);
    if(settings.zoomButton) for(TextButton<?> e:buttons) centerScreen.add.add(e);
    StateGenerator0001.loadState0001(this);
    postSettings();
    firstRun=!Gdx.files.local("data/firstRun.txt").exists();
    gyroscopeAvailable=Gdx.input.isPeripheralAvailable(Peripheral.Gyroscope);
    accelerometerAvailable=Gdx.input.isPeripheralAvailable(Peripheral.Accelerometer);
    compassAvailable=Gdx.input.isPeripheralAvailable(Peripheral.Compass);
    gVel=new Vec3f();
    // firstRun=true;
    if(firstRun) {
      MusicAsset.load_init();
      state(State0001.FirstRun);
      Gdx.files.local("data/firstRun.txt").writeString("1234",false);
    }else state(State0001.Loading);
  }
  public void postSettings() {
    Game game=(Game)State0001.Game.entity;
    if(game.debug=settings.debugInfo) game.createDebugDisplay();
    refreshLocalHost();
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
    if(settings.serverInfo==null) settings.serverInfo=new ServerInfo("127.0.0.1",12347);
  }
  public void initSettings() {
    settings=new SettingsData();
    settings.serverInfo=new ServerInfo("127.0.0.1",12347);
    settings.isAndroid=Gdx.app.getType()==ApplicationType.Android;
  }
  public void saveSettings() {
    KryoUtil.save(kryo,settingsFile,settings);
  }
  @Override
  public State0001 state(State0001 in) {
    State0001 out=state;
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
  public State0001 stateNull() {
    State0001 out=state;
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
      textScale(pus/2f);
      initDebugText();
      debugText("Memory   ="+getMemory()+"Mb");
      float tf=1/frameRate;
      debugText("FrameRate="+(tf<999?getFloatString(tf,6):"???.??")+"fps "+getMillisString((int)(frameRate*1000))+"ms");
      debugText("Render   ="+getMillisString(renderTime)+"ms");
      debugText("Update   ="+getMillisString(updateTime)+"ms");
      debugText("CamScale ="+getFloatString(cam2d.scale.pos));
      textScale(pus);
    }
    if(cam.grabCursor) {
      withCam();
      drawCursor();
    }
  }
  public void initDebugText() {
    debugTextH=pu/2f;
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
    State0001.disposeAll();
    saveSettings();
  }
  @Override
  public void pause() {
    super.pause();
    if(isAndroid) saveSettings();
  }
}