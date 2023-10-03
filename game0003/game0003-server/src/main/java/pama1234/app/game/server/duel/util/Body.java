package pama1234.app.game.server.duel.util;

import pama1234.math.UtilMath;
import pama1234.math.vec.Vec2f;

public abstract class Body{
  public Vec2f pos=new Vec2f();
  public Vec2f vel=new Vec2f();
  public float directionAngle;
  public float speed;
  public void update() {
    pos.add(vel);
  }
  public abstract void display();
  public void vel(float dir,float spd) {
    directionAngle=dir;
    speed=spd;
    vel.set(speed*UtilMath.cos(dir),speed*UtilMath.sin(dir));
  }
  public float dist(Body other) {
    return UtilMath.dist(this.pos.x,this.pos.y,other.pos.x,other.pos.y);
  }
  public float distPow2(Body other) {
    return UtilMath.sq(other.pos.x-this.pos.x)+UtilMath.sq(other.pos.y-this.pos.y);
  }
  public float angle(Body other) {
    return UtilMath.atan2(other.pos.y-this.pos.y,other.pos.x-this.pos.x);
  }
}