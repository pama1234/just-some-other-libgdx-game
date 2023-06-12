package pama1234.gdx.game.duel;

import static pama1234.app.game.server.duel.ServerConfigData.neat;
import static pama1234.app.game.server.duel.util.Const.CANVAS_SIZE;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

import pama1234.gdx.game.duel.NetUtil.ClientConfig;
import pama1234.gdx.game.duel.NetUtil.GameClient;
import pama1234.gdx.game.duel.NetUtil.LoginInfo;
import pama1234.gdx.game.duel.State0002Util.StateCenter0002;
import pama1234.gdx.game.duel.State0002Util.StateChanger0002;
import pama1234.gdx.game.duel.State0002Util.StateEntity0002;
import pama1234.gdx.game.duel.util.ai.nnet.NeatCenter;
import pama1234.gdx.game.duel.util.ai.nnet.NeatCenter.NetworkGroupParam;
import pama1234.gdx.game.duel.util.graphics.DemoInfo;
import pama1234.gdx.game.duel.util.skin.SkinData;
import pama1234.gdx.util.app.ScreenCore2D;
import pama1234.gdx.util.info.MouseInfo;
import pama1234.gdx.util.info.TouchInfo;
import pama1234.util.localization.Localization;
import pama1234.util.protobuf.InputDataProto;

/**
 * Title: Duel
 * </p>
 * Author: FAL ( https://www.fal-works.com/ )
 * </p>
 * Made with Processing 3.3.6
 * </p>
 * </p>
 * Change log: Ver. 0.1 (30. Sep. 2017) First version. Ver. 0.2 ( 1. Oct. 2017) Bug fix
 * (unintended change of strokeWeight), minor update (enabled to hide instruction window). Ver.
 * 0.3 (10. Feb. 2018) Minor fix (lack of semicolon). Ver. 0.4 (12. Feb. 2018) Enabled scaling.
 * </p>
 * </p>
 * Modified by: Pama1234 ( https://space.bilibili.com/646050693 )
 * </p>
 * Altered with self-made framework v0.0.1
 * </p>
 * Altered with Processing-Libgdx v0.0.1
 * </p>
 * </p>
 *
 * The font "Unifont" https://unifoundry.com/unifont/ is part of the GNU Project.
 */
public class Duel extends ScreenCore2D implements StateChanger0002{
  //---
  public static final Localization localization=new Localization();
  // public static LocalBundleCenter bundleCenter;
  //---
  public int canvasSideLength=CANVAS_SIZE;
  //---
  public DemoInfo demoInfo;
  public float strokeUnit;
  public SkinData skin;
  //---
  public Config config;
  public FileHandle configFile;
  //---
  public NeatEntity neatE;
  public NeatCenter neatCenter;
  public NetworkGroupParam param;
  //---
  public GameClient gameClient;
  public ClientConfig clientConfig;
  public LoginInfo loginInfo;
  public InputDataProto.InputData.Builder inputDataBuilder;
  //---
  public StateCenter0002 stateCenter;
  public StateEntity0002 state;
  {
    // isAndroid=true;
  }
  @Override
  public void init() {
    configFile=Gdx.files.local("data/config.yaml");
    config=loadConfig();
    if(config.skin!=null) skin=SkinData.fromData(config.skin);
    else {
      skin=new SkinData();
      skin.init();
    }
    super.init();
  }
  public Config loadConfig() {
    Config out;
    if(configFile.exists()) {
      out=localization.yaml.loadAs(configFile.readString("UTF-8"),Config.class);
    }else {
      Gdx.files.local("data").mkdirs();
      out=new Config();
      out.init();
    }
    return out;
  }
  @Override
  public void setup() {
    stateCenter=new StateCenter0002(this);
    State0002Util.loadState0002(this,stateCenter);
    state(stateCenter.game);
    //---
    TextUtil.used=TextUtil.gen_ch(this::textWidthNoScale);
    //---
    if(config.mode==neat) neatE=new NeatEntity(this,stateCenter.game,true);
    //---
    backgroundColor(skin.background);
    setTextColor(skin.text);
    demoInfo=new DemoInfo(this);
    //---
    setupCamera();
  }
  public void setupCamera() {
    cam.point.des.set(canvasSideLength/2f,canvasSideLength/2f);
    cam.point.pos.set(cam.point.des);
    if(config.mode==neat) {
      cam2d.minScale=1/8f;
      cam2d.scaleUnit=1/8f;
      cam2d.scale.pos=cam2d.scale.des=(isAndroid?0.25f:1)*0.6f;
    }else {
      if(isAndroid) cam2d.scale.pos=cam2d.scale.des=0.25f;
      cam2d.activeDrag=false;
      cam2d.activeScrollZoom=cam2d.activeTouchZoom=false;
    }
  }
  @Override
  public void dispose() {
    super.dispose();
    config.skin=skin.toData();
    configFile.writeString(localization.yaml.dumpAsMap(config),false);
  }
  @Override
  public void display() {
    if(config.mode==neat) neatE.display();
  }
  @Override
  public void displayWithCam() {}
  @Override
  public void update() {}
  @Override
  public void mousePressed(MouseInfo info) {}
  @Override
  public void keyPressed(char key,int keyCode) {}
  @Override
  public void keyReleased(char key,int keyCode) {}
  @Override
  public void frameResized() {
    strokeUnit=isAndroid?u/128f:u/64f;
  }
  @Override
  public void strokeWeight(float in) {
    super.strokeWeight(config.mode==neat?in:in*strokeUnit);
  }
  public void strokeWeightOriginal(float in) {
    super.strokeWeight(in);
  }
  @Override
  public void touchStarted(TouchInfo info) {}
  @Override
  public void touchMoved(TouchInfo info) {}
  @Override
  public void touchEnded(TouchInfo info) {}
  public void stateChangeEvent(ClientGameSystem system,int stateIndex) {
    if(system.stateIndex==ClientGameSystem.play) {
      if(config.mode==neat) neatE.time=0;
    }else if(system.stateIndex==ClientGameSystem.result) {
      system.myGroup.player.engine.setScore(0,system.currentState.getScore(system.myGroup.id));
      system.otherGroup.player.engine.setScore(0,system.currentState.getScore(system.otherGroup.id));
    }
  }
  @Override
  public StateEntity0002 state(StateEntity0002 in) {
    StateEntity0002 out=state;
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
}