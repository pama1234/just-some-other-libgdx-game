package pama1234.gdx.game.dimensional.tower.defense.util.entity;

import com.badlogic.gdx.graphics.g3d.decals.Decal;

import pama1234.gdx.game.dimensional.tower.defense.DemonDefense;
import pama1234.gdx.game.dimensional.tower.defense.util.DimCut;
import pama1234.gdx.game.dimensional.tower.defense.util.math.physics.HighMassPoint;
import pama1234.gdx.game.dimensional.tower.defense.util.math.vec.Vec12f;
import pama1234.math.Tools;
import pama1234.math.physics.PathVar;

public abstract class HighLife extends HighPointEntity<DemonDefense,HighMassPoint>{
  public PathVar life;
  public DimCut lowDimLink;
  public Decal decal;
  public HighLife(DemonDefense p,HighMassPoint point,Decal decal) {
    super(p,point);
    this.decal=decal;
    lowDimLink=new DimCut();
  }
  public abstract boolean inBody(Vec12f in);
  @Override
  public void update() {
    super.update();
    decal.setPosition(
      point.pos.data[lowDimLink.data[0]],
      point.pos.data[lowDimLink.data[1]],
      point.pos.data[lowDimLink.data[2]]);
    decal.lookAt(p.cam.camera.position,p.cam.camera.up);
  }
  @Override
  public void display() {
    p.decal(decal);
  }
  //---------------------------------------------------------------
  public static class RectLife extends HighLife{
    public Vec12f dpos,size;
    public RectLife(DemonDefense p,HighMassPoint point,Decal decal) {
      super(p,point,decal);
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
    public CircleLife(DemonDefense p,HighMassPoint point,Decal decal) {
      super(p,point,decal);
    }
    @Override
    public boolean inBody(Vec12f in) {
      return point.pos.dist(in)<size;
    }
  }
}