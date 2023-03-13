package pama1234.gdx.game.dimensional.tower.defense.util;

public class HighPathPoint extends HighPoint{
  public Vec12f des;
  {
    f=0.2f;
  }
  @Override
  public void update() {
    pos.execute(des,(a,b)->a+(b-a)*f);
  }
}