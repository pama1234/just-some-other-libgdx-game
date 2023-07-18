package pama1234.gdx.game.duel.util.player;

import pama1234.app.game.server.duel.util.player.ServerDrawLongbowPlayerActorState;
import pama1234.app.game.server.duel.util.player.ServerPlayerActor;
import pama1234.gdx.game.duel.Duel;
import pama1234.gdx.game.duel.util.arrow.ClientLongbowArrowHead;
import pama1234.gdx.game.duel.util.arrow.ClientLongbowArrowShaft;
import pama1234.gdx.game.duel.util.graphics.Particle;
import pama1234.math.UtilMath;

public final class ClientDrawLongbowPlayerActorState extends ServerDrawLongbowPlayerActorState{
  public final Duel p;
  public ClientDrawLongbowPlayerActorState(Duel p) {
    super(null);
    this.p=p;
  }
  @Override
  public void fire(ServerPlayerActor parentActor) {
    final float arrowComponentInterval=24;
    final int arrowShaftNumber=5;
    for(int i=0;i<arrowShaftNumber;i++) {
      ClientLongbowArrowShaft newArrow=new ClientLongbowArrowShaft(p);
      newArrow.pos.x=parentActor.pos.x+i*arrowComponentInterval*UtilMath.cos(parentActor.aimAngle);
      newArrow.pos.y=parentActor.pos.y+i*arrowComponentInterval*UtilMath.sin(parentActor.aimAngle);
      newArrow.rotationAngle=parentActor.aimAngle;
      newArrow.vel(parentActor.aimAngle,64);
      parentActor.group.addArrow(newArrow);
    }
    ClientLongbowArrowHead newArrow=new ClientLongbowArrowHead(p);
    newArrow.pos.x=parentActor.pos.x+arrowShaftNumber*arrowComponentInterval*UtilMath.cos(parentActor.aimAngle);
    newArrow.pos.y=parentActor.pos.y+arrowShaftNumber*arrowComponentInterval*UtilMath.sin(parentActor.aimAngle);
    newArrow.rotationAngle=parentActor.aimAngle;
    newArrow.vel(parentActor.aimAngle,64);
    parentActor.group.addArrow(newArrow);
    parentActor.chargedFrameCount=0;
    parentActor.state=moveState.entryState(parentActor);
    // super.fire(parentActor);
    final Particle newParticle=p.game().core.commonParticleSet.builder
      .type(Particle.line)
      .position(parentActor.pos.x,parentActor.pos.y)
      .polarVelocity(0,0)
      .rotation(parentActor.aimAngle)
      .particleColor(p.theme.longbowLine)
      .lifespanSecond(2)
      .weight(16)
      .build();
    p.game().core.commonParticleSet.particleList.add(newParticle);
    p.game().core.screenShakeValue+=10;
  }
  @Override
  public void displayEffect(ServerPlayerActor parentActor) {
    p.noFill();
    p.stroke(p.theme.stroke);
    p.strokeWeight(5);
    p.arc(0,0,50,UtilMath.deg(parentActor.aimAngle)-90,180);
    if(hasCompletedLongBowCharge(parentActor)) p.stroke(p.theme.longbowEffect);
    else p.stroke(p.theme.longbowStroke);
    p.line(0,0,800*UtilMath.cos(parentActor.aimAngle),800*UtilMath.sin(parentActor.aimAngle));
    p.rotate(UtilMath.HALF_PI);
    p.strokeWeight(ringStrokeWeight);
    p.arc(0,0,ringSize/2f,0,360*UtilMath.min(1,(float)(parentActor.chargedFrameCount)/chargeRequiredFrameCount));
    p.rotate(+UtilMath.HALF_PI);
  }
  @Override
  public void act(ServerPlayerActor parentActor) {
    super.act(parentActor);
    if(parentActor.chargedFrameCount!=chargeRequiredFrameCount) return;
    final Particle newParticle=p.game().core.commonParticleSet.builder
      .type(Particle.ring)
      .position(parentActor.pos.x,parentActor.pos.y)
      .polarVelocity(0,0)
      .particleSize(ringSize)
      .particleColor(p.theme.longbowEffect)
      .weight(ringStrokeWeight)
      .lifespanSecond(0.5f)
      .build();
    p.game().core.commonParticleSet.particleList.add(newParticle);
  }
}