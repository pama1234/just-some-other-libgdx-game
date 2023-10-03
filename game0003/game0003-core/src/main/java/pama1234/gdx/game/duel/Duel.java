package pama1234.gdx.game.duel;

import static pama1234.app.game.server.duel.ServerConfigData.neat;
import static pama1234.app.game.server.duel.util.Const.CANVAS_SIZE;

import java.util.function.Supplier;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net.Protocol;
import com.badlogic.gdx.net.SocketHints;

import pama1234.app.game.server.duel.NetUtil.LoginInfo;
import pama1234.app.game.server.duel.ServerConfigData.GameMode;
import pama1234.app.game.server.duel.ServerConfigData.ServerAttr;
import pama1234.app.game.server.duel.ServerConfigData.ThemeType;
import pama1234.gdx.Pama;
import pama1234.gdx.game.duel.NetUtil.GameClient;
import pama1234.gdx.game.duel.State0002Util.DebugStateEntitys;
import pama1234.gdx.game.duel.State0002Util.StateCenter0002;
import pama1234.gdx.game.duel.State0002Util.StateEntity0002;
import pama1234.gdx.game.duel.util.ai.nnet.NeatCenter;
import pama1234.gdx.game.duel.util.ai.nnet.NeatCenter.NetworkGroupParam;
import pama1234.gdx.game.duel.util.graphics.DemoInfo;
import pama1234.gdx.game.duel.util.theme.ThemeData;
import pama1234.gdx.game.state.state0002.Game;
import pama1234.gdx.util.app.ScreenCoreState2D;
import pama1234.gdx.util.info.MouseInfo;
import pama1234.gdx.util.info.TouchInfo;
import pama1234.gdx.util.net.SocketWrapperGDX;
import pama1234.util.yaml.Serialization;

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
 * Altered with self-made framework
 * </p>
 * Altered with Processing-Libgdx
 * </p>
 * </p>
 *
 * The font "Unifont" https://unifoundry.com/unifont/ is part of the GNU Project.
 */
public class Duel extends ScreenCoreState2D<StateCenter0002,StateEntity0002>{

  public static final Serialization localization=new Serialization();

  public int canvasSideLength=CANVAS_SIZE;

  public DemoInfo demoInfo;
  public float strokeUnit;

  public Config config;

  public NeatEntity neatE;
  public NeatCenter neatCenter;
  public NetworkGroupParam param;

  public GameClient gameClient;
  public LoginInfo loginInfo;

  public Supplier<ClientGameSystem> core;
  public boolean debug;
  {
    // isAndroid=true;
    // doUpdateThread=true;
  }
  @Override
  public void init() {
    configMisc();
    super.init();
  }
  public void configMisc() {
    config=new Config();
    initConfig();
    if(isAndroid) Pama.mobile.orientation(config.data.orientation);
    if(config.data.server==null) config.data.server=new ServerAttr("127.0.0.1",12348);
    if(config.data.themeType==null) config.data.themeType=ThemeType.Light;
    if(config.data.gameMode==GameMode.OnLine) {
      SocketHints socketHints=new SocketHints();
      socketHints.connectTimeout=5000;
      socketHints.socketTimeout=5000;
      socketHints.keepAlive=true;
      socketHints.performancePrefConnectionTime=0;
      socketHints.performancePrefLatency=2;
      socketHints.performancePrefBandwidth=1;
      try {
        gameClient=new GameClient(new SocketWrapperGDX(Gdx.net.newClientSocket(Protocol.TCP,config.data.server.addr,config.data.server.port,socketHints)));
      }catch(RuntimeException ex) {
        ex.printStackTrace();
        config.data.gameMode=GameMode.OffLine;
      }
    }
    debug=config.data.debug;
    if(config.data.fpsFix) doUpdateThread=true;
  }
  public void initConfig() {
    config.initConfig();
  }
  public ThemeData theme() {
    return config.theme;
  }
  public void theme(ThemeData in) {
    config.theme=in;
  }
  @Override
  public void setup() {
    stateCenter=new StateCenter0002(this);
    State0002Util.loadState0002(this,stateCenter);
    if(debug) {
      stateCenter.debug=new DebugStateEntitys(this);
      State0002Util.loadState0003Test(this,stateCenter.debug);
    }
    state(stateCenter.startMenu);

    config.themeConfigTextArea=stateCenter.settings.textEditors[0].textArea;
    TextUtil.used=TextUtil.gen_ch(this::textWidthNoScale);

    if(config.data.mode==neat) neatE=new NeatEntity(this,game(),true);

    backgroundColor(theme().background);
    setTextColor(theme().text);
    demoInfo=new DemoInfo(this);

    setupCamera();
  }
  public void setupCamera() {
    cam.point.des.set(canvasSideLength/2f,canvasSideLength/2f);
    cam.point.pos.set(cam.point.des);
    if(config.data.mode==neat) {
      cam2d.minScale=1/8f;
      cam2d.scaleUnit=1/8f;
      cam2d.scale.pos=cam2d.scale.des=(isAndroid?0.25f:1)*0.6f;
    }else {
      if(isAndroid) {
        cam2d.minScale=1/2f;
        cam2d.scale.pos=cam2d.scale.des=0.25f;
      }else {
        cam2d.minScale=2f;
      }
    }
  }
  @Override
  public void pause() {
    super.pause();
    if(isAndroid) config.saveConfig();
  }
  @Override
  public void dispose() {
    super.dispose();
    config.saveConfig();
  }
  @Override
  public void display() {
    if(config.data.mode==neat) neatE.display();
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
    super.strokeWeight(config.data.mode==neat?in:in*strokeUnit);
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
  public void inGameStateChangeEvent(ClientGameSystem system,int stateIndex) {
    if(system.stateIndex==ClientGameSystem.play) {
      if(config.data.mode==neat) neatE.time=0;
    }else if(system.stateIndex==ClientGameSystem.result) {
      for(var group:system.groupCenter.list) {
        for(var i:group.playerCenter.list) i.engine.setScore(0,system.currentState.getScore(group.id));
      }
    }
  }
  @Deprecated
  public Game game() {
    return (Game)state;
  }
  public ClientGameSystem core() {
    return core.get();
  }
  public boolean online() {
    return config.data.gameMode==GameMode.OnLine;
  }
}