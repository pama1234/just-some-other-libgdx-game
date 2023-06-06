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
  public ServerPlayerActor(PlayerEngine engine) {
    super(16,engine);
  }
  public void addVelocity(float xAcceleration,float yAcceleration) {
    xVelocity=UtilMath.constrain(xVelocity+xAcceleration,-10,10);
    yVelocity=UtilMath.constrain(yVelocity+yAcceleration,-7,7);
  }
  @Override
  public void act() {
    engine.run(this);
    state.act(this);
  }
  @Override
  public void update() {
    super.update();
    if(xPosition<halfBodySize) {
      xPosition=halfBodySize;
      xVelocity=-0.5f*xVelocity;
    }
    if(xPosition>Const.CANVAS_SIZE-halfBodySize) {
      xPosition=Const.CANVAS_SIZE-halfBodySize;
      xVelocity=-0.5f*xVelocity;
    }
    if(yPosition<halfBodySize) {
      yPosition=halfBodySize;
      yVelocity=-0.5f*yVelocity;
    }
    if(yPosition>Const.CANVAS_SIZE-halfBodySize) {
      yPosition=Const.CANVAS_SIZE-halfBodySize;
      yVelocity=-0.5f*yVelocity;
    }
    xVelocity=xVelocity*0.92f;
    yVelocity=yVelocity*0.92f;
    rotationAngle+=(0.1f+0.04f*(UtilMath.sq(xVelocity)+UtilMath.sq(yVelocity)))*UtilMath.PI2/Const.IDEAL_FRAME_RATE;
  }
  @Override
  public void display() {}
}
