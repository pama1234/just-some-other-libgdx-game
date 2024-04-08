package pama1234.gdx.game.element.duel.util.graphics;


import pama1234.gdx.game.element.duel.Duel;
import pama1234.gdx.game.element.duel.server.util.Const;

public final class VerticalLine extends BackgroundLine {
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