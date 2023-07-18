package pama1234.gdx.game.duel.util.player;

import pama1234.app.game.server.duel.util.Const;
import pama1234.app.game.server.duel.util.player.DoTeleportPlayerActorState;
import pama1234.app.game.server.duel.util.player.ServerPlayerActor;
import pama1234.gdx.game.duel.Duel;
import pama1234.gdx.game.duel.util.graphics.Particle;
import pama1234.math.UtilMath;

public class ClientDoTeleportPlayerActorState extends DoTeleportPlayerActorState{
  public final Duel p;
  public ClientDoTeleportPlayerActorState(Duel p) {
    super(null);
    this.p=p;
  }
  @Override
  public void fire(ServerPlayerActor parentActor) {
    parentActor.pos.set(desX,desY);
    p.game().core.screenShakeValue+=10;
  }
  @Override
  public void displayEffect(ServerPlayerActor parentActor) {
    p.noFill();
    p.stroke(p.theme.stroke);
    // p.strokeWeight(5);
    // p.arc(0,0,50,UtilMath.deg(parentActor.aimAngle)-90,180);
    boolean hasCompletedTeleportCharge=hasCompletedTeleportCharge(parentActor);
    if(hasCompletedTeleportCharge) p.stroke(p.theme.teleportEffect);
    else p.stroke(p.theme.teleportStroke);
    int l=chargeRequiredFrameCount-parentActor.teleportChargedFrameCount;
    p.rotate(UtilMath.HALF_PI);
    p.strokeWeight(ringStrokeWeight);
    p.arc(0,0,ringSize/2f,0,360*UtilMath.min(1,(float)(parentActor.teleportChargedFrameCount)/chargeRequiredFrameCount));
    p.rotate(+UtilMath.HALF_PI);
    if(hasCompletedTeleportCharge) {
      p.stroke(p.theme.teleportEffect);
      p.translate(parentActor.pos.x-desX,parentActor.pos.y-desY);
      p.rotate(p.frameCount/Const.IDEAL_FRAME_RATE%UtilMath.PI);
      p.rect(-l/2f,-l/2f,l,l);
    }
    super.displayEffect(parentActor);
  }
  @Override
  public void act(ServerPlayerActor parentActor) {
    super.act(parentActor);
    if(parentActor.teleportChargedFrameCount!=chargeRequiredFrameCount) return;
    final Particle newParticle=p.game().core.commonParticleSet.builder
      .type(Particle.ring)
      .position(parentActor.pos.x,parentActor.pos.y)
      .polarVelocity(0,0)
      .particleSize(ringSize)
      .particleColor(p.theme.teleportEffect)
      .weight(ringStrokeWeight)
      .lifespanSecond(0.5f)
      .build();
    p.game().core.commonParticleSet.particleList.add(newParticle);
  }
}