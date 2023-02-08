package pama1234.gdx.game.app;

import static pama1234.math.Tools.getFloatString;
import static pama1234.math.Tools.getMemory;
import static pama1234.math.Tools.getMillisString;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.KryoException;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import com.esotericsoftware.kryo.serializers.FieldSerializer;
import com.esotericsoftware.kryo.serializers.TaggedFieldSerializer.Tag;

import pama1234.gdx.game.asset.MusicAsset;
import pama1234.gdx.game.state.state0001.Game;
import pama1234.gdx.game.state.state0001.State0001;
import pama1234.gdx.game.state.state0001.State0001.StateChanger;
import pama1234.gdx.game.ui.generator.UiGenerator;
import pama1234.gdx.game.ui.util.TextButton;
import pama1234.gdx.game.state.state0001.StateGenerator0001;
import pama1234.gdx.util.app.ScreenCore2D;
import pama1234.gdx.util.info.MouseInfo;
import pama1234.math.Tools;
import pama1234.util.net.ServerInfo;

public class Screen0011 extends ScreenCore2D implements StateChanger{
  public static final Kryo kryo=new Kryo();
  static {
    kryo.register(SettingsData.class);
    kryo.register(ServerInfo.class,new FieldSerializer<ServerInfo>(kryo,ServerInfo.class));
  }
  public static class SettingsData{
    @Tag(0)
    public boolean showEarth=true;
    @Tag(1)
    public boolean debugInfo;
    @Tag(2)
    public boolean mute;
    @Tag(3)
    public float volume=1;
    @Tag(4)
    public ServerInfo serverInfo;
    @Tag(5)
    public boolean zoomButton;
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
  @Override
  public void setup() {
    noStroke();
    loadSettings();
    buttons=UiGenerator.genButtons_0008(this);
    if(settings.zoomButton) for(TextButton<?> e:buttons) centerScreen.add.add(e);
    StateGenerator0001.loadState0001(this);
    postSettings();
    firstRun=!Gdx.files.local("data/firstRun.txt").exists();
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
  }
  public void loadSettings() {
    if(!settingsFile.exists()) {
      initSettings();
      return;
    }
    try(Input input=new Input(new FileInputStream(settingsFile.file()))) {
      SettingsData out=kryo.readObject(input,SettingsData.class);
      input.close();
      settings=out;
    }catch(FileNotFoundException|KryoException e) {
      e.printStackTrace();
    }
    if(settings==null) initSettings();
  }
  public void initSettings() {
    settings=new SettingsData();
    // if(settings.serverInfo==null)
    settings.serverInfo=new ServerInfo("127.0.0.1",12347);
  }
  public void saveSettings() {
    try(Output output=new Output(new FileOutputStream(settingsFile.file()))) {
      kryo.writeObject(output,settings);
      output.close();
    }catch(FileNotFoundException|KryoException e) {
      e.printStackTrace();
    }
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
  public void update() {}
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