package pama1234.gdx.game.app.app0002;

import pama1234.gdx.game.util.legacy.Cell;
import pama1234.gdx.game.util.legacy.GamePage;
import pama1234.gdx.game.util.legacy.Info;
import pama1234.gdx.game.util.legacy.PageCenter;
import pama1234.gdx.game.util.legacy.SettingsPage;
import pama1234.gdx.game.util.legacy.StartPage;
import pama1234.gdx.util.app.ScreenCore2D;
import pama1234.gdx.util.element.Graphics;

/**
 * A REAL GAME that is not "app in Application category"
 */
public class RealGame extends ScreenCore2D{
  public static final int cam_box_r=720;
  public Info info;
  public PageCenter pageCenter;
  public MainMenu mainMenu;
  public RealGame(MainMenu mainMenu) {
    this.mainMenu=mainMenu;
  }
  @Override
  public void setup() {
    // backgroundColor(255);
    backgroundColor(0);
    strokeWeight(Cell.size/4);
    noStroke();
    centerCam.add.add(pageCenter=new PageCenter(this,new StartPage(this),-640,0));
    pageCenter.list.add(new GamePage(this));
    pageCenter.list.add(new SettingsPage(this));
    pageCenter.refresh();
    pageCenter.postSetDes();
    centerCam.add.add(info=new Info(this,520,-320));
  }
  @Override
  public void update() {}
  @Override
  public void display() {
    // rect(0,0,u,u);
  }
  @Override
  public void displayWithCam() {
    // rect(0,0,16,16);
  }
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
