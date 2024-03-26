package pama1234.gdx.game.life.particle.app;

import java.util.LinkedList;

import com.badlogic.gdx.Gdx;
import pama1234.Tools;
import pama1234.gdx.game.life.particle.state0004.State0004Util;
import pama1234.gdx.game.life.particle.state0004.State0004Util.StateCenter0004;
import pama1234.gdx.game.life.particle.state0004.State0004Util.StateEntity0004;
import pama1234.gdx.game.life.particle.ui.generator.UiGenerator;
import pama1234.gdx.game.life.particle.util.input.InputData;
import pama1234.gdx.game.life.particle.util.input.RealGameAndroidCtrl;
import pama1234.gdx.game.ui.element.TextButton;
import pama1234.gdx.util.app.ScreenCoreState2D;
import pama1234.gdx.util.app.UtilScreen;
import pama1234.gdx.util.listener.EntityListener;
import pama1234.gdx.util.wrapper.EntityCenter;
import pama1234.gdx.util.wrapper.EntityCenterAbstract;
import pama1234.server.app.DedicatedServer;
import pama1234.server.game.life.particle.core.CellServer;
import pama1234.server.game.net.LocalServerConfig;
import pama1234.server.game.net.RemoteServerConfig;
import pama1234.server.game.net.TransferManager;
import pama1234.util.Annotations.ScreenDescription;

@ScreenDescription("贪吃蛇游戏")
public class Screen0045 extends ScreenCoreState2D<StateCenter0004,StateEntity0004>{
  public enum GameMode{
    God,Survival;
  }
  public MainMenu mainMenu;

  public boolean paused;
  public InputData currentInput;
  public RealGameAndroidCtrl actrl;

  public TransferManager net;
  public LocalServerConfig localServerConfig;
  public RemoteServerConfig remoteServerConfig;

  //  public GameConfig gameConfig;
  public GameMode gameMode=GameMode.Survival;
  public boolean debug;
  public boolean debugMouseCord;
  public TextButton<?>[] constantGui;

  public DedicatedServer serverCore=new DedicatedServer();
  {
    // debug=true;
    //     debugMouseCord=true;
    // isAndroid=true;
    //    doUpdateThread=false;
//    System.setProperty("os.name","Pama");
  }
  public Screen0045(MainMenu mainMenu) {
    this.mainMenu=mainMenu;
  }
  @Override
  public void setup() {
    //TODO 进入游戏自动切换输入法

    // if(!isAndroid) InputContext.selectInputMethod(Locale.ENGLISH);

    backgroundColor(0);
    // backgroundColor(255);
    strokeWeight(CellServer.size/4);
    stroke(255);
    noStroke();
    setupCamera();

    if(isAndroid) {
      currentInput=new InputData();
      actrl=new RealGameAndroidCtrl(this);
      actrl.active=false;
      centerScreen.add.add(actrl);
    }

    net=new TransferManager();
    localServerConfig=new LocalServerConfig();
    remoteServerConfig=new RemoteServerConfig();

    constantGui=UiGenerator.genButtons_0005(this);
    centerScreenAddAll(constantGui);
    // 加载状态引擎并设置初始视图
    stateCenter=new StateCenter0004(this);
    State0004Util.loadState0004(this,stateCenter);
    state(stateCenter.startMenu);
  }
  public void setupCamera() {
    if(isAndroid) {
      cam2d.minScale=1/8f;
      cam2d.scale.des=0.75f;
      cam2d.point.f=0.1f;
      cam2d.point.des.set(0,-100);
    }else {
      cam2d.minScale=1f;
      cam2d.scale.des=2f;
      cam2d.point.des.set(0,-60);
    }
    cam2d.active(true);
    androidTouch.touchHoldToRightButtonTime=60;
  }
  public void activeACtrl(boolean in) {
    if(isAndroid) {
      actrl.active=in;
      if(in) actrl.addAll();
      else actrl.removeAll();
    }
    cam2d.activeDrag=!in;
  }
  @Override
  public void update() {}
  @Override
  public void display() {}
  @Override
  public void displayWithCam() {
    if(debugMouseCord) {
      // cross(mouse.x,mouse.y,4,4);
      textScale(1/2f);
      textColor(204);
      text(" x: "+Tools.getFloatString(mouse.x,5,2)+" y: "+Tools.getFloatString(mouse.y,5,2),mouse.x,mouse.y);
      text("ox: "+Tools.getIntString(mouse.ox,5)+"   oy: "+Tools.getIntString(mouse.oy,5),mouse.x,mouse.y+8);
      textScale(1);
    }
  }
  @Override
  public void frameResized() {}

  @Override
  public StateEntity0004 stateBack() {
    super.stateBack();
    LinkedList<StateEntity0004> stack=stateCenter.stack;
    if(!stack.isEmpty()) stack.pop();
    StateEntity0004 ts;
    if(!stack.isEmpty()) ts=stack.getLast();
    else ts=stateCenter.defaultState;
    // TODO 比较丑
    stateCenter.stackNotEmpty=ts!=stateCenter.defaultState;
    // System.out.println(ts);
    return state(ts);
  }
  public StateEntity0004 stateForward(StateEntity0004 in) {
    stateCenter.stack.push(in);
    stateCenter.stackNotEmpty=true;
    return state(in);
  }

  @Override
  public void dispose() {
    stateNull();
    super.dispose();
//    System.out.println("Screen0045.dispose");
//    // TODO 暂时的修复
//     Runtime.getRuntime().exit(0);

    //    if (this.doUpdateThread) {
    //      updateThread.stop();
    //    }
    //    Gdx.app.exit();
//    Gdx.files.internal("1");
  }

  @Override
  public EntityCenterAbstract<UtilScreen,EntityListener,?> createEntityCenter() {
    return new EntityCenter<>(this);
  }
}
