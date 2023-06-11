package pama1234.gdx.game.duel;

import pama1234.app.game.server.duel.ServerConfigData;
import pama1234.app.game.server.duel.ServerGameSystem;
import pama1234.app.game.server.duel.util.Const;
import pama1234.app.game.server.duel.util.ai.mesh.ComputerPlayerEngine;
import pama1234.app.game.server.duel.util.player.DrawBowPlayerActorState;
import pama1234.app.game.server.duel.util.player.MovePlayerActorState;
import pama1234.app.game.server.duel.util.player.PlayerEngine;
import pama1234.gdx.game.duel.util.actor.ClientPlayerActor;
import pama1234.gdx.game.duel.util.ai.nnet.ComputerLifeEngine;
import pama1234.gdx.game.duel.util.graphics.DemoInfo;
import pama1234.gdx.game.duel.util.graphics.GameBackground;
import pama1234.gdx.game.duel.util.graphics.Particle;
import pama1234.gdx.game.duel.util.graphics.ParticleBuilder;
import pama1234.gdx.game.duel.util.graphics.ParticleSet;
import pama1234.gdx.game.duel.util.player.ClientAndroidHumanPlayerEngine;
import pama1234.gdx.game.duel.util.player.ClientDamagedPlayerActorState;
import pama1234.gdx.game.duel.util.player.ClientDrawLongbowPlayerActorState;
import pama1234.gdx.game.duel.util.player.ClientDrawShortbowPlayerActorState;
import pama1234.gdx.game.duel.util.player.ClientHumanPlayerEngine;
import pama1234.gdx.game.duel.util.state.ClientGameSystemState;
import pama1234.gdx.game.duel.util.state.ClientStartGameState;
import pama1234.math.UtilMath;

public final class ClientGameSystem extends ServerGameSystem{
  public final Duel duel;
  public final ParticleSet commonParticleSet;
  public float screenShakeValue;
  public ClientGameSystemState currentState;
  public boolean showsInstructionWindow;
  public final ClientDamagedPlayerActorState damagedState;
  public final GameBackground currentBackground;
  public ClientGameSystem(Duel duel) {
    this(duel,false,false);
  }
  public ClientGameSystem(Duel duel,boolean demo,boolean instruction) {
    super(null,demo,false);
    this.duel=duel;
    // prepare PlayerActorState
    final MovePlayerActorState moveState=new MovePlayerActorState();
    final DrawBowPlayerActorState drawShortbowState=new ClientDrawShortbowPlayerActorState(duel);
    final DrawBowPlayerActorState drawLongbowState=new ClientDrawLongbowPlayerActorState(duel);
    damagedState=new ClientDamagedPlayerActorState(duel);
    moveState.drawShortbowState=drawShortbowState;
    moveState.drawLongbowState=drawLongbowState;
    drawShortbowState.moveState=moveState;
    drawLongbowState.moveState=moveState;
    damagedState.moveState=moveState;
    // prepare PlayerActor
    PlayerEngine myEngine;
    if(demo) myEngine=createComputerEngine(true);
    else {
      if(duel.isAndroid) myEngine=new ClientAndroidHumanPlayerEngine(duel.currentInput);
      else myEngine=new ClientHumanPlayerEngine(duel.currentInput);
    }
    ClientPlayerActor myPlayer=new ClientPlayerActor(duel,myEngine,duel.config.mode==ServerConfigData.neat?duel.skin.player_b:duel.skin.player_a);
    myPlayer.xPosition=Const.CANVAS_SIZE*0.5f;
    myPlayer.yPosition=Const.CANVAS_SIZE-100;
    myPlayer.state=moveState;
    myGroup.setPlayer(myPlayer);
    PlayerEngine otherEngine=createComputerEngine(false);
    ClientPlayerActor otherPlayer=new ClientPlayerActor(duel,otherEngine,duel.skin.player_b);
    otherPlayer.xPosition=Const.CANVAS_SIZE*0.5f;
    otherPlayer.yPosition=100;
    otherPlayer.state=moveState;
    otherGroup.setPlayer(otherPlayer);
    // other
    commonParticleSet=new ParticleSet(duel,2048);
    currentState(new ClientStartGameState(duel,this));
    currentBackground=new GameBackground(duel,duel.skin.background,0.1f);
    // demoPlay=demo;
    showsInstructionWindow=instruction;
  }
  public PlayerEngine createComputerEngine(boolean side) {
    if(duel.config.mode==ServerConfigData.neat) {
      // if(type) return new ComputerPlayerEngine(duel::random);
      // else return new ComputerLifeEngine((type?duel.player_a:duel.player_b).graphics,duel.neatCenter.getNext());
      return new ComputerLifeEngine((side?duel.player_a:duel.player_b).graphics,duel.neatCenter.getNext(),side);
    }else return new ComputerPlayerEngine(duel::random);
  }
  public void update() {
    if(demoPlay) {
      if(duel.currentInput.isZPressed) {
        duel.system=new ClientGameSystem(duel); // stop demo and start game
        return;
      }
    }
    currentBackground.update();
    currentState.update();
  }
  public void display() {
    duel.pushMatrix();
    if(screenShakeValue>0) {
      duel.translate(duel.random(screenShakeValue,screenShakeValue),duel.random(-screenShakeValue,screenShakeValue));
      screenShakeValue-=50f/Const.IDEAL_FRAME_RATE;
    }
    currentBackground.display();
    currentState.display();
    duel.popMatrix();
  }
  public void displayScreen() {
    currentState.displayScreen();
    if(demoPlay&&showsInstructionWindow) DemoInfo.displayDemo(duel);
  }
  public void addSquareParticles(float x,float y,int particleCount,float particleSize,float minSpeed,float maxSpeed,float lifespanSecondValue) {
    final ParticleBuilder builder=duel.system.commonParticleSet.builder
      .type(Particle.square)
      .position(x,y)
      .particleSize(particleSize)
      .particleColor(duel.skin.squareParticles)
      .lifespanSecond(lifespanSecondValue);
    for(int i=0;i<particleCount;i++) {
      final Particle newParticle=builder
        .polarVelocity(duel.random(UtilMath.PI2),duel.random(minSpeed,maxSpeed))
        .build();
      duel.system.commonParticleSet.particleList.add(newParticle);
    }
  }
  public void currentState(ClientGameSystemState currentState) {
    this.currentState=currentState;
    duel.stateChangeEvent(this,stateIndex);
  }
}