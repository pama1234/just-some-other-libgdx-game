package pama1234.gdx.game.duel.util.graphics;

import com.badlogic.gdx.graphics.Color;

import pama1234.Tools;
import pama1234.app.game.server.duel.util.Body;
import pama1234.app.game.server.duel.util.Const;
import pama1234.app.game.server.duel.util.ObjectPool;
import pama1234.app.game.server.duel.util.Poolable;
import pama1234.gdx.game.duel.ClientGameSystem;
import pama1234.gdx.game.duel.Duel;
import pama1234.math.UtilMath;

public final class Particle extends Body implements Poolable<Particle>{
  public static final int dot=0,square=1,line=2,ring=3;
  public static float f=0.98f;
  public Duel duel;
  public ClientGameSystem core;
  public Particle(Duel duel,ClientGameSystem core) {
    this.duel=duel;
    this.core=core;
  }
  // fields for Poolable
  public boolean allocatedIndicator;
  public ObjectPool<Particle> belongingPool;
  public int allocationIdentifier;
  public float rotationAngle;
  public Color displayColor;
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
    pos.set(0,0);
    vel.set(0,0);
    directionAngle=0;
    speed=0;
    rotationAngle=0;
    displayColor=duel.theme().particleDefault;
    strokeWeightValue=1;
    displaySize=10;
    lifespanFrameCount=0;
    properFrameCount=0;
    particleTypeNumber=0;
  }
  @Override
  public void update() {
    super.update();
    vel.scale(f);
    properFrameCount++;
    if(properFrameCount>lifespanFrameCount) {
      duel.core().commonParticleSet.removingParticleList.add(this);
    }
    switch(particleTypeNumber) {
      case square:
        rotationAngle+=1.5f*UtilMath.PI2/Const.IDEAL_FRAME_RATE;
        break;
      default:
        break;
    }
  }
  public float getProgressRatio() {
    return UtilMath.min(1,(float)properFrameCount/lifespanFrameCount);
  }
  public float getFadeRatio() {
    return 1-getProgressRatio();
  }
  @Override
  public void display() {
    switch(particleTypeNumber) {
      case dot:
        duel.dot(UtilMath.floor(pos.x),UtilMath.floor(pos.y),Tools.color((int)(128+127*getProgressRatio())));
        break;
      case square: {
        int alpha=colorClamp(getFadeRatio());
        if(alpha>0.01f) {
          duel.noFill();
          duel.stroke(displayColor,alpha);
          duel.strokeWeight(2);
          duel.pushMatrix();
          duel.translate(pos.x,pos.y);
          duel.rotate(rotationAngle);
          duel.rect(-displaySize/2f,-displaySize/2f,displaySize,displaySize);
          duel.popMatrix();
        }
      }
        break;
      case line: {
        int alpha=colorClamp(getFadeRatio()/2f);
        if(alpha>0.01f) {
          duel.stroke(displayColor,alpha);
          duel.strokeWeight(strokeWeightValue*UtilMath.pow(getFadeRatio(),4));
          duel.line(pos.x,pos.y,pos.x+800*UtilMath.cos(rotationAngle),pos.y+800*UtilMath.sin(rotationAngle));
        }
      }
        break;
      case ring: {
        final float ringSizeExpandRatio=2*(UtilMath.pow(getProgressRatio()-1,5)+1);
        int alpha=colorClamp(getFadeRatio());
        if(alpha>0.01f) {
          duel.noFill();
          duel.stroke(displayColor,alpha);
          duel.strokeWeight(strokeWeightValue*getFadeRatio());
          duel.circle(pos.x,pos.y,displaySize*(1+ringSizeExpandRatio)/2f);
        }
      }
        break;
      default:
        break;
    }
  }
  public int colorClamp(float in) {
    return UtilMath.constrain((int)(in*256),0,255);
  }
}