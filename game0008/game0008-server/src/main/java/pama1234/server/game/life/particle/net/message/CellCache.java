package pama1234.server.game.life.particle.net.message;

import pama1234.math.physics.MassPoint;

public class CellCache{
  public int id;
  public byte meta;
  // public boolean highPriority;
  public MassPoint point=new MassPoint(0,0);
  public CellCache copyFrom(int id,byte meta,MassPoint point) {
    this.id=id;
    this.meta=meta;
    this.point.set(point);
    return this;
  }
}
