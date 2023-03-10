package pama1234.gdx.game.duel.util.actor;

import pama1234.gdx.game.duel.Duel;
import pama1234.gdx.game.duel.util.ai.PlayerEngine;
import pama1234.math.UtilMath;

public final class PlayerActor extends AbstractPlayerActor{
  private final Duel duel;
  public final float bodySize=32.0f;
  public final float halfBodySize=bodySize*0.5f;
  public final int fillColor;
  public float aimAngle;
  public int chargedFrameCount;
  public int damageRemainingFrameCount;
  public PlayerActor(Duel duel,PlayerEngine _engine,int col) {
    super(16.0f,_engine);
    this.duel=duel;
    fillColor=col;
  }
  public void addVelocity(float xAcceleration,float yAcceleration) {
    xVelocity=UtilMath.constrain(xVelocity+xAcceleration,-10.0f,10.0f);
    yVelocity=UtilMath.constrain(yVelocity+yAcceleration,-7.0f,7.0f);
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
    if(xPosition>Duel.INTERNAL_CANVAS_SIDE_LENGTH-halfBodySize) {
      xPosition=Duel.INTERNAL_CANVAS_SIDE_LENGTH-halfBodySize;
      xVelocity=-0.5f*xVelocity;
    }
    if(yPosition<halfBodySize) {
      yPosition=halfBodySize;
      yVelocity=-0.5f*yVelocity;
    }
    if(yPosition>Duel.INTERNAL_CANVAS_SIDE_LENGTH-halfBodySize) {
      yPosition=Duel.INTERNAL_CANVAS_SIDE_LENGTH-halfBodySize;
      yVelocity=-0.5f*yVelocity;
    }
    xVelocity=xVelocity*0.92f;
    yVelocity=yVelocity*0.92f;
    rotationAngle+=(0.1f+0.04f*(UtilMath.sq(xVelocity)+UtilMath.sq(yVelocity)))*UtilMath.PI2/Duel.IDEAL_FRAME_RATE;
  }
  @Override
  public void display() {
    this.duel.stroke(0);
    this.duel.fill(fillColor);
    this.duel.pushMatrix();
    this.duel.translate(xPosition,yPosition);
    this.duel.pushMatrix();
    this.duel.rotate(rotationAngle);
    this.duel.rect(0.0f,0.0f,32.0f,32.0f);
    this.duel.popMatrix();
    state.displayEffect(this);
    this.duel.popMatrix();
  }
}