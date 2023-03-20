package pama1234.gdx.game.dimensional.tower.defense.util.math.physics;

import pama1234.gdx.game.dimensional.tower.defense.util.math.vec.Vec12f;

public abstract class HighPoint{
  public Vec12f pos;
  public float f,step=1;
  public HighPoint() {
    this.pos=new Vec12f();
  }
  public HighPoint(Vec12f pos) {
    this.pos=pos;
  }
  public abstract void update();
}