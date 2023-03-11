package pama1234.gdx.game.duel.util.graphics;

import pama1234.gdx.game.duel.Duel;
import pama1234.gdx.game.duel.util.Body;
import pama1234.gdx.game.duel.util.ObjectPool;
import pama1234.gdx.game.duel.util.Poolable;
import pama1234.math.Tools;
import pama1234.math.UtilMath;

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
    displayColor=Tools.color(0);
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
        rotationAngle+=1.5f*UtilMath.TWO_PI/Duel.IDEAL_FRAME_RATE;
        break;
      default:
        break;
    }
  }
  public float getProgressRatio() {
    return UtilMath.min(1.0f,UtilMath.floor(properFrameCount)/lifespanFrameCount);
  }
  public float getFadeRatio() {
    return 1.0f-getProgressRatio();
  }
  @Override
  public void display() {
    switch(particleTypeNumber) {
      case dot:
        duel.dot(UtilMath.floor(xPosition),UtilMath.floor(yPosition),Tools.color((int)(128+127*getProgressRatio())));
        break;
      case square:
        duel.noFill();
        duel.stroke(displayColor,(int)(255*getFadeRatio()));
        duel.pushMatrix();
        duel.translate(xPosition,yPosition);
        duel.rotate(rotationAngle);
        duel.rect(-displaySize/2f,-displaySize/2f,displaySize,displaySize);
        duel.popMatrix();
        break;
      case line:
        duel.stroke(displayColor,(int)(128*getFadeRatio()));
        duel.strokeWeight(strokeWeightValue*UtilMath.pow(getFadeRatio(),4.0f));
        duel.line(xPosition,yPosition,xPosition+800.0f*UtilMath.cos(rotationAngle),yPosition+800.0f*UtilMath.sin(rotationAngle));
        duel.strokeWeight(1.0f);
        break;
      case ring:
        final float ringSizeExpandRatio=2.0f*(UtilMath.pow(getProgressRatio()-1.0f,5.0f)+1.0f);
        duel.noFill();
        duel.stroke(displayColor,(int)(255*getFadeRatio()));
        duel.strokeWeight(strokeWeightValue*getFadeRatio());
        duel.circle(xPosition,yPosition,displaySize*(1.0f+ringSizeExpandRatio));
        duel.strokeWeight(1.0f);
        break;
      default:
        break;
    }
  }
}