package pama1234.processing.game.duel.util.graphics;

import pama1234.math.Tools;
import pama1234.processing.game.duel.Duel;
import processing.core.PApplet;

public final class ParticleBuilder{
  private final Duel duel;
  public ParticleBuilder(Duel duel) {
    this.duel=duel;
  }
  public int particleTypeNumber;
  public float xPosition,yPosition;
  public float xVelocity,yVelocity;
  public float directionAngle,speed;
  public float rotationAngle;
  public int displayColor;
  public float strokeWeightValue;
  public float displaySize;
  public int lifespanFrameCount;
  public ParticleBuilder initialize() {
    particleTypeNumber=0;
    xPosition=0.0f;
    yPosition=0.0f;
    xVelocity=0.0f;
    yVelocity=0.0f;
    directionAngle=0.0f;
    speed=0.0f;
    rotationAngle=0.0f;
    displayColor=Tools.color(0.0f);
    strokeWeightValue=1.0f;
    displaySize=10.0f;
    lifespanFrameCount=60;
    return this;
  }
  public ParticleBuilder type(int v) {
    particleTypeNumber=v;
    return this;
  }
  public ParticleBuilder position(float x,float y) {
    xPosition=x;
    yPosition=y;
    return this;
  }
  public ParticleBuilder polarVelocity(float dir,float spd) {
    directionAngle=dir;
    speed=spd;
    xVelocity=spd*PApplet.cos(dir);
    yVelocity=spd*PApplet.sin(dir);
    return this;
  }
  public ParticleBuilder rotation(float v) {
    rotationAngle=v;
    return this;
  }
  public ParticleBuilder particleColor(int c) {
    displayColor=c;
    return this;
  }
  public ParticleBuilder weight(float v) {
    strokeWeightValue=v;
    return this;
  }
  public ParticleBuilder particleSize(float v) {
    displaySize=v;
    return this;
  }
  public ParticleBuilder lifespan(int v) {
    lifespanFrameCount=v;
    return this;
  }
  public ParticleBuilder lifespanSecond(float v) {
    lifespan(PApplet.parseInt(v*Duel.IDEAL_FRAME_RATE));
    return this;
  }
  public Particle build() {
    final Particle newParticle=this.duel.system.commonParticleSet.allocate();
    newParticle.particleTypeNumber=this.particleTypeNumber;
    newParticle.xPosition=this.xPosition;
    newParticle.yPosition=this.yPosition;
    newParticle.xVelocity=this.xVelocity;
    newParticle.yVelocity=this.yVelocity;
    newParticle.directionAngle=this.directionAngle;
    newParticle.speed=this.speed;
    newParticle.rotationAngle=this.rotationAngle;
    newParticle.displayColor=this.displayColor;
    newParticle.strokeWeightValue=this.strokeWeightValue;
    newParticle.displaySize=this.displaySize;
    newParticle.lifespanFrameCount=this.lifespanFrameCount;
    return newParticle;
  }
}