package pama1234.gdx.game.state.state0001.game.region;

import pama1234.math.physics.PathVar;

public class PathVarLighting{
  public PathVar r,g,b;
  public PathVarLighting() {
    r=new PathVar(255,0.05f);
    g=new PathVar(255,0.05f);
    b=new PathVar(255,0.05f);
  }
  public void update() {
    r.update();
    g.update();
    b.update();
  }
  public float r() {
    return r.pos;
  }
  public float g() {
    return g.pos;
  }
  public float b() {
    return b.pos;
  }
  public void set(float in) {
    r.des=g.des=b.des=in;
  }
}
