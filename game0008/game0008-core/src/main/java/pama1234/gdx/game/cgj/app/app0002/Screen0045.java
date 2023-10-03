package pama1234.gdx.game.cgj.app.app0002;

import pama1234.gdx.game.cgj.life.particle.Cell;
import pama1234.gdx.game.cgj.life.particle.GameConfig;
import pama1234.gdx.game.cgj.state0004.State0004Util;
import pama1234.gdx.game.cgj.state0004.State0004Util.StateCenter0004;
import pama1234.gdx.game.cgj.state0004.State0004Util.StateEntity0004;
import pama1234.gdx.game.cgj.ui.generator.UiGenerator;
import pama1234.gdx.game.cgj.util.input.InputData;
import pama1234.gdx.game.cgj.util.input.RealGameAndroidCtrl;
import pama1234.gdx.game.ui.element.TextButton;
import pama1234.gdx.util.app.ScreenCoreState2D;
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

  public GameConfig gameConfig;
  public GameMode gameMode=GameMode.Survival;
  public boolean debug;
  public TextButton<?>[] returnButton;
  {
    // debug=true;
    // isAndroid=true;
  }
  public Screen0045(MainMenu mainMenu) {
    this.mainMenu=mainMenu;
  }
  @Override
  public void setup() {
    backgroundColor(0);
    // backgroundColor(255);
    strokeWeight(Cell.size/4);
    stroke(255);
    noStroke();
    setupCamera();

    if(isAndroid) {
      currentInput=new InputData();
      actrl=new RealGameAndroidCtrl(this);
      actrl.active=false;
      centerScreen.add.add(actrl);
    }

    returnButton=UiGenerator.genButtons_0005(this);
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
  public void activeActrl(boolean in) {
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
  public void displayWithCam() {}
  @Override
  public void frameResized() {}
}
