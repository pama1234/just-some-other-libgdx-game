package pama1234.gdx.game.dimensional.tower.defense.util.entity;

import pama1234.gdx.game.dimensional.tower.defense.DemonDefense;
import pama1234.gdx.game.dimensional.tower.defense.util.math.physics.HighMassPoint;
import pama1234.gdx.game.dimensional.tower.defense.util.math.vec.Vec12f;
import pama1234.math.Tools;
import pama1234.math.physics.PathVar;

public abstract class HighLife extends HighPointEntity<DemonDefense,HighMassPoint>{
  public PathVar life;
  public HighLife(DemonDefense p) {
    super(p);
  }
  public abstract boolean inBody(Vec12f in);
  public static class RectLife extends HighLife{
    public Vec12f dpos,size;
    public RectLife(DemonDefense p) {
      super(p);
    }
    @Override
    public boolean inBody(Vec12f in) {
      float[] data=point.pos.data;
      for(int i=0;i<data.length;i++) if(!Tools.inBox(in.data[i],data[i]-dpos.data[i],size.data[i])) return false;
      return true;
    }
  }
  public static class CircleLife extends HighLife{
    public float size;
    public CircleLife(DemonDefense p) {
      super(p);
    }
    @Override
    public boolean inBody(Vec12f in) {
      return point.pos.dist(in)<size;
    }
  }
}