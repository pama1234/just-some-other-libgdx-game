package pama1234.gdx.game.duel.util.player;

import pama1234.gdx.game.duel.Duel;
import pama1234.gdx.game.duel.util.actor.PlayerActor;
import pama1234.gdx.game.duel.util.arrow.LongbowArrowHead;
import pama1234.gdx.game.duel.util.arrow.LongbowArrowShaft;
import pama1234.gdx.game.duel.util.graphics.Particle;
import pama1234.gdx.game.duel.util.input.AbstractInputDevice;
import pama1234.math.Tools;
import pama1234.math.UtilMath;

public final class DrawLongbowPlayerActorState extends DrawBowPlayerActorState{
  private final Duel duel;
  public final float unitAngleSpeed=0.1f*UtilMath.PI2/Duel.IDEAL_FRAME_RATE;
  public final int chargeRequiredFrameCount=UtilMath.floor(0.5f*Duel.IDEAL_FRAME_RATE);
  public final int effectColor=Tools.color(192,64,64);
  public final float ringSize=80.0f;
  public final float ringStrokeWeight=5.0f;
  public DrawLongbowPlayerActorState(Duel duel) {
    this.duel=duel;
  }
  @Override
  public PlayerActorState entryState(PlayerActor parentActor) {
    parentActor.chargedFrameCount=0;
    return this;
  }
  @Override
  public void aim(PlayerActor parentActor,AbstractInputDevice input) {
    parentActor.aimAngle+=input.horizontalMoveButton*unitAngleSpeed;
  }
  @Override
  public void fire(PlayerActor parentActor) {
    final float arrowComponentInterval=24.0f;
    final int arrowShaftNumber=5;
    for(int i=0;i<arrowShaftNumber;i++) {
      LongbowArrowShaft newArrow=new LongbowArrowShaft(this.duel);
      newArrow.xPosition=parentActor.xPosition+i*arrowComponentInterval*UtilMath.cos(parentActor.aimAngle);
      newArrow.yPosition=parentActor.yPosition+i*arrowComponentInterval*UtilMath.sin(parentActor.aimAngle);
      newArrow.rotationAngle=parentActor.aimAngle;
      newArrow.setVelocity(parentActor.aimAngle,64.0f);
      parentActor.group.addArrow(newArrow);
    }
    LongbowArrowHead newArrow=new LongbowArrowHead(this.duel);
    newArrow.xPosition=parentActor.xPosition+arrowShaftNumber*arrowComponentInterval*UtilMath.cos(parentActor.aimAngle);
    newArrow.yPosition=parentActor.yPosition+arrowShaftNumber*arrowComponentInterval*UtilMath.sin(parentActor.aimAngle);
    newArrow.rotationAngle=parentActor.aimAngle;
    newArrow.setVelocity(parentActor.aimAngle,64.0f);
    final Particle newParticle=duel.system.commonParticleSet.builder
      .type(Particle.line)
      .position(parentActor.xPosition,parentActor.yPosition)
      .polarVelocity(0.0f,0.0f)
      .rotation(parentActor.aimAngle)
      .particleColor(Tools.color(192.0f,64.0f,64.0f))
      .lifespanSecond(2.0f)
      .weight(16.0f)
      .build();
    duel.system.commonParticleSet.particleList.add(newParticle);
    parentActor.group.addArrow(newArrow);
    duel.system.screenShakeValue+=10.0f;
    parentActor.chargedFrameCount=0;
    parentActor.state=moveState.entryState(parentActor);
  }
  @Override
  public void displayEffect(PlayerActor parentActor) {
    duel.noFill();
    duel.stroke(0);
    duel.arc(0,0,100,100,parentActor.aimAngle-UtilMath.QUARTER_PI,parentActor.aimAngle+UtilMath.QUARTER_PI);
    if(hasCompletedLongBowCharge(parentActor)) duel.stroke(effectColor);
    else duel.stroke(0,128);
    duel.line(0.0f,0.0f,800.0f*UtilMath.cos(parentActor.aimAngle),800.0f*UtilMath.sin(parentActor.aimAngle));
    duel.rotate(UtilMath.HALF_PI);
    duel.strokeWeight(ringStrokeWeight);
    duel.arc(0,0,ringSize,ringSize,0,UtilMath.TWO_PI*UtilMath.min(1.0f,UtilMath.floor(parentActor.chargedFrameCount)/chargeRequiredFrameCount));
    duel.strokeWeight(1.0f);
    duel.rotate(+UtilMath.HALF_PI);
    parentActor.chargedFrameCount++;
  }
  @Override
  public void act(PlayerActor parentActor) {
    super.act(parentActor);
    if(parentActor.chargedFrameCount!=chargeRequiredFrameCount) return;
    final Particle newParticle=duel.system.commonParticleSet.builder
      .type(Particle.ring)
      .position(parentActor.xPosition,parentActor.yPosition)
      .polarVelocity(0.0f,0.0f)
      .particleSize(ringSize)
      .particleColor(effectColor)
      .weight(ringStrokeWeight)
      .lifespanSecond(0.5f)
      .build();
    duel.system.commonParticleSet.particleList.add(newParticle);
  }
  @Override
  public boolean isDrawingLongBow() {
    return true;
  }
  @Override
  public boolean hasCompletedLongBowCharge(PlayerActor parentActor) {
    return parentActor.chargedFrameCount>=chargeRequiredFrameCount;
  }
  @Override
  public boolean buttonPressed(AbstractInputDevice input) {
    return input.longShotButtonPressed;
  }
  @Override
  public boolean triggerPulled(PlayerActor parentActor) {
    return !buttonPressed(parentActor.engine.controllingInputDevice)&&hasCompletedLongBowCharge(parentActor);
  }
}