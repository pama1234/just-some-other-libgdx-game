package pama1234.gdx.game.duel;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;

import pama1234.gdx.game.duel.util.ai.nnet.FisheyeVision;
import pama1234.gdx.game.duel.util.ai.nnet.NeatCenter;
import pama1234.gdx.game.duel.util.ai.nnet.NeatCenter.NetworkGroupParam;
import pama1234.gdx.game.duel.util.graphics.DemoInfo;
import pama1234.gdx.game.duel.util.input.InputData;
import pama1234.gdx.game.duel.util.input.UiGenerator;
import pama1234.gdx.game.ui.util.TextButton;
import pama1234.gdx.util.app.ScreenCore2D;
import pama1234.gdx.util.element.Graphics;
import pama1234.gdx.util.info.MouseInfo;
import pama1234.gdx.util.info.TouchInfo;
import pama1234.math.Tools;
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
  public int mode=isAndroid?game:neat;
  // public int mode=game;
  public Graphics graphics;
  //---
  public NeatCenter neatCenter;
  public NetworkGroupParam param;
  //---
  public ShaderProgram shader;
  public String visionVert,visionFrag;
  public FisheyeVision player_a,player_b;
  public int timeLimitConst=60*10;
  public int time,timeLimit=timeLimitConst;
  @Override
  public void setup() {
    TextUtil.used=TextUtil.gen_ch(this::textWidthNoScale);
    if(isAndroid) {
      buttons=UiGenerator.genButtons_0010(this);
      for(TextButton<?> e:buttons) centerScreen.add.add(e);
    }
    currentInput=new InputData();
    //---
    if(mode==neat) {
      param=new NetworkGroupParam(32);
      neatCenter=new NeatCenter(param);
      //---
      graphics=new Graphics(this,CANVAS_SIZE,CANVAS_SIZE);
      // graphics.texture.setFilter(TextureFilter.Linear,TextureFilter.Linear);
      visionVert=Gdx.files.internal("shader/main0005/vision.vert").readString();
      visionFrag=Gdx.files.internal("shader/main0005/vision.frag").readString();
      int ts=param.canvasSize;
      player_a=new FisheyeVision(this,
        new ShaderProgram(visionVert,visionFrag),
        new Graphics(this,ts,ts));
      player_b=new FisheyeVision(this,
        new ShaderProgram(visionVert,visionFrag),
        new Graphics(this,ts,ts));
    }
    //---
    newGame(true,true); // demo play (computer vs computer), shows instruction window
    //---
    setTextColor(0);
    demoInfo=new DemoInfo(this);
    //---
    cam.point.des.set(canvasSideLength/2f,canvasSideLength/2f);
    cam.point.pos.set(cam.point.des);
    if(mode==neat) cam2d.scale.pos=cam2d.scale.des=(isAndroid?0.25f:1)*0.6f;
    else if(isAndroid) cam2d.scale.pos=cam2d.scale.des=0.25f;
    cam2d.activeDrag=false;
    cam2d.activeScrollZoom=cam2d.activeTouchZoom=false;
  }
  public void newGame(boolean demo,boolean instruction) {
    system=new GameSystem(this,demo,instruction);
  }
  @Override
  public void display() {
    system.displayScreen();
    if(mode==neat) {
      text(Tools.getFloatString(time,5,0)+"ms -> "+Tools.getFloatString(timeLimit,5,0)+"ms",0,0);
      text("real time score",0,pu);
      text("a - "+Tools.getFloatString(system.myGroup.player.engine.getScore(1)),0,pu*2);
      text("b - "+Tools.getFloatString(system.otherGroup.player.engine.getScore(1)),0,pu*3);
    }
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
      player_b.render();
      image(player_a.graphics.texture,-656,0,CANVAS_SIZE,CANVAS_SIZE);
      image(graphics.texture,0,0,CANVAS_SIZE,CANVAS_SIZE);
      image(player_b.graphics.texture,656,0,CANVAS_SIZE,CANVAS_SIZE);
      clearMatrix();
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
        if(system.stateIndex==GameSystem.play) {
          time++;
          system.myGroup.player.engine.setScore(1,system.currentState.getScore(system.myGroup.id));
          system.otherGroup.player.engine.setScore(1,system.currentState.getScore(system.otherGroup.id));
          if(time>timeLimit) {
            timeLimit=timeLimitConst;
            newGame(true,false);
          }
        }
        player_a.update(system.myGroup.player);
        player_b.update(system.otherGroup.player);
      }
    }
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
  public void stateChangeEvent(GameSystem system,int stateIndex) {
    if(system.stateIndex==GameSystem.play) time=0;
    else if(system.stateIndex==GameSystem.result) {
      system.myGroup.player.engine.setScore(0,system.currentState.getScore(system.myGroup.id));
      system.otherGroup.player.engine.setScore(0,system.currentState.getScore(system.otherGroup.id));
    }
  }
}