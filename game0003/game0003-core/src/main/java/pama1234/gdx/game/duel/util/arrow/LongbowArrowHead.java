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
    duel.strokeWeight(5);
    duel.doStroke();
    duel.stroke(0);
    duel.doFill();
    duel.fill(0);
    duel.pushMatrix();
    duel.translate(xPosition,yPosition);
    duel.rotate(rotationAngle);
    duel.line(-halfLength,0,0,0);
    duel.quad(
      0,0,
      -halfHeadLength,-halfHeadWidth,
      halfHeadLength,0,
      -halfHeadLength,halfHeadWidth);
    duel.popMatrix();
  }
}