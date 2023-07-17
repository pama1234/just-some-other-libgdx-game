package pama1234.gdx.game.duel.util.arrow;

import pama1234.app.game.server.duel.util.arrow.ServerShortbowArrow;
import pama1234.gdx.game.duel.Duel;
import pama1234.gdx.game.duel.util.graphics.Particle;
import pama1234.math.UtilMath;
import pama1234.util.protobuf.OutputDataProto.OutputDataElement;

public class ClientShortbowArrow extends ServerShortbowArrow{
  public final Duel p;
  public ClientShortbowArrow(Duel duel) {
    super();
    this.p=duel;
  }
  public ClientShortbowArrow(Duel p,OutputDataElement proto) {
    this(p);
    copyFromProto(proto);
  }
  @Override
  public void act() {
    if((p.random(1)>=0.5f)) return;
    final float particleDirectionAngle=this.directionAngle+UtilMath.PI+p.random(-UtilMath.QUARTER_PI,UtilMath.QUARTER_PI);
    for(int i=0;i<3;i++) {
      final float particleSpeed=p.random(0.5f,2);
      final Particle newParticle=p.game().core.commonParticleSet.builder
        .type(Particle.square)
        .position(pos.x,pos.y)
        .polarVelocity(particleDirectionAngle,particleSpeed)
        .particleSize(2)
        .particleColor(p.theme.shortbowArrow)
        .lifespanSecond(0.5f)
        .build();
      p.game().core.commonParticleSet.particleList.add(newParticle);
    }
  }
  @Override
  public void display() {
    p.strokeWeight(3);
    p.stroke(p.theme.stroke);
    p.doFill();
    p.fill(0);
    p.pushMatrix();
    p.translate(pos.x,pos.y);
    p.rotate(rotationAngle);
    p.line(-halfLength,0,halfLength,0);
    p.quad(
      halfLength,0,
      halfLength-halfHeadLength,-halfHeadWidth,
      halfLength+halfHeadLength,0,
      halfLength-halfHeadLength,+halfHeadWidth);
    p.line(-halfLength,0,-halfLength-featherLength,-halfFeatherWidth);
    p.line(-halfLength,0,-halfLength-featherLength,+halfFeatherWidth);
    p.line(-halfLength+4,0,-halfLength-featherLength+4,-halfFeatherWidth);
    p.line(-halfLength+4,0,-halfLength-featherLength+4,+halfFeatherWidth);
    p.line(-halfLength+8,0,-halfLength-featherLength+8,-halfFeatherWidth);
    p.line(-halfLength+8,0,-halfLength-featherLength+8,+halfFeatherWidth);
    p.popMatrix();
  }
}