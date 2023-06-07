package pama1234.gdx.game.duel;

import static pama1234.app.game.server.duel.Config.neat;
import static pama1234.app.game.server.duel.util.Const.CANVAS_SIZE;

import java.io.IOException;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;

import pama1234.app.game.server.duel.Config;
import pama1234.app.game.server.duel.Config.GameMode;
import pama1234.gdx.game.duel.util.ai.nnet.ClientFisheyeVision;
import pama1234.gdx.game.duel.util.ai.nnet.NeatCenter;
import pama1234.gdx.game.duel.util.ai.nnet.NeatCenter.NetworkGroupParam;
import pama1234.gdx.game.duel.util.graphics.DemoInfo;
import pama1234.gdx.game.duel.util.input.ClientInputData;
import pama1234.gdx.game.duel.util.input.UiGenerator;
import pama1234.gdx.game.ui.util.TextButton;
import pama1234.gdx.util.app.ScreenCore2D;
import pama1234.gdx.util.element.Graphics;
import pama1234.gdx.util.info.MouseInfo;
import pama1234.gdx.util.info.TouchInfo;
import pama1234.math.Tools;
import pama1234.math.UtilMath;
import pama1234.util.localization.Localization;
import pama1234.util.net.SocketData;
import pama1234.util.protobuf.InputDataProto;
import pama1234.util.protobuf.InputDataProto.InputData;

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
 * Modified by: Pama1234 ( https://space.bilibili.com/646050693 )
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
  public class GameClient{
    // public DatagramSocket socket;
    public SocketData socketData;
  }
  public class ClientConfig{
    public String serverAddr;
    public int port;
  }
  public class LoginInfo{
    public String userName;
    public byte[] token;
  }
  //---
  public static final Localization localization=new Localization();
  // public static LocalBundleCenter bundleCenter;
  //---
  public TextButton<?>[] buttons;
  public ClientInputData currentInput;
  public ClientGameSystem system;
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
  public Config config;
  public FileHandle configFile;
  public Graphics graphics;
  //---
  public NeatCenter neatCenter;
  public NetworkGroupParam param;
  //---
  // public ShaderProgram polarShader;
  public ShaderProgram cartesianShader;
  public ClientFisheyeVision player_a,player_b;
  public int timeLimitConst=60*10;
  public int time,timeLimit=timeLimitConst;
  //---
  public GameClient gameClient;
  public ClientConfig clientConfig;
  public LoginInfo loginInfo;
  public InputDataProto.InputData.Builder inputDataBuilder;
  @Override
  public void init() {
    configFile=Gdx.files.local("data/config.yaml");
    config=loadConfig();
    super.init();
  }
  public Config loadConfig() {
    if(configFile.exists()) {
      return localization.yaml.loadAs(configFile.readString("UTF-8"),Config.class);
    }else {
      Gdx.files.local("data").mkdirs();
      return new Config().init(isAndroid);
    }
  }
  @Override
  public void setup() {
    TextUtil.used=TextUtil.gen_ch(this::textWidthNoScale);
    if(isAndroid) {
      buttons=UiGenerator.genButtons_0010(this);
      for(TextButton<?> e:buttons) centerScreen.add.add(e);
    }
    currentInput=new ClientInputData();
    //---
    if(config.mode==neat) {
      param=new NetworkGroupParam(32);
      neatCenter=new NeatCenter(param);
      //---
      graphics=new Graphics(this,CANVAS_SIZE,CANVAS_SIZE);
      String polarVisionVert=Gdx.files.internal("shader/main0005/vision-polar.vert").readString(),
        polarVisionFrag=Gdx.files.internal("shader/main0005/vision-polar.frag").readString();
      String cartesianVisionVert=Gdx.files.internal("shader/main0005/example.vert").readString(),
        cartesianVisionFrag=Gdx.files.internal("shader/main0005/vision-cartesian.frag").readString();
      cartesianShader=new ShaderProgram(cartesianVisionVert,cartesianVisionFrag);
      int ts=param.canvasSize;
      player_a=new ClientFisheyeVision(this,
        new ShaderProgram(polarVisionVert,polarVisionFrag),
        new Graphics(this,ts,ts));
      player_b=new ClientFisheyeVision(this,
        new ShaderProgram(polarVisionVert,polarVisionFrag),
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
    if(config.mode==neat) {
      cam2d.minScale=1/8f;
      cam2d.scaleUnit=1/8f;
      cam2d.scale.pos=cam2d.scale.des=(isAndroid?0.25f:1)*0.6f;
    }else {
      if(isAndroid) cam2d.scale.pos=cam2d.scale.des=0.25f;
      cam2d.activeDrag=false;
      cam2d.activeScrollZoom=cam2d.activeTouchZoom=false;
    }
    if(config.gameMode==GameMode.OnLine) onlineGameSetup();
  }
  public void onlineGameSetup() {
    inputDataBuilder=InputDataProto.InputData.newBuilder();
  }
  public void onlineGameUpdate() {
    currentInput.copyToProto(inputDataBuilder);
    InputData inputData=inputDataBuilder.build();
    try {
      inputData.writeTo(gameClient.socketData.o);
    }catch(IOException e) {
      e.printStackTrace();
    }
  }
  @Override
  public void dispose() {
    super.dispose();
    configFile.writeString(localization.yaml.dumpAsMap(config),false);
  }
  public void newGame(boolean demo,boolean instruction) {
    system=new ClientGameSystem(this,demo,instruction);
  }
  @Override
  public void display() {
    system.displayScreen();
    if(config.mode==neat) {
      textScale(UtilMath.ceil(UtilMath.max(1,pus/3f)));
      float ts=textScale()*textSize();
      text(Tools.getFloatString(time,5,0)+"ms -> "+Tools.getFloatString(timeLimit,5,0)+"ms",0,0);
      text("real time score",0,ts);
      text("a - "+Tools.getFloatString(system.myGroup.player.engine.getScore(1)),0,ts*2);
      text("b - "+Tools.getFloatString(system.otherGroup.player.engine.getScore(1)),0,ts*3);
      text("final score",0,ts*4);
      text("a - "+Tools.getFloatString(system.myGroup.player.engine.getScore(0)),0,ts*5);
      text("b - "+Tools.getFloatString(system.otherGroup.player.engine.getScore(0)),0,ts*6);
    }
  }
  @Override
  public void displayWithCam() {
    doStroke();
    if(config.mode==neat) {
      graphics.begin();
      background(255);
      system.display();
      graphics.end();
      player_a.render();
      player_b.render();
      //---
      cartesianShader.bind();
      cartesianShader.setUniformf("u_dist",player_a.camX/CANVAS_SIZE,player_a.camY/CANVAS_SIZE);
      image(player_a.graphics.texture,-656,0,CANVAS_SIZE,CANVAS_SIZE,cartesianShader);
      //---
      image(graphics.texture,0,0,CANVAS_SIZE,CANVAS_SIZE);
      //---
      cartesianShader.bind();
      cartesianShader.setUniformf("u_dist",player_b.camX/CANVAS_SIZE,player_b.camY/CANVAS_SIZE);
      image(player_b.graphics.texture,656,0,CANVAS_SIZE,CANVAS_SIZE,cartesianShader);
      //---
      image(player_a.graphics.texture,-656,-656,CANVAS_SIZE,CANVAS_SIZE);
      image(player_b.graphics.texture,656,-656,CANVAS_SIZE,CANVAS_SIZE);
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
      //---
      if(config.gameMode==GameMode.OnLine) onlineGameUpdate();
      //---
      system.update();
      //---
      if(config.mode==neat) {
        if(system.stateIndex==ClientGameSystem.play) {
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
    currentInput.keyPressed(this,key,keyCode);
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
    super.strokeWeight(config.mode==neat?in:in*strokeUnit);
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
  public void stateChangeEvent(ClientGameSystem system,int stateIndex) {
    if(system.stateIndex==ClientGameSystem.play) time=0;
    else if(system.stateIndex==ClientGameSystem.result) {
      system.myGroup.player.engine.setScore(0,system.currentState.getScore(system.myGroup.id));
      system.otherGroup.player.engine.setScore(0,system.currentState.getScore(system.otherGroup.id));
    }
  }
}