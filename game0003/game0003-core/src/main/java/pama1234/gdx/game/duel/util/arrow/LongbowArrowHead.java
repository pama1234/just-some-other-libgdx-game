package pama1234.gdx.game.duel.util.arrow;

import pama1234.gdx.game.duel.Duel;

public final class LongbowArrowHead extends ClientLongbowArrowComponent{
  public final float halfHeadLength=24;
  public final float halfHeadWidth=24;
  public LongbowArrowHead(Duel duel) {
    super(duel);
  }
  @Override
  public void display() {
    p.strokeWeight(5);
    p.doStroke();
    p.stroke(p.skin.stroke);
    p.doFill();
    p.fill(0);
    p.pushMatrix();
    p.translate(xPosition,yPosition);
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