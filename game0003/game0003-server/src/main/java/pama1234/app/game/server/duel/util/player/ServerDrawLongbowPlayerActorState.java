package pama1234.app.game.server.duel.util.player;

import pama1234.app.game.server.duel.DuelServer;
import pama1234.app.game.server.duel.util.Const;
import pama1234.app.game.server.duel.util.input.AbstractInputDevice;
import pama1234.math.UtilMath;

public class ServerDrawLongbowPlayerActorState extends DrawBowPlayerActorState{
  public DuelServer duelSever;
  public final float unitAngleSpeed=0.1f*UtilMath.PI2/Const.IDEAL_FRAME_RATE;
  public final int chargeRequiredFrameCount=UtilMath.floor(0.5f*Const.IDEAL_FRAME_RATE);
  public final float ringSize=80;
  public final float ringStrokeWeight=8;
  public ServerDrawLongbowPlayerActorState(DuelServer duelSever) {
    this.duelSever=duelSever;
  }
  @Override
  public PlayerActorState entryState(ServerPlayerActor parentActor) {
    parentActor.chargedFrameCount=0;
    return this;
  }
  @Override
  public void aim(ServerPlayerActor parentActor,AbstractInputDevice input) {
    parentActor.aimAngle+=input.horizontalMove*unitAngleSpeed;
  }
  @Override
  public void fire(ServerPlayerActor parentActor) {
    // final float arrowComponentInterval=24;
    // final int arrowShaftNumber=5;
    // for(int i=0;i<arrowShaftNumber;i++) {
    //   LongbowArrowShaft newArrow=new LongbowArrowShaft(duelSever);
    //   newArrow.xPosition=parentActor.xPosition+i*arrowComponentInterval*UtilMath.cos(parentActor.aimAngle);
    //   newArrow.yPosition=parentActor.yPosition+i*arrowComponentInterval*UtilMath.sin(parentActor.aimAngle);
    //   newArrow.rotationAngle=parentActor.aimAngle;
    //   newArrow.setVelocity(parentActor.aimAngle,64);
    //   parentActor.group.addArrow(newArrow);
    // }
    // LongbowArrowHead newArrow=new LongbowArrowHead(duelSever);
    // newArrow.xPosition=parentActor.xPosition+arrowShaftNumber*arrowComponentInterval*UtilMath.cos(parentActor.aimAngle);
    // newArrow.yPosition=parentActor.yPosition+arrowShaftNumber*arrowComponentInterval*UtilMath.sin(parentActor.aimAngle);
    // newArrow.rotationAngle=parentActor.aimAngle;
    // newArrow.setVelocity(parentActor.aimAngle,64);
    // final Particle newParticle=duel.system.commonParticleSet.builder
    //   .type(Particle.line)
    //   .position(parentActor.xPosition,parentActor.yPosition)
    //   .polarVelocity(0,0)
    //   .rotation(parentActor.aimAngle)
    //   .particleColor(Duel.color(192,64,64))
    //   .lifespanSecond(2)
    //   .weight(16)
    //   .build();
    // duelSever.system.commonParticleSet.particleList.add(newParticle);
    // parentActor.group.addArrow(newArrow);
    // duelSever.system.screenShakeValue+=10;
    // parentActor.chargedFrameCount=0;
    // parentActor.state=moveState.entryState(parentActor);
  }
  @Override
  public void displayEffect(ServerPlayerActor parentActor) {
    parentActor.chargedFrameCount++;
  }
  @Override
  public boolean isDrawingLongBow() {
    return true;
  }
  @Override
  public boolean hasCompletedLongBowCharge(ServerPlayerActor parentActor) {
    return parentActor.chargedFrameCount>=chargeRequiredFrameCount;
  }
  @Override
  public boolean buttonPressed(AbstractInputDevice input) {
    return input.longShotButtonPressed;
  }
  @Override
  public boolean triggerPulled(ServerPlayerActor parentActor) {
    return !buttonPressed(parentActor.engine.inputDevice)&&hasCompletedLongBowCharge(parentActor);
  }
}
