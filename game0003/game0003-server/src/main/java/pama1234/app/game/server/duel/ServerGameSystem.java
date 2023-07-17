package pama1234.app.game.server.duel;

import java.util.LinkedList;

import pama1234.app.game.server.duel.util.Const;
import pama1234.app.game.server.duel.util.actor.ActorGroup;
import pama1234.app.game.server.duel.util.ai.mesh.ComputerPlayerEngine;
import pama1234.app.game.server.duel.util.input.ServerInputOutput;
import pama1234.app.game.server.duel.util.player.DrawBowPlayerActorState;
import pama1234.app.game.server.duel.util.player.MovePlayerActorState;
import pama1234.app.game.server.duel.util.player.PlayerEngine;
import pama1234.app.game.server.duel.util.player.ServerDamagedPlayerActorState;
import pama1234.app.game.server.duel.util.player.ServerDrawLongbowPlayerActorState;
import pama1234.app.game.server.duel.util.player.ServerDrawShortbowPlayerActorState;
import pama1234.app.game.server.duel.util.player.ServerHumanPlayerEngine;
import pama1234.app.game.server.duel.util.player.ServerPlayerActor;
import pama1234.app.game.server.duel.util.state.ServerGameSystemState;
import pama1234.app.game.server.duel.util.state.ServerStartGameState;
import pama1234.util.wrapper.Center;

public class ServerGameSystem{
  public static final int start=1,play=2,result=3;
  //---
  public DuelServer duelServer;
  //---
  public Center<ActorGroup> groupCenter;
  public ServerGameSystemState currentState;
  public int stateIndex;
  public final boolean demoPlay;
  public final ServerDamagedPlayerActorState damagedState;
  public float level;
  public ServerGameSystem(DuelServer duelServer) {
    this(duelServer,false,true,1);
  }
  public ServerGameSystem(DuelServer duelServer,boolean demo,boolean prepareServer,float level) {
    this.level=level;
    this.duelServer=duelServer;
    groupCenter=new Center<>();
    groupCenter.list.add(null);
    groupCenter.list.add(null);
    myGroup(new ActorGroup(0));
    otherGroup(new ActorGroup(1));
    // prepare ActorGroup
    myGroup().enemyGroup.list.add(otherGroup());
    otherGroup().enemyGroup.list.add(myGroup());
    demoPlay=demo;
    //---
    damagedState=new ServerDamagedPlayerActorState();
    if(prepareServer) prepareServer(demo);
  }
  // @Deprecated
  public ActorGroup myGroup() {
    return groupCenter.list.get(0);
  }
  // @Deprecated
  public ActorGroup otherGroup() {
    return groupCenter.list.get(1);
  }
  // @Deprecated
  public void myGroup(ActorGroup in) {
    groupCenter.list.set(0,in);
  }
  // @Deprecated
  public void otherGroup(ActorGroup in) {
    groupCenter.list.set(1,in);
  }
  private void prepareServer(boolean demo) {
    // prepare PlayerActorState
    final MovePlayerActorState moveState=new MovePlayerActorState();
    final DrawBowPlayerActorState drawShortbowState=new ServerDrawShortbowPlayerActorState(duelServer);
    final DrawBowPlayerActorState drawLongbowState=new ServerDrawLongbowPlayerActorState(duelServer);
    moveState.drawShortbowState=drawShortbowState;
    moveState.drawLongbowState=drawLongbowState;
    drawShortbowState.moveState=moveState;
    drawLongbowState.moveState=moveState;
    damagedState.moveState=moveState;
    // prepare PlayerActor
    PlayerEngine myEngine;
    if(demo) myEngine=createComputerEngine(true);
    else myEngine=new ServerHumanPlayerEngine(duelServer.input_a.inputData);
    ServerPlayerActor myPlayer=new ServerPlayerActor(myEngine);
    myPlayer.pos.x=Const.CANVAS_SIZE*0.5f;
    myPlayer.pos.y=Const.CANVAS_SIZE-100;
    myPlayer.state=moveState;
    myGroup().addPlayer(myPlayer);
    PlayerEngine otherEngine;
    if(demo) otherEngine=createComputerEngine(false);
    else otherEngine=new ServerHumanPlayerEngine(duelServer.input_b.inputData);
    ServerPlayerActor otherPlayer=new ServerPlayerActor(otherEngine);
    otherPlayer.pos.x=Const.CANVAS_SIZE*0.5f;
    otherPlayer.pos.y=100;
    otherPlayer.state=moveState;
    otherGroup().addPlayer(otherPlayer);
    currentState(new ServerStartGameState(this));
  }
  public PlayerEngine createComputerEngine(boolean side) {
    if(duelServer.config.mode==ServerConfigData.neat) {
      //TODO support neat mod on server
      return null;
    }else return new ComputerPlayerEngine(duelServer::random,level);
  }
  public void run() {
    update();
    display();
  }
  public void update() {
    if(demoPlay) {
      // if(allZPressed(duelServer.inputCenter.list)) {
      if(duelServer.input_a.inputData.isZPressed) {
        // stop demo and start game
        duelServer.newGame(false);
        return;
      }
    }
    groupCenter.refresh();
    currentState.update();
  }
  public boolean allZPressed(LinkedList<ServerInputOutput> list) {
    for(var i:list) if(!i.inputData.isZPressed) return false;
    return true;
  }
  public void display() {}
  public void displayScreen() {}
  public void addSquareParticles(float x,float y,int particleCount,float particleSize,float minSpeed,float maxSpeed,float lifespanSecondValue) {}
  public void currentState(ServerGameSystemState currentState) {
    this.currentState=currentState;
    // duelServer.stateChangeEvent(this,stateIndex);
    duelServer.inGameStateChangeEvent(this,stateIndex);
  }
}
