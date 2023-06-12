package pama1234.gdx.game.state.state0002;

import static pama1234.app.game.server.duel.ServerConfigData.game;
import static pama1234.app.game.server.duel.ServerConfigData.neat;

import java.io.IOException;

import com.badlogic.gdx.Input.Buttons;

import pama1234.app.game.server.duel.ServerConfigData.GameMode;
import pama1234.gdx.game.duel.ClientGameSystem;
import pama1234.gdx.game.duel.Duel;
import pama1234.gdx.game.duel.State0002Util.StateEntity0002;
import pama1234.gdx.game.duel.util.input.AndroidCtrl;
import pama1234.gdx.game.duel.util.input.ClientInputData;
import pama1234.gdx.game.duel.util.input.UiGenerator;
import pama1234.gdx.game.ui.util.TextButton;
import pama1234.gdx.util.info.MouseInfo;
import pama1234.util.protobuf.InputDataProto;
import pama1234.util.protobuf.InputDataProto.InputData;

public class Game extends StateEntity0002{
  public TextButton<?>[] buttons;
  public ClientInputData currentInput;
  public ClientGameSystem system;
  public boolean paused;
  public AndroidCtrl actrl;
  //---
  public Game(Duel p,int id) {
    super(p,id);
    init();
  }
  @Override
  public void init() {
    if(p.isAndroid) {
      actrl=new AndroidCtrl(p,this);
      actrl.init();
      // p.centerCam.add.add(actrl);
    }
    currentInput=new ClientInputData();
    //---
    newGame(true,true); // demo play (computer vs computer), shows instruction window
    //---
    if(p.config.gameMode==GameMode.OnLine) onlineGameSetup();
    buttons=UiGenerator.genButtons_0002(p);
    for(TextButton<?> e:buttons) p.centerScreen.add.add(e);
  }
  @Override
  public void display() {
    system.displayScreen();
  }
  @Override
  public void update() {
    if(!paused) {
      //---
      if(p.config.gameMode==GameMode.OnLine) onlineGameUpdate();
      //---
      system.update();
      //---
      if(p.config.mode==neat) p.neatE.update();
    }
  }
  @Override
  public void displayCam() {
    p.doStroke();
    if(p.config.mode==neat) p.neatE.displayCam();
    else {
      system.display();
    }
    p.clearMatrix();
    p.noStroke();
    p.doFill();
  }
  public void onlineGameSetup() {
    p.inputDataBuilder=InputDataProto.InputData.newBuilder();
  }
  public void onlineGameUpdate() {
    currentInput.copyToProto(p.inputDataBuilder);
    InputData inputData=p.inputDataBuilder.build();
    try {
      inputData.writeTo(p.gameClient.socketData.o);
    }catch(IOException e) {
      e.printStackTrace();
    }
  }
  public void newGame(boolean demo,boolean instruction) {
    system=new ClientGameSystem(p,this,demo,instruction);
  }
  public void doPause() {
    paused=!paused;
  }
  @Override
  public void mousePressed(MouseInfo info) {
    if(info.button==Buttons.LEFT) system.showsInstructionWindow=!system.showsInstructionWindow;
  }
  @Override
  public void keyPressed(char key,int keyCode) {
    currentInput.keyPressed(p,key,keyCode);
  }
  @Override
  public void keyReleased(char key,int keyCode) {
    currentInput.keyReleased(p,key,keyCode);
  }
  @Override
  public void from(StateEntity0002 in) {
    paused=false;
    if(p.config.mode==game) {
      p.cam2d.activeDrag=false;
      p.cam2d.activeScrollZoom=p.cam2d.activeTouchZoom=false;
    }
    if(actrl!=null) {
      actrl.resume();
      p.centerCam.add.add(actrl);
    }
  }
  @Override
  public void to(StateEntity0002 in) {
    paused=true;
    if(p.config.mode==game) {
      p.cam2d.activeDrag=true;
      p.cam2d.activeScrollZoom=p.cam2d.activeTouchZoom=true;
    }
    if(actrl!=null) {
      actrl.pause();
      p.centerCam.remove.add(actrl);
    }
  }
}