package pama1234.processing.game.duel.util.graphics;

import pama1234.math.Tools;
import pama1234.processing.game.duel.Duel;
import pama1234.processing.game.duel.util.Body;
import pama1234.processing.game.duel.util.ObjectPool;
import pama1234.processing.game.duel.util.Poolable;
import processing.core.PApplet;
import processing.core.PConstants;

public final class Particle extends Body implements Poolable<Particle>{
  public static final int dot=0,square=1,line=2,ring=3;
  private final Duel duel;
  public Particle(Duel duel) {
    this.duel=duel;
  }
  // fields for Poolable
  public boolean allocatedIndicator;
  public ObjectPool<Particle> belongingPool;
  public int allocationIdentifier;
  public float rotationAngle;
  public int displayColor;
  public float strokeWeightValue;
  public float displaySize;
  public int lifespanFrameCount;
  public int properFrameCount;
  public int particleTypeNumber;
  // override methods of Poolable
  @Override
  public boolean isAllocated() {
    return allocatedIndicator;
  }
  @Override
  public void setAllocated(boolean indicator) {
    allocatedIndicator=indicator;
  }
  @Override
  public ObjectPool<Particle> getBelongingPool() {
    return belongingPool;
  }
  @Override
  public void setBelongingPool(ObjectPool<?> pool) {
    belongingPool=(ObjectPool<Particle>)pool;
  }
  @Override
  public int getAllocationIdentifier() {
    return allocationIdentifier;
  }
  @Override
  public void setAllocationIdentifier(int id) {
    allocationIdentifier=id;
  }
  @Override
  public void initialize() {
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
    lifespanFrameCount=0;
    properFrameCount=0;
    particleTypeNumber=0;
  }
  @Override
  public void update() {
    super.update();
    xVelocity=xVelocity*0.98f;
    yVelocity=yVelocity*0.98f;
    properFrameCount++;
    if(properFrameCount>lifespanFrameCount) duel.system.commonParticleSet.removingParticleList.add(this);
    switch(particleTypeNumber) {
      case square:
        rotationAngle+=1.5f*PConstants.TWO_PI/Duel.IDEAL_FRAME_RATE;
        break;
      default:
        break;
    }
  }
  public float getProgressRatio() {
    return PApplet.min(1.0f,PApplet.parseFloat(properFrameCount)/lifespanFrameCount);
  }
  public float getFadeRatio() {
    return 1.0f-getProgressRatio();
  }
  @Override
  public void display() {
    switch(particleTypeNumber) {
      case dot:
        duel.set(PApplet.parseInt(xPosition),PApplet.parseInt(yPosition),Tools.color(128.0f+127.0f*getProgressRatio()));
        break;
      case square:
        duel.noFill();
        duel.stroke(displayColor,255.0f*getFadeRatio());
        duel.pushMatrix();
        duel.translate(xPosition,yPosition);
        duel.rotate(rotationAngle);
        duel.rect(0.0f,0.0f,displaySize,displaySize);
        duel.popMatrix();
        break;
      case line:
        duel.stroke(displayColor,128.0f*getFadeRatio());
        duel.strokeWeight(strokeWeightValue*PApplet.pow(getFadeRatio(),4.0f));
        duel.line(xPosition,yPosition,xPosition+800.0f*PApplet.cos(rotationAngle),yPosition+800.0f*PApplet.sin(rotationAngle));
        duel.strokeWeight(1.0f);
        break;
      case ring:
        final float ringSizeExpandRatio=2.0f*(PApplet.pow(getProgressRatio()-1.0f,5.0f)+1.0f);
        duel.noFill();
        duel.stroke(displayColor,255.0f*getFadeRatio());
        duel.strokeWeight(strokeWeightValue*getFadeRatio());
        duel.ellipse(xPosition,yPosition,displaySize*(1.0f+ringSizeExpandRatio),displaySize*(1.0f+ringSizeExpandRatio));
        duel.strokeWeight(1.0f);
        break;
      default:
        break;
    }
  }
}