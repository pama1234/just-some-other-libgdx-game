package pama1234.gdx.game.state.state0002;

import static pama1234.app.game.server.duel.ServerConfigData.game;
import static pama1234.app.game.server.duel.ServerConfigData.neat;

import java.io.IOException;
import java.util.List;

import com.badlogic.gdx.Input.Buttons;

import pama1234.app.game.server.duel.ServerConfigData.GameMode;
import pama1234.app.game.server.duel.util.actor.ActorGroup;
import pama1234.app.game.server.duel.util.arrow.AbstractArrowActor;
import pama1234.gdx.game.duel.ClientGameSystem;
import pama1234.gdx.game.duel.Duel;
import pama1234.gdx.game.duel.State0002Util.StateEntity0002;
import pama1234.gdx.game.duel.util.arrow.ClientLongbowArrowHead;
import pama1234.gdx.game.duel.util.arrow.ClientShortbowArrow;
import pama1234.gdx.game.duel.util.input.DuelAndroidCtrl;
import pama1234.gdx.game.duel.util.input.ClientInputData;
import pama1234.gdx.game.duel.util.input.UiGenerator;
import pama1234.gdx.game.ui.util.TextButton;
import pama1234.gdx.util.element.AndroidCtrlBase;
import pama1234.gdx.util.info.MouseInfo;
import pama1234.util.protobuf.InputDataProto;
import pama1234.util.protobuf.InputDataProto.InputData;
import pama1234.util.protobuf.OutputDataProto.GroupData;
import pama1234.util.protobuf.OutputDataProto.OutputData;
import pama1234.util.protobuf.OutputDataProto.OutputDataElement;
import pama1234.util.wrapper.Center;

public class Game extends StateEntity0002{
  public TextButton<?>[] buttons;
  public ClientInputData currentInput;
  public ClientGameSystem core;
  public boolean paused;
  public DuelAndroidCtrl actrl;
  //---
  public InputDataProto.InputData.Builder inputDataBuilder;
  public Game(Duel p,int id,boolean doInit) {
    super(p,id);
    if(doInit) init();
  }
  public Game(Duel p,int id) {
    super(p,id);
    init();
  }
  @Override
  public void init() {
    if(p.isAndroid) {
      actrl=new DuelAndroidCtrl(p,this);
      if(p.config.orientation==1) actrl.activeCondition=AndroidCtrlBase.portraitCondition;
      else actrl.activeCondition=AndroidCtrlBase.landscapeCondition;
      actrl.init();
    }
    currentInput=new ClientInputData();
    //---
    newGame(true,true);
    //---
    if(p.config.gameMode==GameMode.OnLine) onlineGameSetup();
    buttons=UiGenerator.genButtons_0005(p);
  }
  @Override
  public void display() {
    core.displayScreen();
  }
  @Override
  public void update() {
    if(!paused) {
      if(p.config.gameMode==GameMode.OnLine) {
        onlineGameUpdate();
        core.updateClient();
      }else {
        core.update();
      }
      if(p.config.mode==neat) p.neatE.update();
    }
  }
  @Override
  public void displayCam() {
    p.doStroke();
    if(p.config.mode==neat) p.neatE.displayCam();
    else {
      core.display();
    }
    p.clearMatrix();
    p.noStroke();
    p.doFill();
  }
  public void onlineGameSetup() {
    inputDataBuilder=InputDataProto.InputData.newBuilder();
  }
  public void onlineGameUpdate() {
    try {
      inputDataBuilder.clear();
      currentInput.copyToProto(inputDataBuilder);
      InputData inputData=inputDataBuilder.build();
      inputData.writeDelimitedTo(p.gameClient.socketData.o);
      p.gameClient.socketData.o.flush();
      //---
      OutputData outputData=OutputData.parseDelimitedFrom(p.gameClient.socketData.i);
      GroupData a=outputData.getElements(0);
      ActorGroup ag=core.myGroup();
      copyGroupFromProto(a,ag);
      GroupData b=outputData.getElements(1);
      ActorGroup bg=core.otherGroup();
      copyGroupFromProto(b,bg);
    }catch(IOException e) {
      e.printStackTrace();
      p.config.gameMode=GameMode.OffLine;
    }
  }
  public void copyGroupFromProto(GroupData proto,ActorGroup group) {
    List<OutputDataElement> longArrowList=proto.getLongArrowList();
    List<OutputDataElement> shortArrowList=proto.getShortArrowList();
    Center<AbstractArrowActor> arrowCenter=group.arrowCenter;
    arrowCenter.add.addAll(arrowCenter.list);
    arrowCenter.list.clear();
    var itlong=longArrowList.iterator();
    var itshort=shortArrowList.iterator();
    for(var i:arrowCenter.add) {
      if(i.isLethal()) {
        if(itlong.hasNext()) i.copyFromProto(itlong.next());
        else arrowCenter.remove.add(i);
      }else {
        if(itshort.hasNext()) i.copyFromProto(itshort.next());
        else arrowCenter.remove.add(i);
      }
    }
    while(itlong.hasNext()) group.addArrow(new ClientLongbowArrowHead(p,itlong.next()));
    while(itshort.hasNext()) group.addArrow(new ClientShortbowArrow(p,itshort.next()));
    //---
    if(group.playerCenter.list.size()>0&&proto.getPlayerCount()>0) group.playerCenter.list.getFirst().copyFromProto(proto.getPlayer(0));
  }
  public void newGame(boolean demo,boolean instruction) {
    core=new ClientGameSystem(p,this,demo,instruction);
  }
  public void doPause() {
    paused=!paused;
  }
  @Override
  public void mousePressed(MouseInfo info) {
    if(info.button==Buttons.LEFT) core.showsInstructionWindow=!core.showsInstructionWindow;
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
      //---
      p.cam.point.des.set(p.canvasSideLength/2f,p.canvasSideLength/2f);
      p.cam.point.pos.set(p.cam.point.des);
      p.cam2d.scale.pos=p.cam2d.scale.des=p.isAndroid?0.25f:1;
    }
    if(actrl!=null) {
      actrl.addAll();
      p.centerCam.add.add(actrl);
    }
    for(TextButton<?> e:buttons) p.centerScreen.add.add(e);
  }
  @Override
  public void to(StateEntity0002 in) {
    paused=true;
    if(p.config.mode==game) {
      p.cam2d.activeDrag=true;
      p.cam2d.activeScrollZoom=p.cam2d.activeTouchZoom=true;
    }
    if(actrl!=null) {
      actrl.removeAll();
      p.centerCam.remove.add(actrl);
    }
    for(TextButton<?> e:buttons) p.centerScreen.remove.add(e);
  }
}