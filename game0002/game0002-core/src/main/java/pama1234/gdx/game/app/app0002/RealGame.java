package pama1234.gdx.game.app.app0002;

import pama1234.gdx.game.ui.generator.UiGenerator;
import pama1234.gdx.game.ui.util.TextButton;
import pama1234.gdx.game.util.input.InputData;
import pama1234.gdx.game.util.legacy.Cell;
import pama1234.gdx.game.util.legacy.GamePage;
import pama1234.gdx.game.util.legacy.Info;
import pama1234.gdx.game.util.legacy.PageCenter;
import pama1234.gdx.game.util.legacy.SettingsPage;
import pama1234.gdx.game.util.legacy.StartPage;
import pama1234.gdx.util.app.ScreenCore2D;
import pama1234.gdx.util.element.Graphics;
import pama1234.gdx.util.entity.Entity;
import pama1234.gdx.util.info.TouchInfo;
import pama1234.math.UtilMath;

/**
 * A REAL GAME that is not "app in Application category"
 */
public class RealGame extends ScreenCore2D{
  public static final int cam_box_r=720;
  public Info info;
  public PageCenter pageCenter;
  public MainMenu mainMenu;
  //---
  public boolean paused;
  public InputData currentInput;
  public AndroidCtrl actrl;
  {
    // isAndroid=true;
  }
  public RealGame(MainMenu mainMenu) {
    this.mainMenu=mainMenu;
  }
  @Override
  public void setup() {
    if(isAndroid) {
      currentInput=new InputData();
      actrl=new AndroidCtrl(this);
      actrl.active=false;
      centerScreen.add.add(actrl);
    }
    // backgroundColor(255);
    backgroundColor(0);
    strokeWeight(Cell.size/4);
    stroke(255);
    noStroke();
    centerCam.add.add(pageCenter=new PageCenter(this,new StartPage(this),-640,0));
    pageCenter.list.add(new GamePage(this));
    pageCenter.list.add(new SettingsPage(this));
    pageCenter.refresh();
    pageCenter.postSetDes();
    centerCam.add.add(info=new Info(this,520,-320));
    setupCamera();
  }
  public void setupCamera() {
    if(isAndroid) {
      cam2d.minScale=1/8f;
      cam2d.scale.des=0.5f;
    }
    cam2d.point.des.y=-60;
    // cam2d.activeDrag=false;
    // cam2d.activeScrollZoom=cam2d.activeTouchZoom=false;
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
  public static class AndroidCtrl extends Entity<RealGame>{
    public float maxDist,maxSpeed;
    public float magCache;
    public float dxCache,dyCache;
    public TouchInfo moveCtrl;
    public float scale;
    public boolean active=true;
    public AndroidCtrl(RealGame p) {
      super(p);
      maxSpeed=1f;
      scale=2;
      updateMaxDist();
      init();
    }
    @Override
    public void init() {
      p.textButtons=UiGenerator.genButtons_0003(p);
      for(TextButton<?> e:p.textButtons) p.centerScreen.add.add(e);
    }
    @Override
    public void display() {
      if(moveCtrl!=null) {
        p.doStroke();
        p.stroke(255);
        p.strokeWeight(2*scale);
        p.cross(moveCtrl.osx,moveCtrl.osy,32*scale,32*scale);
        p.line(moveCtrl.ox,moveCtrl.oy,moveCtrl.osx,moveCtrl.osy);
        p.cross(moveCtrl.ox,moveCtrl.oy,16*scale,16*scale);
        float deg=UtilMath.deg(UtilMath.atan2(dxCache,dyCache));
        p.arc(moveCtrl.osx,moveCtrl.osy,magCache,45-deg,90);
        p.noStroke();
      }
    }
    @Override
    public void update() {
      if(!p.paused) {
        if(moveCtrl!=null) {
          dxCache=moveCtrl.ox-moveCtrl.osx;
          dyCache=moveCtrl.oy-moveCtrl.osy;
          p.currentInput.targetTouchMoved(dxCache,dyCache,magCache=UtilMath.min(UtilMath.mag(dxCache,dyCache),maxDist));
        }
      }
    }
    @Override
    public void touchStarted(TouchInfo info) {
      if(active&&info.osx<p.width/3f) {
        if(moveCtrl==null) moveCtrl=info;
      }
    }
    @Override
    public void touchEnded(TouchInfo info) {
      if(moveCtrl==info) {
        moveCtrl=null;
        p.currentInput.dx=0;
        p.currentInput.dy=0;
        magCache=0;
      }
    }
    @Override
    public void frameResized(int w,int h) {
      updateMaxDist();
    }
    public void updateMaxDist() {
      maxDist=p.u*scale;
    }
  }
}
