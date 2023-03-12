package pama1234.gdx.game.duel.util.state;

import com.badlogic.gdx.graphics.Color;

import pama1234.gdx.game.duel.Duel;
import pama1234.gdx.game.duel.GameSystem;
import pama1234.gdx.game.duel.util.graphics.Particle;
import pama1234.math.UtilMath;

public final class StartGameState extends GameSystemState{
  public StartGameState(Duel duel) {
    super(duel);
  }
  public final int frameCountPerNumber=UtilMath.floor(Duel.IDEAL_FRAME_RATE);
  public final float ringSize=200;
  public final Color ringColor=Duel.color(0);
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
    duel.translate(Duel.INTERNAL_CANVAS_SIDE_LENGTH*0.5f,Duel.INTERNAL_CANVAS_SIDE_LENGTH*0.5f);
    drawRing();
  }
  @Override
  public void displayMessage(GameSystem system) {
    final int currentNumberFrameCount=properFrameCount%frameCountPerNumber;
    if(currentNumberFrameCount==0) displayNumber--;
    if(displayNumber<=0) return;
    // duel.doFill();
    // duel.fill(ringColor);
    duel.setTextColor(ringColor);
    duel.setTextScale(duel.pus);
    String in=Integer.toString(displayNumber);
    // duel.drawText(in,-16,-32);
    duel.drawText(in,(duel.width-duel.textWidth(in))/2f,(duel.height-duel.pu)/2f);
    // duel.setCamera(duel.cam.camera);
    // duel.pushMatrix();
    duel.setTextScale(1);
    // drawCam();
    // duel.popMatrix();
    // duel.setCamera(duel.screenCam);
  }
  public void drawRing() {
    duel.rotate(UtilMath.HALF_PI);
    duel.strokeWeight(3);
    duel.doStroke();
    duel.stroke(ringColor);
    duel.noFill();
    duel.arc(0,0,ringSize/2f,0,360*(float)(properFrameCount%frameCountPerNumber)/frameCountPerNumber);
    duel.strokeWeight(1);
  }
  @Override
  public void checkStateTransition(GameSystem system) {
    if(properFrameCount>=frameCountPerNumber*3) {
      final Particle newParticle=system.commonParticleSet.builder
        .type(Particle.ring)
        .position(Duel.INTERNAL_CANVAS_SIDE_LENGTH*0.5f,Duel.INTERNAL_CANVAS_SIDE_LENGTH*0.5f)
        .polarVelocity(0,0)
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