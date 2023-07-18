package pama1234.gdx.game.cgj.app.app0002;

import pama1234.gdx.game.cgj.state0004.State0004Util;
import pama1234.gdx.game.cgj.state0004.State0004Util.StateCenter0004;
import pama1234.gdx.game.cgj.state0004.State0004Util.StateEntity0004;
import pama1234.gdx.game.cgj.ui.generator.UiGenerator;
import pama1234.gdx.game.cgj.util.input.InputData;
import pama1234.gdx.game.cgj.util.input.RealGameAndroidCtrl;
import pama1234.gdx.game.cgj.util.legacy.Cell;
import pama1234.gdx.game.ui.util.TextButton;
import pama1234.gdx.util.app.ScreenCoreState2D;
import pama1234.gdx.util.element.Graphics;

/**
 * A REAL GAME that is not "app in Application category"
 */
public class RealGame0002 extends ScreenCoreState2D<StateCenter0004,StateEntity0004>{
  public enum GameMode{
    God,Survival;
  }
  public MainMenu mainMenu;
  //---
  public boolean paused;
  public InputData currentInput;
  public RealGameAndroidCtrl actrl;
  //---
  public GameMode gameMode=GameMode.Survival;
  // public boolean debug=true;
  public boolean debug;
  public TextButton<?>[] returnButton;
  {
    // isAndroid=true;
  }
  public RealGame0002(MainMenu mainMenu) {
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
    //---
    if(isAndroid) {
      currentInput=new InputData();
      actrl=new RealGameAndroidCtrl(this);
      actrl.active=false;
      centerScreen.add.add(actrl);
    }
    //---
    returnButton=UiGenerator.genButtons_0005(this);
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
  /**
   * use new Graphics(p,
   * 
   * @param w
   * @param h
   * @return
   */
  @Deprecated
  public Graphics createGraphics(int w,int h) {
    return new Graphics(this,w,h);
  }
}
