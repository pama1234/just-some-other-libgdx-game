package pama1234.gdx.game.duel.util.player;

import pama1234.app.game.server.duel.util.player.DoTeleportPlayerActorState;
import pama1234.app.game.server.duel.util.player.ServerPlayerActor;
import pama1234.gdx.game.duel.Duel;
import pama1234.gdx.game.duel.util.graphics.Particle;
import pama1234.math.UtilMath;

public class ClientDoTeleportPlayerActorState extends DoTeleportPlayerActorState{
  public final Duel p;
  // public final Color effectColor;
  public ClientDoTeleportPlayerActorState(Duel p) {
    super(null);
    this.p=p;
    // effectColor=duel.theme.longbowEffect;
  }
  @Override
  public void fire(ServerPlayerActor parentActor) {
    // p.game().core
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
    super.displayEffect(parentActor);
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