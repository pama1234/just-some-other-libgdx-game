package pama1234.gdx.game.duel;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;

import pama1234.gdx.game.duel.util.actor.AbstractPlayerActor;
import pama1234.gdx.game.duel.util.ai.nnet.FisheyeVision;
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
  public static final int game=0,neat=1;
  public int mode=neat;
  public Graphics graphics;
  public ShaderProgram shader;
  // public Graphics fisheye;
  // public float camX,camY;
  public FisheyeVision player_a,player_b;
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
    cam2d.scale.pos=cam2d.scale.des=(isAndroid?0.25f:1)*0.8f;
    cam2d.activeDrag=false;
    cam2d.activeScrollZoom=cam2d.activeTouchZoom=false;
    //---
    if(mode==neat) {
      graphics=new Graphics(this,CANVAS_SIZE,CANVAS_SIZE);
      shader=new ShaderProgram(
        Gdx.files.internal("shader/main0005/vision.vert").readString(),
        Gdx.files.internal("shader/main0005/vision.frag").readString());
      // fisheye=new Graphics(this,INTERNAL_CANVAS_SIDE_LENGTH,INTERNAL_CANVAS_SIDE_LENGTH);
      // fisheye=new Graphics(this,256,256);
      player_a=new FisheyeVision(this,new ShaderProgram(shader.getVertexShaderSource(),shader.getFragmentShaderSource()),new Graphics(this,256,256));
    }
  }
  @Override
  public void display() {
    system.displayScreen();
  }
  @Override
  public void displayWithCam() {
    doStroke();
    if(mode==neat) {
      graphics.begin();
      background(255);
      system.display();
      graphics.end();
      player_a.render();
      image(player_a.graphics.texture,340,0,CANVAS_SIZE,CANVAS_SIZE);
      image(graphics.texture,-340,0,CANVAS_SIZE,CANVAS_SIZE);
      // image(graphics.texture,-graphics.width()/2f,-graphics.height()/2f);
    }else {
      system.display();
    }
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
      if(mode==neat) {
        // shader.bind();
        // System.out.println(system.myGroup.player.xPosition);
        AbstractPlayerActor player=system.myGroup.player;
        player_a.update(player);
        // if(Float.isFinite(player.xPosition)&&
        //   Float.isFinite(player.yPosition)) {
        //   camX=player.xPosition;
        //   camY=player.yPosition;
        // }
        // // System.out.println(camX);
        // shader.setUniformf("u_dist",camX/CANVAS_SIZE,1-camY/CANVAS_SIZE);
      }
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
    super.strokeWeight(mode==neat?in:in*strokeUnit);
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