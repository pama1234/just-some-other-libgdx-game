package pama1234.gdx.game.duel.util.state;

import com.badlogic.gdx.graphics.Color;

import pama1234.gdx.game.duel.Duel;
import pama1234.gdx.game.duel.GameSystem;
import pama1234.gdx.game.duel.util.graphics.Particle;
import pama1234.math.UtilMath;

public final class StartGameState extends GameSystemState{
  public StartGameState(Duel duel,GameSystem system) {
    super(duel,system);
    system.stateIndex=GameSystem.start;
  }
  public final int frameCountPerNumber=UtilMath.floor(Duel.IDEAL_FRAME_RATE);
  public final float ringSize=200;
  public final Color ringColor=Duel.color(0);
  public final float ringStrokeWeight=5.0f;
  public int displayNumber=3;
  @Override
  public void updateSystem() {
    system.myGroup.update();
    system.otherGroup.update();
  }
  @Override
  public void displaySystem() {
    system.myGroup.displayPlayer();
    system.otherGroup.displayPlayer();
    duel.translate(Duel.CANVAS_SIZE*0.5f,Duel.CANVAS_SIZE*0.5f);
    drawRing();
  }
  @Override
  public void displayMessage() {
    final int currentNumberFrameCount=properFrameCount%frameCountPerNumber;
    if(currentNumberFrameCount==0) displayNumber--;
    if(displayNumber<0) return;
    duel.setTextColor(ringColor);
    duel.setTextScale(duel.pus);
    String in=Integer.toString(displayNumber);
    duel.drawText(in,(duel.width-duel.textWidth(in))/2f,(duel.height-duel.pu)/2f);
    duel.setTextScale(1);
  }
  public void drawRing() {
    duel.rotate(UtilMath.HALF_PI);
    duel.strokeWeight(3);
    duel.doStroke();
    duel.stroke(ringColor);
    duel.noFill();
    duel.arc(0,0,ringSize/2f,0,360*(float)(properFrameCount%frameCountPerNumber)/frameCountPerNumber);
  }
  @Override
  public void checkStateTransition() {
    if(properFrameCount>=frameCountPerNumber*3) {
      final Particle newParticle=system.commonParticleSet.builder
        .type(Particle.ring)
        .position(Duel.CANVAS_SIZE*0.5f,Duel.CANVAS_SIZE*0.5f)
        .polarVelocity(0,0)
        .particleSize(ringSize)
        .particleColor(ringColor)
        .weight(ringStrokeWeight)
        .lifespanSecond(1.0f)
        .build();
      system.commonParticleSet.particleList.add(newParticle);
      system.currentState(new PlayGameState(duel,system));
    }
  }
}