package pama1234.app.game.server.duel.util;

import pama1234.math.UtilMath;

public abstract class Body{
  public float xPosition,yPosition;
  public float xVelocity,yVelocity;
  public float directionAngle;
  public float speed;
  public void update() {
    xPosition+=xVelocity;
    yPosition+=yVelocity;
  }
  public abstract void display();
  public void setVelocity(float dir,float spd) {
    directionAngle=dir;
    speed=spd;
    xVelocity=speed*UtilMath.cos(dir);
    yVelocity=speed*UtilMath.sin(dir);
  }
  public float getDistance(Body other) {
    return UtilMath.dist(this.xPosition,this.yPosition,other.xPosition,other.yPosition);
  }
  public float getDistancePow2(Body other) {
    return UtilMath.sq(other.xPosition-this.xPosition)+UtilMath.sq(other.yPosition-this.yPosition);
  }
  public float getAngle(Body other) {
    return UtilMath.atan2(other.yPosition-this.yPosition,other.xPosition-this.xPosition);
  }
}