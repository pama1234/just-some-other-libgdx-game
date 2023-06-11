package pama1234.app.game.server.duel;

import pama1234.app.game.server.duel.util.Const;
import pama1234.app.game.server.duel.util.actor.ActorGroup;
import pama1234.app.game.server.duel.util.ai.mesh.ComputerPlayerEngine;
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

public class ServerGameSystem{
  public static final int start=1,play=2,result=3;
  //---
  public DuelServer duelServer;
  //---
  public final ActorGroup myGroup,otherGroup;
  public ServerGameSystemState currentState;
  public int stateIndex;
  public final boolean demoPlay;
  public final ServerDamagedPlayerActorState damagedState;
  public ServerGameSystem(DuelServer duelServer) {
    this(duelServer,false,true);
  }
  public ServerGameSystem(DuelServer duelServer,boolean demo,boolean prepareServer) {
    this.duelServer=duelServer;
    myGroup=new ActorGroup(0);
    otherGroup=new ActorGroup(1);
    // prepare ActorGroup
    myGroup.enemyGroup=otherGroup;
    otherGroup.enemyGroup=myGroup;
    demoPlay=demo;
    //---
    damagedState=new ServerDamagedPlayerActorState();
    if(prepareServer) prepareServer(demo);
  }
  private void prepareServer(boolean demo) {
    // prepare PlayerActorState
    final MovePlayerActorState moveState=new MovePlayerActorState();
    final DrawBowPlayerActorState drawShortbowState=new ServerDrawShortbowPlayerActorState(duelServer);
    final DrawBowPlayerActorState drawLongbowState=new ServerDrawLongbowPlayerActorState(duelServer);
    // damagedState=new ServerDamagedPlayerActorState();
    moveState.drawShortbowState=drawShortbowState;
    moveState.drawLongbowState=drawLongbowState;
    drawShortbowState.moveState=moveState;
    drawLongbowState.moveState=moveState;
    damagedState.moveState=moveState;
    // prepare PlayerActor
    PlayerEngine myEngine;
    if(demo) myEngine=createComputerEngine(true);
    else {
      // if(duelServer.isAndroid) myEngine=new AndroidHumanPlayerEngine(duelServer.currentInput);
      // else myEngine=new ServerHumanPlayerEngine(duelServer.currentInput);
      myEngine=new ServerHumanPlayerEngine(duelServer.input_a);
    }
    ServerPlayerActor myPlayer=new ServerPlayerActor(myEngine);
    myPlayer.xPosition=Const.CANVAS_SIZE*0.5f;
    myPlayer.yPosition=Const.CANVAS_SIZE-100;
    myPlayer.state=moveState;
    myGroup.setPlayer(myPlayer);
    PlayerEngine otherEngine=createComputerEngine(false);
    ServerPlayerActor otherPlayer=new ServerPlayerActor(otherEngine);
    otherPlayer.xPosition=Const.CANVAS_SIZE*0.5f;
    otherPlayer.yPosition=100;
    otherPlayer.state=moveState;
    otherGroup.setPlayer(otherPlayer);
    // other
    // commonParticleSet=new ParticleSet(duel,2048);
    currentState(new ServerStartGameState(duelServer,this));
    // currentBackground=new GameBackground(duel,Duel.color(224),0.1f);
    // demoPlay=demo;
    // showsInstructionWindow=instruction;
  }
  public PlayerEngine createComputerEngine(boolean side) {
    if(duelServer.config.mode==ServerConfigData.neat) {
      // if(type) return new ComputerPlayerEngine(duel::random);
      // else return new ComputerLifeEngine((type?duel.player_a:duel.player_b).graphics,duel.neatCenter.getNext());
      // return new ComputerLifeEngine((side?duelServer.player_a:duelServer.player_b).graphics,duelServer.neatCenter.getNext(),side);
      return null;
    }else return new ComputerPlayerEngine(duelServer::random);
  }
  public void run() {
    update();
    display();
  }
  public void update() {
    if(demoPlay) {
      if(duelServer.input_a.isZPressed&&duelServer.input_b.isZPressed) {
        duelServer.system=new ServerGameSystem(duelServer); // stop demo and start game
        return;
      }
    }
    // currentBackground.update();
    currentState.update();
  }
  public void display() {}
  public void displayScreen() {
    // currentState.displayScreen();
    // if(demoPlay&&showsInstructionWindow) DemoInfo.displayDemo(duelServer);
  }
  public void addSquareParticles(float x,float y,int particleCount,float particleSize,float minSpeed,float maxSpeed,float lifespanSecondValue) {
    // final ParticleBuilder builder=duel.system.commonParticleSet.builder
    //   .type(Particle.square)
    //   .position(x,y)
    //   .particleSize(particleSize)
    //   .particleColor(Duel.color(0))
    //   .lifespanSecond(lifespanSecondValue);
    // for(int i=0;i<particleCount;i++) {
    //   final Particle newParticle=builder
    //     .polarVelocity(duelServer.random(UtilMath.PI2),duelServer.random(minSpeed,maxSpeed))
    //     .build();
    //     duelServer.system.commonParticleSet.particleList.add(newParticle);
    // }
  }
  public void currentState(ServerGameSystemState currentState) {
    this.currentState=currentState;
    duelServer.stateChangeEvent(this,stateIndex);
  }
}
