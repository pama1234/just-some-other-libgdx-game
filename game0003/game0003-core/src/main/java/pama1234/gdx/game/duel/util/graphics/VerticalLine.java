package pama1234.gdx.game.duel.util.graphics;

import pama1234.app.game.server.duel.util.Const;
import pama1234.gdx.game.duel.Duel;

public final class VerticalLine extends BackgroundLine{
  public final Duel duel;
  public VerticalLine(Duel duel) {
    super(duel.random(Const.CANVAS_SIZE));
    this.duel=duel;
  }
  @Override
  public void display() {
    duel.line(position,0.0f,position,Const.CANVAS_SIZE);
  }
  @Override
  public float getMaxPosition() {
    return Const.CANVAS_SIZE;
  }
}