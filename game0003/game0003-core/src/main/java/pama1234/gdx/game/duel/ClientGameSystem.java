package pama1234.gdx.game.duel;

import pama1234.app.game.server.duel.ServerConfigData;
import pama1234.app.game.server.duel.ServerGameSystem;
import pama1234.app.game.server.duel.util.Const;
import pama1234.app.game.server.duel.util.ai.mesh.ComputerPlayerEngine;
import pama1234.app.game.server.duel.util.player.DrawBowPlayerActorState;
import pama1234.app.game.server.duel.util.player.MovePlayerActorState;
import pama1234.app.game.server.duel.util.player.PlayerEngine;
import pama1234.app.game.server.duel.util.player.ServerDoTeleportPlayerActorState;
import pama1234.gdx.game.duel.util.actor.ClientPlayerActor;
import pama1234.gdx.game.duel.util.ai.nnet.ComputerLifeEngine;
import pama1234.gdx.game.duel.util.graphics.DemoInfo;
import pama1234.gdx.game.duel.util.graphics.GameBackground;
import pama1234.gdx.game.duel.util.graphics.Particle;
import pama1234.gdx.game.duel.util.graphics.ParticleBuilder;
import pama1234.gdx.game.duel.util.graphics.ParticleSet;
import pama1234.gdx.game.duel.util.player.ClientAndroidHumanPlayerEngine;
import pama1234.gdx.game.duel.util.player.ClientDamagedPlayerActorState;
import pama1234.gdx.game.duel.util.player.ClientDoTeleportPlayerActorState;
import pama1234.gdx.game.duel.util.player.ClientDrawLongbowPlayerActorState;
import pama1234.gdx.game.duel.util.player.ClientDrawShortbowPlayerActorState;
import pama1234.gdx.game.duel.util.player.ClientHumanPlayerEngine;
import pama1234.gdx.game.duel.util.state.ClientGameSystemState;
import pama1234.gdx.game.duel.util.state.ClientStartGameState;
import pama1234.gdx.game.state.state0002.Game;
import pama1234.math.UtilMath;

public final class ClientGameSystem extends ServerGameSystem{
  public final Duel p;
  public final Game pg;
  public final ParticleSet commonParticleSet;
  public float screenShakeValue;
  public ClientGameSystemState currentState;
  public boolean showsInstructionWindow;
  public final ClientDamagedPlayerActorState damagedState;
  public final GameBackground currentBackground;
  public ClientGameSystem(Duel duel,Game pg) {
    this(duel,pg,false,false);
  }
  public ClientGameSystem(Duel duel,Game pg,boolean demo,boolean instruction) {
    this(duel,pg,demo,instruction,1);
  }
  public ClientGameSystem(Duel duel,Game pg,boolean demo,boolean instruction,float level) {
    super(null,demo,false,level);
    this.p=duel;
    this.pg=pg;
    // prepare PlayerActorState
    final MovePlayerActorState moveState=new MovePlayerActorState();
    final DrawBowPlayerActorState drawShortbowState=new ClientDrawShortbowPlayerActorState(duel);
    final DrawBowPlayerActorState drawLongbowState=new ClientDrawLongbowPlayerActorState(duel);
    final ServerDoTeleportPlayerActorState doTeleportState=new ClientDoTeleportPlayerActorState(duel);
    damagedState=new ClientDamagedPlayerActorState(duel);
    moveState.drawShortbowState=drawShortbowState;
    moveState.drawLongbowState=drawLongbowState;
    moveState.doTeleportState=doTeleportState;
    drawShortbowState.moveState=moveState;
    drawLongbowState.moveState=moveState;
    doTeleportState.moveState=moveState;
    damagedState.moveState=moveState;
    // prepare PlayerActor
    PlayerEngine myEngine;
    if(demo) myEngine=createComputerEngine(true);
    else {
      if(duel.isAndroid) myEngine=new ClientAndroidHumanPlayerEngine(pg.currentInput);
      else myEngine=new ClientHumanPlayerEngine(pg.currentInput);
    }
    ClientPlayerActor myPlayer=new ClientPlayerActor(duel,myEngine,duel.config.data.mode==ServerConfigData.neat?duel.theme().player_b:duel.theme().player_a);
    myPlayer.pos.set(Const.CANVAS_SIZE*0.5f,Const.CANVAS_SIZE-100);
    myPlayer.state=moveState;
    myGroup().addPlayer(myPlayer);
    PlayerEngine otherEngine=createComputerEngine(false);
    ClientPlayerActor otherPlayer=new ClientPlayerActor(duel,otherEngine,duel.theme().player_b);
    otherPlayer.pos.set(Const.CANVAS_SIZE*0.5f,100);
    otherPlayer.state=moveState;
    otherGroup().addPlayer(otherPlayer);
    // other
    commonParticleSet=new ParticleSet(duel,this,2048);
    currentState(new ClientStartGameState(duel,this));
    currentBackground=new GameBackground(duel,duel.theme().backgroundLine,0.1f);
    // demoPlay=demo;
    showsInstructionWindow=instruction;
  }
  @Override
  public PlayerEngine createComputerEngine(boolean side) {
    if(p.config.data.mode==ServerConfigData.neat) {
      return new ComputerLifeEngine((side?p.neatE.player_a:p.neatE.player_b).graphics,p.neatCenter.getNext(),side);
    }else return new ComputerPlayerEngine(p::random,level);
  }
  @Override
  public void update() {
    if(demoPlay) {
      if(pg.currentInput.isZPressed) {
        // stop demo and start game
        pg.newGame(false,false);
        return;
      }
    }
    currentBackground.update();
    currentState.update();
  }
  @Override
  public void display() {
    p.pushMatrix();
    if(screenShakeValue>0) {
      p.translate(p.random(screenShakeValue,screenShakeValue),p.random(-screenShakeValue,screenShakeValue));
      screenShakeValue-=50f/Const.IDEAL_FRAME_RATE;
    }
    currentBackground.display();
    currentState.display();
    p.popMatrix();
  }
  @Override
  public void displayScreen() {
    currentState.displayScreen();
    if(demoPlay&&showsInstructionWindow) DemoInfo.displayDemo(p);
  }
  @Override
  public void addSquareParticles(float x,float y,int particleCount,float particleSize,float minSpeed,float maxSpeed,float lifespanSecondValue) {
    final ParticleBuilder builder=pg.core.commonParticleSet.builder
      .type(Particle.square)
      .position(x,y)
      .particleSize(particleSize)
      .particleColor(p.theme().squareParticles)
      .lifespanSecond(lifespanSecondValue);
    for(int i=0;i<particleCount;i++) {
      final Particle newParticle=builder
        .polarVelocity(p.random(UtilMath.PI2),p.random(minSpeed,maxSpeed))
        .build();
      pg.core.commonParticleSet.particleList.add(newParticle);
    }
  }
  public void currentState(ClientGameSystemState currentState) {
    this.currentState=currentState;
    p.inGameStateChangeEvent(this,stateIndex);
  }
}