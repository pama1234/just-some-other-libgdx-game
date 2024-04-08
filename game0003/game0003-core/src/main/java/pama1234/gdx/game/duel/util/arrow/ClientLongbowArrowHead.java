package pama1234.gdx.game.duel.util.arrow;

import pama1234.gdx.game.duel.Duel;

public final class ClientLongbowArrowHead extends ClientLongbowArrowComponent{
  public final float halfHeadLength=24;
  public final float halfHeadWidth=24;
  public ClientLongbowArrowHead(Duel duel) {
    super(duel);
  }
  // public ClientLongbowArrowHead(Duel p,OutputDataElement proto) {
  //   this(p);
  //   copyFromProto(proto);
  // }
  @Override
  public void display() {
    p.strokeWeight(5);
    p.doStroke();
    p.stroke(p.theme().stroke);
    p.doFill();
    p.fill(p.theme().longbowArrow);
    p.pushMatrix();
    p.translate(pos.x,pos.y);
    p.rotate(rotationAngle);
    p.line(-halfLength,0,0,0);
    p.quad(
      0,0,
      -halfHeadLength,-halfHeadWidth,
      halfHeadLength,0,
      -halfHeadLength,halfHeadWidth);
    p.popMatrix();
  }
}