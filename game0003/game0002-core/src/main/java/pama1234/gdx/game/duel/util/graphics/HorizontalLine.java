package pama1234.gdx.game.duel.util.graphics;

import pama1234.gdx.game.duel.Duel;

public final class HorizontalLine extends BackgroundLine{
  public final Duel duel;
  public HorizontalLine(Duel duel) {
    super(duel.random(Duel.INTERNAL_CANVAS_SIDE_LENGTH));
    this.duel=duel;
  }
  @Override
  public void display() {
    duel.line(0.0f,position,Duel.INTERNAL_CANVAS_SIDE_LENGTH,position);
  }
  @Override
  public float getMaxPosition() {
    return Duel.INTERNAL_CANVAS_SIDE_LENGTH;
  }
}