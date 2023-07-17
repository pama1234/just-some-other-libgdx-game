package pama1234.gdx.game.duel.util.arrow;

import pama1234.app.game.server.duel.util.arrow.ServerLongbowArrowComponent;
import pama1234.gdx.game.duel.Duel;
import pama1234.gdx.game.duel.util.graphics.Particle;
import pama1234.math.UtilMath;

public abstract class ClientLongbowArrowComponent extends ServerLongbowArrowComponent{
  public final Duel p;
  public ClientLongbowArrowComponent(Duel duel) {
    super();
    this.p=duel;
  }
  @Override
  public void act() {
    final float particleDirectionAngle=this.directionAngle+UtilMath.PI+p.random(-UtilMath.HALF_PI,UtilMath.HALF_PI);
    for(int i=0;i<5;i++) {
      final float particleSpeed=p.random(2,4);
      final Particle newParticle=p.game().core.commonParticleSet.builder
        .type(Particle.square)
        .position(pos.x,pos.y)
        .polarVelocity(particleDirectionAngle,particleSpeed)
        .particleSize(4)
        .particleColor(p.theme.longbowArrow)
        .lifespanSecond(1)
        .build();
      p.game().core.commonParticleSet.particleList.add(newParticle);
    }
  }
}