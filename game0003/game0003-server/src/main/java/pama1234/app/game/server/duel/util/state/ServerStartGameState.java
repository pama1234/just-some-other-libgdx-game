package pama1234.app.game.server.duel.util.state;

import pama1234.app.game.server.duel.ServerGameSystem;
import pama1234.app.game.server.duel.util.Const;
import pama1234.math.UtilMath;

public class ServerStartGameState extends ServerGameSystemState{
  public final int frameCountPerNumber=UtilMath.floor(Const.IDEAL_FRAME_RATE);
  public final float ringSize=200;
  // public final Color ringColor;
  public final float ringStrokeWeight=5.0f;
  public int displayNumber=3;
  public ServerStartGameState(ServerGameSystem system) {
    super(system);
    system.stateIndex=ServerGameSystem.start;
    // ringColor=duel.theme.ring;
  }
  @Override
  public void updateSystem() {
    for(var group:system.groupCenter.list) group.update();
    // system.myGroup().update();
    // system.otherGroup().update();
  }
  // @Override
  // public void displaySystem() {
  //   for(var group:system.groupCenter.list) group.displayPlayer();
  //   // system.myGroup().displayPlayer();
  //   // system.otherGroup().displayPlayer();
  //   p.translate(Const.CANVAS_SIZE*0.5f,Const.CANVAS_SIZE*0.5f);
  //   drawRing();
  // }
  // @Override
  // public void displayMessage() {
  //   final int currentNumberFrameCount=properFrameCount%frameCountPerNumber;
  //   if(currentNumberFrameCount==0) displayNumber--;
  //   if(displayNumber<0) return;
  //   p.setTextColor(ringColor);
  //   p.setTextScale(p.pus);
  //   String in=Integer.toString(displayNumber);
  //   p.fullText(in,(p.width-p.textWidth(in))/2f,(p.height-p.pu)/2f);
  //   p.setTextScale(1);
  // }
  // public void drawRing() {
  //   p.rotate(UtilMath.HALF_PI);
  //   p.strokeWeight(3);
  //   p.doStroke();
  //   p.stroke(ringColor);
  //   p.noFill();
  //   p.arc(0,0,ringSize/2f,0,360*(float)(properFrameCount%frameCountPerNumber)/frameCountPerNumber);
  // }
  @Override
  public void checkStateTransition() {
    if(properFrameCount>=frameCountPerNumber*3) {
      // final Particle newParticle=system.commonParticleSet.builder
      //   .type(Particle.ring)
      //   .position(Const.CANVAS_SIZE*0.5f,Const.CANVAS_SIZE*0.5f)
      //   .polarVelocity(0,0)
      //   .particleSize(ringSize)
      //   .particleColor(ringColor)
      //   .weight(ringStrokeWeight)
      //   .lifespanSecond(1.0f)
      //   .build();
      // system.commonParticleSet.particleList.add(newParticle);
      system.currentState(new ServerPlayGameState(system));
    }
  }
  @Override
  public float getScore(int group) {
    return 0;
  }
}
