package pama1234.gdx.game.duel.util.actor;

import pama1234.gdx.game.duel.util.Body;

public abstract class Actor extends Body{
  public ActorGroup group;
  public float rotationAngle;
  public final float collisionRadius;
  public Actor(float _collisionRadius) {
    collisionRadius=_collisionRadius;
  }
  public abstract void act();
  public boolean isCollided(Actor other) {
    return getDistance(other)<this.collisionRadius+other.collisionRadius;
  }
}