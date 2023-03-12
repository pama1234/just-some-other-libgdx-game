package pama1234.gdx.game.duel.util.arrow;

import pama1234.gdx.game.duel.Duel;

public final class LongbowArrowShaft extends LongbowArrowComponent{
  public LongbowArrowShaft(Duel duel) {
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
    duel.line(-halfLength,0,halfLength,0);
    duel.popMatrix();
    duel.strokeWeight(1);
  }
}