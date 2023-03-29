package pama1234.gdx.game.duel.util.actor;

import com.badlogic.gdx.graphics.Color;

import pama1234.gdx.game.duel.Duel;
import pama1234.gdx.game.duel.util.player.PlayerEngine;
import pama1234.math.UtilMath;

public final class PlayerActor extends AbstractPlayerActor{
  private final Duel duel;
  public final float bodySize=32;
  public final float halfBodySize=bodySize*0.5f;
  public final Color fillColor;
  public float aimAngle;
  public int chargedFrameCount;
  public int damageRemainingFrameCount;
  public PlayerActor(Duel duel,PlayerEngine engine,Color color) {
    super(16,engine);
    this.duel=duel;
    fillColor=color;
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
    duel.stroke(0);
    duel.strokeWeight(3);
    duel.doFill();
    duel.fill(fillColor);
    duel.pushMatrix();
    duel.translate(xPosition,yPosition);
    duel.pushMatrix();
    duel.rotate(rotationAngle);
    duel.rect(-16,-16,32,32);
    duel.popMatrix();
    state.displayEffect(this);
    duel.popMatrix();
  }
}