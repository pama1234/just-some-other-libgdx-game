package pama1234.app.game.server.duel.util.actor;

import pama1234.app.game.server.duel.util.Body;
import pama1234.util.protobuf.OutputDataProto.OutputDataElement;

public abstract class Actor extends Body{
  public ActorGroup group;
  public float rotationAngle;
  public final float collisionRadius;
  public Actor(float collisionRadius) {
    this.collisionRadius=collisionRadius;
  }
  public abstract void act();
  public boolean isCollided(Actor other) {
    return dist(other)<this.collisionRadius+other.collisionRadius;
  }
  public void copyFromProto(OutputDataElement proto) {
    pos.x=proto.getX();
    pos.y=proto.getY();
    // directionAngle=proto.getAngle();
    rotationAngle=proto.getAngle();
  }
  public void copyToProto(OutputDataElement.Builder elementBuilder) {
    elementBuilder.setX(pos.x);
    elementBuilder.setY(pos.y);
    // elementBuilder.setAngle(directionAngle);
    elementBuilder.setAngle(rotationAngle);
  }
}