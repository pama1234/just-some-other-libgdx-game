package pama1234.gdx.game.dimensional.tower.defense.util.math.physics;

import pama1234.gdx.game.dimensional.tower.defense.util.math.vec.Vec12f;

public class HighPathPoint extends HighPoint{
  public Vec12f des;
  {
    f=0.2f;
  }
  @Override
  public void update() {
    pos.setEach(des,(a,b)->a+(b-a)*f);
  }
}