package pama1234.game.app.server.game.net;

import static pama1234.math.UtilMath.PI;

import pama1234.math.physics.PathPoint;
import pama1234.math.physics.PathPoint3D;

public class PlayerInfo3D{
  public PathPoint3D point;
  public PathPoint viewDir;
  public String name;
  public PlayerInfo3D() {}
  public PlayerInfo3D(String name) {
    this.name=name;
  }
  public PlayerInfo3D(String name,PathPoint3D point,PathPoint viewDir) {
    this.name=name;
    this.point=point;
    this.viewDir=viewDir;
  }
  public PlayerInfo3D(String name,PathPoint3D point) {
    this.name=name;
    this.point=point;
    viewDir=new PathPoint(PI/2*3,-PI/2f,0.5f);
  }
}