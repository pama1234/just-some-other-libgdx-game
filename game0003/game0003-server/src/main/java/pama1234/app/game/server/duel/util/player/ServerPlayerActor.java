package pama1234.app.game.server.duel.util.player;

import pama1234.app.game.server.duel.util.Const;
import pama1234.app.game.server.duel.util.actor.AbstractPlayerActor;
import pama1234.math.UtilMath;

public class ServerPlayerActor extends AbstractPlayerActor{
  public final float bodySize=32;
  public final float halfBodySize=bodySize*0.5f;
  public float aimAngle;
  public int chargedFrameCount;
  public int damageRemainingFrameCount;
  //---
  public int teleportChargedFrameCount;
  public ServerPlayerActor(PlayerEngine engine) {
    super(16,engine);
  }
  public void addVelocity(float xAcceleration,float yAcceleration) {
    vel.x=UtilMath.constrain(vel.x+xAcceleration,-10,10);
    vel.y=UtilMath.constrain(vel.y+yAcceleration,-7,7);
  }
  @Override
  public void act() {
    engine.run(this);
    state.act(this);
  }
  @Override
  public void update() {
    super.update();
    if(pos.x<halfBodySize) {
      pos.x=halfBodySize;
      vel.x=-0.5f*vel.x;
    }
    if(pos.x>Const.CANVAS_SIZE-halfBodySize) {
      pos.x=Const.CANVAS_SIZE-halfBodySize;
      vel.x=-0.5f*vel.x;
    }
    if(pos.y<halfBodySize) {
      pos.y=halfBodySize;
      vel.y=-0.5f*vel.y;
    }
    if(pos.y>Const.CANVAS_SIZE-halfBodySize) {
      pos.y=Const.CANVAS_SIZE-halfBodySize;
      vel.y=-0.5f*vel.y;
    }
    vel.x=vel.x*0.92f;
    vel.y=vel.y*0.92f;
    rotationAngle+=(0.1f+0.04f*(UtilMath.sq(vel.x)+UtilMath.sq(vel.y)))*UtilMath.PI2/Const.IDEAL_FRAME_RATE;
    state.update(this);
  }
  @Override
  public void display() {}
}
