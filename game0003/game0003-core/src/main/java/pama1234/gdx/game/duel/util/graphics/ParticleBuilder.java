package pama1234.gdx.game.duel.util.graphics;

import com.badlogic.gdx.graphics.Color;

import pama1234.app.game.server.duel.util.Const;
import pama1234.gdx.game.duel.Duel;
import pama1234.math.UtilMath;

public final class ParticleBuilder{
  private final Duel p;
  public ParticleBuilder(Duel duel) {
    this.p=duel;
  }
  public int particleTypeNumber;
  public float xPosition,yPosition;
  public float xVelocity,yVelocity;
  public float directionAngle,speed;
  public float rotationAngle;
  public Color displayColor;
  public float strokeWeightValue;
  public float displaySize;
  public int lifespanFrameCount;
  public ParticleBuilder initialize() {
    particleTypeNumber=0;
    xPosition=0;
    yPosition=0;
    xVelocity=0;
    yVelocity=0;
    directionAngle=0;
    speed=0;
    rotationAngle=0;
    displayColor=p.theme.stroke;
    strokeWeightValue=1;
    displaySize=10;
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
    xVelocity=spd*UtilMath.cos(dir);
    yVelocity=spd*UtilMath.sin(dir);
    return this;
  }
  public ParticleBuilder rotation(float v) {
    rotationAngle=v;
    return this;
  }
  public ParticleBuilder particleColor(Color c) {
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
    lifespan(UtilMath.floor(v*Const.IDEAL_FRAME_RATE));
    return this;
  }
  public Particle build() {
    final Particle newParticle=p.game().core.commonParticleSet.allocate();
    newParticle.particleTypeNumber=this.particleTypeNumber;
    newParticle.pos.set(xPosition,yPosition);
    newParticle.vel.set(xVelocity,yVelocity);
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