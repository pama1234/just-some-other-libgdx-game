package pama1234.gdx.game.dimensional.tower.defense.util.math.physics;

import pama1234.gdx.game.dimensional.tower.defense.util.math.vec.Vec12f;

public class HighPathPoint extends HighPoint{
  public Vec12f des;
  {
    f=0.2f;
  }
  public HighPathPoint() {
    super();
    des=new Vec12f();
  }
  public HighPathPoint(Vec12f pos) {
    super(pos);
    des=new Vec12f(pos);
  }
  @Override
  public void update() {
    pos.setEach(des,(a,b)->a+(b-a)*f);
  }
}