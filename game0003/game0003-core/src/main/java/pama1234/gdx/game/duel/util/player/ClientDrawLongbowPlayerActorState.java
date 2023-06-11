package pama1234.gdx.game.duel.util.player;

import com.badlogic.gdx.graphics.Color;

import pama1234.app.game.server.duel.util.Const;
import pama1234.app.game.server.duel.util.input.AbstractInputDevice;
import pama1234.app.game.server.duel.util.player.PlayerActorState;
import pama1234.app.game.server.duel.util.player.ServerDrawLongbowPlayerActorState;
import pama1234.app.game.server.duel.util.player.ServerPlayerActor;
import pama1234.gdx.game.duel.Duel;
import pama1234.gdx.game.duel.util.arrow.LongbowArrowHead;
import pama1234.gdx.game.duel.util.arrow.LongbowArrowShaft;
import pama1234.gdx.game.duel.util.graphics.Particle;
import pama1234.math.UtilMath;

public final class ClientDrawLongbowPlayerActorState extends ServerDrawLongbowPlayerActorState{
  private final Duel p;
  public final float unitAngleSpeed=0.1f*UtilMath.PI2/Const.IDEAL_FRAME_RATE;
  public final int chargeRequiredFrameCount=UtilMath.floor(0.5f*Const.IDEAL_FRAME_RATE);
  public final Color effectColor;
  public final float ringSize=80;
  public final float ringStrokeWeight=8;
  public ClientDrawLongbowPlayerActorState(Duel duel) {
    super(null);
    this.p=duel;
    effectColor=duel.skin.longbowEffect;
  }
  @Override
  public PlayerActorState entryState(ServerPlayerActor parentActor) {
    parentActor.chargedFrameCount=0;
    return this;
  }
  @Override
  public void aim(ServerPlayerActor parentActor,AbstractInputDevice input) {
    parentActor.aimAngle+=input.horizontalMove*unitAngleSpeed;
  }
  @Override
  public void fire(ServerPlayerActor parentActor) {
    final float arrowComponentInterval=24;
    final int arrowShaftNumber=5;
    for(int i=0;i<arrowShaftNumber;i++) {
      LongbowArrowShaft newArrow=new LongbowArrowShaft(p);
      newArrow.xPosition=parentActor.xPosition+i*arrowComponentInterval*UtilMath.cos(parentActor.aimAngle);
      newArrow.yPosition=parentActor.yPosition+i*arrowComponentInterval*UtilMath.sin(parentActor.aimAngle);
      newArrow.rotationAngle=parentActor.aimAngle;
      newArrow.setVelocity(parentActor.aimAngle,64);
      parentActor.group.addArrow(newArrow);
    }
    LongbowArrowHead newArrow=new LongbowArrowHead(p);
    newArrow.xPosition=parentActor.xPosition+arrowShaftNumber*arrowComponentInterval*UtilMath.cos(parentActor.aimAngle);
    newArrow.yPosition=parentActor.yPosition+arrowShaftNumber*arrowComponentInterval*UtilMath.sin(parentActor.aimAngle);
    newArrow.rotationAngle=parentActor.aimAngle;
    newArrow.setVelocity(parentActor.aimAngle,64);
    final Particle newParticle=p.stateCenter.game.system.commonParticleSet.builder
      .type(Particle.line)
      .position(parentActor.xPosition,parentActor.yPosition)
      .polarVelocity(0,0)
      .rotation(parentActor.aimAngle)
      .particleColor(p.skin.longbowLine)
      .lifespanSecond(2)
      .weight(16)
      .build();
    p.stateCenter.game.system.commonParticleSet.particleList.add(newParticle);
    parentActor.group.addArrow(newArrow);
    p.stateCenter.game.system.screenShakeValue+=10;
    parentActor.chargedFrameCount=0;
    parentActor.state=moveState.entryState(parentActor);
  }
  @Override
  public void displayEffect(ServerPlayerActor parentActor) {
    p.noFill();
    p.stroke(p.skin.stroke);
    p.strokeWeight(5);
    p.arc(0,0,50,UtilMath.deg(parentActor.aimAngle)-90,180);
    if(hasCompletedLongBowCharge(parentActor)) p.stroke(effectColor);
    else p.stroke(0,128);
    p.line(0,0,800*UtilMath.cos(parentActor.aimAngle),800*UtilMath.sin(parentActor.aimAngle));
    p.rotate(UtilMath.HALF_PI);
    p.strokeWeight(ringStrokeWeight);
    p.arc(0,0,ringSize/2f,0,360*UtilMath.min(1,(float)(parentActor.chargedFrameCount)/chargeRequiredFrameCount));
    p.rotate(+UtilMath.HALF_PI);
    // parentActor.chargedFrameCount++;
    super.displayEffect(parentActor);
  }
  @Override
  public void act(ServerPlayerActor parentActor) {
    super.act(parentActor);
    if(parentActor.chargedFrameCount!=chargeRequiredFrameCount) return;
    final Particle newParticle=p.stateCenter.game.system.commonParticleSet.builder
      .type(Particle.ring)
      .position(parentActor.xPosition,parentActor.yPosition)
      .polarVelocity(0,0)
      .particleSize(ringSize)
      .particleColor(effectColor)
      .weight(ringStrokeWeight)
      .lifespanSecond(0.5f)
      .build();
    p.stateCenter.game.system.commonParticleSet.particleList.add(newParticle);
  }
}