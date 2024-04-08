package pama1234.gdx.game.dimensional.tower.defense.util.math.physics;

import pama1234.gdx.game.dimensional.tower.defense.util.math.vec.Vec12f;

public class HighMassPoint extends HighPoint{
  public Vec12f vel;
  {
    f=0.8f;
  }
  public HighMassPoint() {
    super();
    vel=new Vec12f();
  }
  public HighMassPoint(Vec12f pos) {
    super(pos);
    vel=new Vec12f();
  }
  @Override
  public void update() {
    if(step==1) {
      pos.add(vel);
      if(f!=1) vel.scale(f);
    }else {
      pos.setEach(vel,(a,b)->a+b*step);
      if(f!=1) {
        float tf=(f-1)*step;
        vel.setEach(vel,(a,b)->a+b*tf);
      }
    }
  }
}