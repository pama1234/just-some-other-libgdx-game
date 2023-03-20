package pama1234.gdx.game.state.state0001.game.region;

import pama1234.math.physics.PathVar;

public class PathVarLighting{
  public PathVar r,g,b;
  public PathVarLighting() {
    int ti=0;
    r=new PathVar(ti,0.05f);
    g=new PathVar(ti,0.05f);
    b=new PathVar(ti,0.05f);
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
  public void set(float rIn,float gIn,float bIn) {
    r.des=rIn;
    g.des=gIn;
    b.des=bIn;
  }
  @Override
  public String toString() {
    return formatFloat(r.des)+" "+formatFloat(g.des)+" "+formatFloat(b.des);
  }
  public String formatFloat(float in) {
    return String.format("%04.1f",in);
  }
}
