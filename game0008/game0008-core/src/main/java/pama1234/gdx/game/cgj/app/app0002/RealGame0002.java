package pama1234.gdx.game.cgj.app.app0002;

import pama1234.gdx.game.cgj.util.input.InputData;
import pama1234.gdx.game.cgj.util.input.RealGameAndroidCtrl;
import pama1234.gdx.game.cgj.util.legacy.Cell;
import pama1234.gdx.game.cgj.util.legacy.GamePage;
import pama1234.gdx.game.cgj.util.legacy.PageCenter;
import pama1234.gdx.game.cgj.util.legacy.SettingsPage;
import pama1234.gdx.game.cgj.util.legacy.StartPage;
import pama1234.gdx.game.ui.util.TextButtonCam;
import pama1234.gdx.util.app.ScreenCore2D;
import pama1234.gdx.util.element.Graphics;

/**
 * A REAL GAME that is not "app in Application category"
 */
public class RealGame0002 extends ScreenCore2D{
  public enum GameMode{
    God,Survival;
  }
  public static final int cam_box_r=720;
  public PageCenter pageCenter;
  public MainMenu mainMenu;
  //---
  public boolean paused;
  public InputData currentInput;
  public RealGameAndroidCtrl actrl;
  //---
  public GameMode gameMode=GameMode.Survival;
  // public boolean debug=true;
  public boolean debug;
  public GamePage gamePage;
  public StartPage startPage;
  public SettingsPage settingsPage;
  // public TextButtonCam<RealGame0002> startGame;
  public TextButtonCam<?>[] textButtonCams;
  {
    // isAndroid=true;
  }
  public RealGame0002(MainMenu mainMenu) {
    this.mainMenu=mainMenu;
  }
  @Override
  public void setup() {
    if(isAndroid) {
      currentInput=new InputData();
      actrl=new RealGameAndroidCtrl(this);
      actrl.active=false;
      centerScreen.add.add(actrl);
    }
    // backgroundColor(0);
    backgroundColor(255);
    strokeWeight(Cell.size/4);
    stroke(255);
    noStroke();
    centerCam.add.add(pageCenter=new PageCenter(this,startPage=new StartPage(this),-640,0));
    pageCenter.list.add(gamePage=new GamePage(this));
    pageCenter.list.add(settingsPage=new SettingsPage(this));
    setupCamera();
    textButtonCams=new TextButtonCam[] {
      new TextButtonCam<RealGame0002>(this,true,()->true,self-> {},self-> {},self-> {
        pageCenter.setSelect(gamePage);
        centerCamRemoveAll(textButtonCams);
      },self->self.text="开始游戏",()->18,()->-40,()->-60),
      new TextButtonCam<RealGame0002>(this,true,()->true,self-> {},self-> {},self-> {
        pageCenter.setSelect(settingsPage);
        centerCamRemoveAll(textButtonCams);
      },self->self.text="  设置  ",()->18,()->-40,()->-40),
    };
    centerCamAddAll(textButtonCams);
  }
  public void setupCamera() {
    if(isAndroid) {
      cam2d.minScale=1/8f;
      cam2d.scale.des=0.75f;
      cam2d.point.f=0.1f;
      cam2d.point.des.y=-100;
    }else {
      cam2d.minScale=1f;
      cam2d.scale.des=2f;
      cam2d.point.des.y=-60;
    }
  }
  public void activeActrl(boolean in) {
    if(isAndroid) actrl.active=in;
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
