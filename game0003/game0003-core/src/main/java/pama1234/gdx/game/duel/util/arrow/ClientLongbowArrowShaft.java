package pama1234.gdx.game.duel.util.arrow;

import pama1234.gdx.game.duel.Duel;

public final class ClientLongbowArrowShaft extends ClientLongbowArrowComponent{
  public ClientLongbowArrowShaft(Duel duel) {
    super(duel);
  }
  @Override
  public void display() {
    p.strokeWeight(5);
    p.doStroke();
    p.stroke(0);
    p.doFill();
    p.fill(0);
    p.pushMatrix();
    p.translate(pos.x,pos.y);
    p.rotate(rotationAngle);
    p.line(-halfLength,0,halfLength,0);
    p.popMatrix();
  }
}