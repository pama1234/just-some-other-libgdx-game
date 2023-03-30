package pama1234.gdx.game.duel;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;

import pama1234.gdx.game.duel.util.graphics.DemoInfo;
import pama1234.gdx.game.duel.util.input.InputData;
import pama1234.gdx.game.duel.util.input.UiGenerator;
import pama1234.gdx.game.ui.util.TextButton;
import pama1234.gdx.util.app.ScreenCore2D;
import pama1234.gdx.util.element.Graphics;
import pama1234.gdx.util.info.MouseInfo;
import pama1234.gdx.util.info.TouchInfo;
import pama1234.math.UtilMath;

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
 * Altered with self-made framework v0.0.1
 * </p>
 * Altered with Processing-Libgdx v0.0.1
 * </p>
 * </p>
 *
 * The font "Unifont" https://unifoundry.com/unifont/ is part of the GNU Project.
 */
public class Duel extends ScreenCore2D{
  public static final float IDEAL_FRAME_RATE=60;
  public static final int CANVAS_SIZE=640;
  public TextButton<?>[] buttons;
  public InputData currentInput;
  public GameSystem system;
  public boolean paused;
  public int canvasSideLength=CANVAS_SIZE;
  public TouchInfo moveCtrl;
  //---
  public DemoInfo demoInfo;
  public float maxDist;
  public float magCache;
  public float dxCache,dyCache;
  public float strokeUnit;
  //---
  public ShaderProgram shader;
  public Graphics graphics,fisheye;
  @Override
  public void setup() {
    TextUtil.used=TextUtil.gen_ch(this::textWidthNoScale);
    if(isAndroid) {
      buttons=UiGenerator.genButtons_0010(this);
      for(TextButton<?> e:buttons) centerScreen.add.add(e);
    }
    currentInput=new InputData();
    newGame(true,true); // demo play (computer vs computer), shows instruction window
    //---
    setTextColor(0);
    demoInfo=new DemoInfo(this);
    //---
    cam.point.des.set(canvasSideLength/2f,canvasSideLength/2f);
    cam.point.pos.set(cam.point.des);
    if(isAndroid) cam2d.scale.pos=cam2d.scale.des=0.25f;
    cam2d.activeDrag=false;
    cam2d.activeScrollZoom=cam2d.activeTouchZoom=false;
    //---
    shader=new ShaderProgram(
      Gdx.files.internal("shader/main0005/vision.vert").readString(),
      Gdx.files.internal("shader/main0005/vision.frag").readString());
    graphics=new Graphics(this,CANVAS_SIZE,CANVAS_SIZE);
    // fisheye=new Graphics(this,INTERNAL_CANVAS_SIDE_LENGTH,INTERNAL_CANVAS_SIDE_LENGTH);
    fisheye=new Graphics(this,256,256);
  }
  @Override
  public void display() {
    system.displayScreen();
  }
  @Override
  public void displayWithCam() {
    doStroke();
    graphics.begin();
    background(255);
    system.display();
    graphics.end();
    fisheye.begin();
    imageBatch.begin();
    imageBatch.setShader(shader);
    // shaderUpdate();
    imageBatch.draw(graphics.texture,0,0,fisheye.width(),fisheye.height());
    imageBatch.end();
    imageBatch.setShader(null);
    fisheye.end();
    image(fisheye.texture,0,0,CANVAS_SIZE,CANVAS_SIZE);
    // image(graphics.texture,0,0);
    // image(graphics.texture,-graphics.width()/2f,-graphics.height()/2f);
    clearMatrix();
    if(isAndroid) {
      if(moveCtrl!=null) {
        // doStroke();
        stroke(0);
        strokeWeight(2);
        cross(moveCtrl.sx,moveCtrl.sy,32,32);
        line(moveCtrl.x,moveCtrl.y,moveCtrl.sx,moveCtrl.sy);
        cross(moveCtrl.x,moveCtrl.y,16,16);
        float deg=UtilMath.deg(UtilMath.atan2(dxCache,dyCache));
        arc(moveCtrl.sx,moveCtrl.sy,magCache,45-deg,90);
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
        if(moveCtrl!=null) {
          dxCache=moveCtrl.x-moveCtrl.sx;
          dyCache=moveCtrl.y-moveCtrl.sy;
          currentInput.targetTouchMoved(dxCache,dyCache,magCache=UtilMath.min(UtilMath.mag(dxCache,dyCache),maxDist));
        }
      }
      system.update();
      //---
      shader.bind();
      // System.out.println(system.myGroup.player.xPosition);
      shader.setUniformf("u_dist",system.myGroup.player.xPosition/CANVAS_SIZE,1-system.myGroup.player.yPosition/CANVAS_SIZE);
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
  public void frameResized() {
    maxDist=u;
    strokeUnit=isAndroid?u/128f:u/64f;
  }
  @Override
  public void strokeWeight(float in) {
    super.strokeWeight(in*strokeUnit);
  }
  public void strokeWeightOriginal(float in) {
    super.strokeWeight(in);
  }
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
    if(moveCtrl==info) {
      moveCtrl=null;
      currentInput.dx=0;
      currentInput.dy=0;
      magCache=0;
    }
  }
}