package pama1234.gdx.game.duel.util.state;

import pama1234.gdx.game.duel.Duel;
import pama1234.gdx.game.duel.GameSystem;
import pama1234.gdx.game.duel.TextUtil;
import pama1234.gdx.game.duel.util.actor.AbstractPlayerActor;
import pama1234.gdx.game.duel.util.actor.Actor;
import pama1234.gdx.game.duel.util.actor.ActorGroup;
import pama1234.gdx.game.duel.util.actor.NullPlayerActor;
import pama1234.gdx.game.duel.util.actor.PlayerActor;
import pama1234.gdx.game.duel.util.arrow.AbstractArrowActor;
import pama1234.math.UtilMath;

public final class PlayGameState extends GameSystemState{
  public PlayGameState(Duel duel,GameSystem system) {
    super(duel,system);
    system.stateIndex=GameSystem.play;
  }
  public int messageDurationFrameCount=UtilMath.floor(Duel.IDEAL_FRAME_RATE);
  @Override
  public void updateSystem() {
    system.myGroup.update();
    system.myGroup.act();
    system.otherGroup.update();
    system.otherGroup.act();
    //---
    checkCollision();
    system.commonParticleSet.update();
  }
  @Override
  public void displaySystem() {
    duel.beginBlend();
    system.myGroup.displayPlayer();
    system.otherGroup.displayPlayer();
    system.myGroup.displayArrows();
    system.otherGroup.displayArrows();
    system.commonParticleSet.display();
    duel.endBlend();
  }
  @Override
  public void displayMessage() {
    if(properFrameCount>=messageDurationFrameCount) return;
    duel.setTextColor(0,(int)(255*(1-(float)(properFrameCount)/messageDurationFrameCount)));
    duel.setTextScale(duel.pus);
    duel.drawText(TextUtil.used.go.text,(duel.width-TextUtil.used.go.width*duel.pus)/2f,(duel.height-duel.pu)/2f);
    duel.setTextScale(1);
  }
  @Override
  public void checkStateTransition() {
    if(system.myGroup.player.isNull()) system.currentState(new GameResultState(duel,system,system.myGroup.id,TextUtil.used.lose));
    else if(system.otherGroup.player.isNull()) system.currentState(new GameResultState(duel,system,system.otherGroup.id,TextUtil.used.win));
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
    duel.system.addSquareParticles(player.xPosition,player.yPosition,50,16,2,10,4);
    // player.group.player=new NullPlayerActor(player.xPosition,player.yPosition,player.group);//TODO
    player.group.setPlayer(new NullPlayerActor(player.engine));
    duel.system.screenShakeValue=50;
  }
  public void breakArrow(AbstractArrowActor arrow,ActorGroup group) {
    duel.system.addSquareParticles(arrow.xPosition,arrow.yPosition,10,7,1,5,1);
    group.removingArrowList.add(arrow);
  }
  public void thrustPlayerActor(Actor referenceActor,PlayerActor targetPlayerActor) {
    final float relativeAngle=UtilMath.atan2(targetPlayerActor.yPosition-referenceActor.yPosition,targetPlayerActor.xPosition-referenceActor.xPosition);
    final float thrustAngle=relativeAngle+duel.random(-0.5f*UtilMath.HALF_PI,0.5f*UtilMath.HALF_PI);
    targetPlayerActor.xVelocity+=20*UtilMath.cos(thrustAngle);
    targetPlayerActor.yVelocity+=20*UtilMath.sin(thrustAngle);
    targetPlayerActor.state=duel.system.damagedState.entryState(targetPlayerActor);
    targetPlayerActor.group.damageCount++;//TODO shit
    duel.system.screenShakeValue+=10;
  }
  @Override
  public float getScore(int group) {
    if(group==0) return -system.myGroup.damageCount;
    else return -system.otherGroup.damageCount;
  }
}