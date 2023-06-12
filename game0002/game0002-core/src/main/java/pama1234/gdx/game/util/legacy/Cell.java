package pama1234.gdx.game.util.legacy;

import static pama1234.gdx.game.util.legacy.CellCenter.layer_cell_size;

import java.util.LinkedList;

import pama1234.gdx.game.app.app0002.RealGame;
import pama1234.gdx.util.entity.Entity;
import pama1234.math.physics.MassPoint;
import pama1234.math.physics.MassVar;

public class Cell extends Entity<RealGame>{
  public static final float damping=1f/8;
  public static final float size=3,dist=size;
  public static final float minScore=1f/2048;
  public CellCenter parent;
  public int meta;
  public final MassPoint point;
  public final LinkedList<Cell> back=new LinkedList<Cell>();
  public final MassVar score=new MassVar(0);
  {
    score.f=0f;
  }
  public Cell(RealGame p,CellCenter parent,int meta,float x,float y) {
    super(p);
    this.parent=parent;
    this.meta=meta;
    this.point=new MassPoint(x,y);
  }
  @Override
  public void init() {}
  @Override
  public void update() {
    point.update();
    score.pos*=damping;
    score.pos+=minScore;
    score.update();
  }
  @SuppressWarnings("static-access")
  @Override
  public void display() {
    p.fill(parent.meta.list.get(meta).colorCache);
    if(parent.boxed) {
      p.circle(
        point.pos.x-parent.x1+layer_cell_size,
        point.pos.y-parent.y1+layer_cell_size,
        size/2f);
    }else {
      float tx=point.pos.x,
        ty=point.pos.y;
      if(tx>0) tx-=parent.w;
      if(ty>0) ty-=parent.h;
      p.circle(
        tx-parent.x1+parent.w/4f,
        ty-parent.y1+parent.h/4f,
        size);
      p.circle(
        tx+parent.w-parent.x1+parent.w/4f,
        ty+parent.h-parent.y1+parent.h/4f,
        size);
      p.circle(
        tx+parent.w-parent.x1+parent.w/4f,
        ty-parent.y1+parent.h/4f,
        size);
      p.circle(
        tx-parent.x1+parent.w/4f,
        ty+parent.h-parent.y1+parent.h/4f,
        size);
    }
  }
}
