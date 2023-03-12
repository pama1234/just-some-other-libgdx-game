package pama1234.gdx.game.duel;

import com.badlogic.gdx.Input.Buttons;

import pama1234.gdx.game.duel.util.graphics.DrawUtil;
import pama1234.gdx.game.duel.util.input.InputData;
import pama1234.gdx.game.duel.util.input.UiGenerator;
import pama1234.gdx.game.ui.util.TextButton;
import pama1234.gdx.util.app.ScreenCore2D;
import pama1234.gdx.util.info.MouseInfo;
import pama1234.gdx.util.info.TouchInfo;

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
 * Modified by: Pama1234 (https://space.bilibili.com/646050693)
 * </p>
 * Altered with self-made framework v0.0.1 Altered with Processing-Libgdx v0.0.1
 * </p>
 * </p>
 *
 * The font "Unifont" https://unifoundry.com/unifont/ is part of the GNU Project.
 */
public class Duel extends ScreenCore2D{
  public static final float IDEAL_FRAME_RATE=60;
  public static final int INTERNAL_CANVAS_SIDE_LENGTH=640;
  public TextButton<?>[] buttons;
  public InputData currentInput;
  public GameSystem system;
  public int smallFontSize=16,largeFontSize=128;
  public boolean paused;
  public int canvasSideLength=INTERNAL_CANVAS_SIDE_LENGTH;
  public TouchInfo moveCtrl;
  @Override
  public void setup() {
    if(isAndroid) {
      buttons=UiGenerator.genButtons_0010(this);
      for(TextButton<?> e:buttons) centerScreen.add.add(e);
    }
    currentInput=new InputData();
    newGame(true,true); // demo play (computer vs computer), shows instruction window
    //---
    setTextColor(0);
    //---
    cam.point.des.set(canvasSideLength/2f,canvasSideLength/2f);
    cam.point.pos.set(cam.point.des);
    if(isAndroid) cam2d.scale.pos=cam2d.scale.des=0.25f;
    cam2d.activeDrag=false;
    cam2d.activeScrollZoom=cam2d.activeTouchZoom=false;
  }
  @Override
  public void display() {
    if(system.demoPlay&&system.showsInstructionWindow) DrawUtil.displayDemo(this);
  }
  @Override
  public void displayWithCam() {
    doStroke();
    system.display();
    clearMatrix();
    if(isAndroid) {
      if(moveCtrl!=null) {
        // doStroke();
        cross(moveCtrl.sx,moveCtrl.sy,32,32);
        line(moveCtrl.x,moveCtrl.y,moveCtrl.sx,moveCtrl.sy);
        cross(moveCtrl.x,moveCtrl.y,16,16);
        // noStroke();
      }
    }
    noStroke();
    doFill();
  }
  @Override
  public void update() {
    if(!paused) {
      if(isAndroid) {
        if(moveCtrl!=null) currentInput.targetTouchMoved(moveCtrl.x-moveCtrl.sx,moveCtrl.y-moveCtrl.sy);
      }
      system.update();
    }
  }
  public void newGame(boolean demo,boolean instruction) {
    system=new GameSystem(this,demo,instruction);
  }
  @Override
  public void mousePressed(MouseInfo info) {
    if(info.button==Buttons.LEFT) system.showsInstructionWindow=!system.showsInstructionWindow;
  }
  @Override
  public void keyPressed(char key,int keyCode) {
    currentInput.keyPressedEvent(this,key,keyCode);
  }
  public void doPause() {
    paused=!paused;
  }
  @Override
  public void keyReleased(char key,int keyCode) {
    currentInput.keyReleased(this,key,keyCode);
  }
  @Override
  public void frameResized() {}
  @Override
  public void touchStarted(TouchInfo info) {
    if(isAndroid) {
      if(info.osx<width/2f) {
        if(moveCtrl==null) moveCtrl=info;
      }
    }
  }
  @Override
  public void touchMoved(TouchInfo info) {}
  @Override
  public void touchEnded(TouchInfo info) {
    if(moveCtrl==info) moveCtrl=null;
  }
}