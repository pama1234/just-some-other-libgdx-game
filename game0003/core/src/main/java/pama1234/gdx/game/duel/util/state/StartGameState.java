package pama1234.gdx.game.duel.util.state;

import pama1234.gdx.game.duel.Duel;
import pama1234.gdx.game.duel.GameSystem;
import pama1234.gdx.game.duel.util.graphics.Particle;
import pama1234.math.Tools;
import pama1234.math.UtilMath;

public final class StartGameState extends GameSystemState{
  public StartGameState(Duel duel) {
    super(duel);
  }
  public final int frameCountPerNumber=UtilMath.floor(Duel.IDEAL_FRAME_RATE);
  public final float ringSize=200.0f;
  public final int ringColor=Tools.color(0);
  public final float ringStrokeWeight=5.0f;
  public int displayNumber=4;
  @Override
  public void updateSystem(GameSystem system) {
    system.myGroup.update();
    system.otherGroup.update();
  }
  @Override
  public void displaySystem(GameSystem system) {
    system.myGroup.displayPlayer();
    system.otherGroup.displayPlayer();
  }
  @Override
  public void displayMessage(GameSystem system) {
    final int currentNumberFrameCount=properFrameCount%frameCountPerNumber;
    if(currentNumberFrameCount==0) displayNumber--;
    if(displayNumber<=0) return;
    duel.doFill();
    duel.fill(ringColor);
    duel.drawText(Integer.toString(displayNumber),0.0f,0.0f);
    duel.rotate(-UtilMath.HALF_PI);
    duel.strokeWeight(3.0f);
    duel.stroke(ringColor);
    duel.noFill();
    duel.arc(0.0f,0.0f,ringSize,ringSize,0.0f,UtilMath.TWO_PI*(float)(properFrameCount%frameCountPerNumber)/frameCountPerNumber);
    duel.strokeWeight(1.0f);
  }
  @Override
  public void checkStateTransition(GameSystem system) {
    if(properFrameCount>=frameCountPerNumber*3) {
      final Particle newParticle=system.commonParticleSet.builder
        .type(Particle.ring)
        .position(Duel.INTERNAL_CANVAS_SIDE_LENGTH*0.5f,Duel.INTERNAL_CANVAS_SIDE_LENGTH*0.5f)
        .polarVelocity(0.0f,0.0f)
        .particleSize(ringSize)
        .particleColor(ringColor)
        .weight(ringStrokeWeight)
        .lifespanSecond(1.0f)
        .build();
      system.commonParticleSet.particleList.add(newParticle);
      system.currentState=new PlayGameState(duel);
    }
  }
}