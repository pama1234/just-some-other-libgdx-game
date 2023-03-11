package pama1234.gdx.game.duel.util.arrow;

import pama1234.gdx.game.duel.Duel;

public final class LongbowArrowHead extends LongbowArrowComponent{
  public final float halfHeadLength=24.0f;
  public final float halfHeadWidth=24.0f;
  public LongbowArrowHead(Duel duel) {
    super(duel);
  }
  @Override
  public void display() {
    duel.strokeWeight(5);
    duel.stroke(0);
    duel.doFill();
    duel.fill(0);
    duel.pushMatrix();
    duel.translate(xPosition,yPosition);
    duel.rotate(rotationAngle);
    duel.line(-halfLength,0.0f,0.0f,0.0f);
    duel.quad(
      0.0f,0.0f,
      -halfHeadLength,-halfHeadWidth,
      +halfHeadLength,0.0f,
      -halfHeadLength,+halfHeadWidth);
    duel.popMatrix();
    duel.strokeWeight(1);
  }
}