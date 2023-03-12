package pama1234.gdx.game.duel.util.state;

import pama1234.gdx.game.duel.Duel;
import pama1234.gdx.game.duel.GameSystem;
import pama1234.gdx.game.duel.util.actor.AbstractPlayerActor;
import pama1234.gdx.game.duel.util.actor.Actor;
import pama1234.gdx.game.duel.util.actor.ActorGroup;
import pama1234.gdx.game.duel.util.actor.NullPlayerActor;
import pama1234.gdx.game.duel.util.actor.PlayerActor;
import pama1234.gdx.game.duel.util.arrow.AbstractArrowActor;
import pama1234.math.UtilMath;

public final class PlayGameState extends GameSystemState{
  public PlayGameState(Duel duel) {
    super(duel);
  }
  public int messageDurationFrameCount=UtilMath.floor(Duel.IDEAL_FRAME_RATE);
  @Override
  public void updateSystem(GameSystem system) {
    system.myGroup.update();
    system.myGroup.act();
    system.otherGroup.update();
    system.otherGroup.act();
    //---
    checkCollision();
    system.commonParticleSet.update();
  }
  @Override
  public void displaySystem(GameSystem system) {
    duel.beginBlend();
    system.myGroup.displayPlayer();
    system.otherGroup.displayPlayer();
    system.myGroup.displayArrows();
    system.otherGroup.displayArrows();
    system.commonParticleSet.display();
    duel.endBlend();
  }
  @Override
  public void displayMessage(GameSystem system) {
    if(properFrameCount>=messageDurationFrameCount) return;
    // duel.doFill();
    duel.setTextColor(0,(int)(255*(1-(float)(properFrameCount)/messageDurationFrameCount)));
    duel.setTextScale(duel.pus);
    // duel.drawText("Go",-32,-32);
    String in="Go";
    duel.drawText(in,(duel.width-duel.textWidth(in))/2f,(duel.height-duel.pu)/2f);
    duel.setTextScale(1);
  }
  @Override
  public void checkStateTransition(GameSystem system) {
    // if(system.myGroup.player.isNull()) system.currentState=new GameResultState(this.duel,"You lose.");
    // else if(system.otherGroup.player.isNull()) system.currentState=new GameResultState(this.duel,"You win.");
    if(system.myGroup.player.isNull()) system.currentState=new GameResultState(this.duel,"你输了");
    else if(system.otherGroup.player.isNull()) system.currentState=new GameResultState(this.duel,"你赢了");
  }
  public void checkCollision() {
    final ActorGroup myGroup=duel.system.myGroup;
    final ActorGroup otherGroup=duel.system.otherGroup;
    for(AbstractArrowActor eachMyArrow:myGroup.arrowList) {
      for(AbstractArrowActor eachEnemyArrow:otherGroup.arrowList) {
        if(!eachMyArrow.isCollided(eachEnemyArrow)) continue;
        breakArrow(eachMyArrow,myGroup);
        breakArrow(eachEnemyArrow,otherGroup);
      }
    }
    if(!otherGroup.player.isNull()) {
      for(AbstractArrowActor eachMyArrow:myGroup.arrowList) {
        AbstractPlayerActor enemyPlayer=otherGroup.player;
        if(!eachMyArrow.isCollided(enemyPlayer)) continue;
        if(eachMyArrow.isLethal()) killPlayer(otherGroup.player);
        else thrustPlayerActor(eachMyArrow,(PlayerActor)enemyPlayer);
        breakArrow(eachMyArrow,myGroup);
      }
    }
    if(!myGroup.player.isNull()) {
      for(AbstractArrowActor eachEnemyArrow:otherGroup.arrowList) {
        if(!eachEnemyArrow.isCollided(myGroup.player)) continue;
        if(eachEnemyArrow.isLethal()) killPlayer(myGroup.player);
        else thrustPlayerActor(eachEnemyArrow,(PlayerActor)myGroup.player);
        breakArrow(eachEnemyArrow,otherGroup);
      }
    }
  }
  public void killPlayer(AbstractPlayerActor player) {
    duel.system.addSquareParticles(player.xPosition,player.yPosition,50,16.0f,2.0f,10.0f,4.0f);
    player.group.player=new NullPlayerActor();
    duel.system.screenShakeValue=50.0f;
  }
  public void breakArrow(AbstractArrowActor arrow,ActorGroup group) {
    duel.system.addSquareParticles(arrow.xPosition,arrow.yPosition,10,7.0f,1.0f,5.0f,1.0f);
    group.removingArrowList.add(arrow);
  }
  public void thrustPlayerActor(Actor referenceActor,PlayerActor targetPlayerActor) {
    final float relativeAngle=UtilMath.atan2(targetPlayerActor.yPosition-referenceActor.yPosition,targetPlayerActor.xPosition-referenceActor.xPosition);
    final float thrustAngle=relativeAngle+duel.random(-0.5f*UtilMath.HALF_PI,0.5f*UtilMath.HALF_PI);
    targetPlayerActor.xVelocity+=20.0f*UtilMath.cos(thrustAngle);
    targetPlayerActor.yVelocity+=20.0f*UtilMath.sin(thrustAngle);
    targetPlayerActor.state=duel.system.damagedState.entryState(targetPlayerActor);
    duel.system.screenShakeValue+=10.0f;
  }
}