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
    this.duel.strokeWeight(5.0f);
    this.duel.stroke(0);
    this.duel.fill(0);
    this.duel.pushMatrix();
    this.duel.translate(xPosition,yPosition);
    this.duel.rotate(rotationAngle);
    this.duel.line(-halfLength,0.0f,0.0f,0.0f);
    this.duel.quad(
      0.0f,0.0f,
      -halfHeadLength,-halfHeadWidth,
      +halfHeadLength,0.0f,
      -halfHeadLength,+halfHeadWidth);
    this.duel.popMatrix();
    this.duel.strokeWeight(1.0f);
  }
}