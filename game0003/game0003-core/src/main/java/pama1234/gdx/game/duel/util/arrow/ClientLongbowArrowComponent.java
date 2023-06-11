package pama1234.gdx.game.duel.util.arrow;

import pama1234.app.game.server.duel.util.arrow.AbstractArrowActor;
import pama1234.gdx.game.duel.Duel;
import pama1234.gdx.game.duel.util.graphics.Particle;
import pama1234.math.UtilMath;

public abstract class ClientLongbowArrowComponent extends AbstractArrowActor{
  public final Duel duel;
  public ClientLongbowArrowComponent(Duel duel) {
    super(16,16);
    this.duel=duel;
  }
  @Override
  public void act() {
    final float particleDirectionAngle=this.directionAngle+UtilMath.PI+duel.random(-UtilMath.HALF_PI,UtilMath.HALF_PI);
    for(int i=0;i<5;i++) {
      final float particleSpeed=duel.random(2,4);
      final Particle newParticle=duel.system.commonParticleSet.builder
        .type(Particle.square)
        .position(this.xPosition,this.yPosition)
        .polarVelocity(particleDirectionAngle,particleSpeed)
        .particleSize(4)
        .particleColor(duel.skin.longbowArrow)
        .lifespanSecond(1)
        .build();
      duel.system.commonParticleSet.particleList.add(newParticle);
    }
  }
  @Override
  public boolean isLethal() {
    return true;
  }
}