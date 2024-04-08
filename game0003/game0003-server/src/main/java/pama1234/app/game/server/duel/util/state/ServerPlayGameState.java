package pama1234.app.game.server.duel.util.state;

import pama1234.app.game.server.duel.ServerGameSystem;
import pama1234.app.game.server.duel.util.Const;
import pama1234.app.game.server.duel.util.actor.AbstractPlayerActor;
import pama1234.app.game.server.duel.util.actor.Actor;
import pama1234.app.game.server.duel.util.actor.ActorGroup;
import pama1234.app.game.server.duel.util.arrow.AbstractArrowActor;
import pama1234.app.game.server.duel.util.player.ServerPlayerActor;
import pama1234.math.UtilMath;

public class ServerPlayGameState extends ServerGameSystemState{
  public ServerPlayGameState(ServerGameSystem system) {
    super(system);
    system.stateIndex=ServerGameSystem.play;
  }
  public int messageDurationFrameCount=UtilMath.floor(Const.IDEAL_FRAME_RATE);
  @Override
  public void updateSystem() {
    for(var group:system.groupCenter.list) {
      group.update();
      group.act();
    }

    checkCollision();
  }
  @Override
  public void checkStateTransition() {
    if(system.myGroup().playerCenter.list.isEmpty()) system.currentState(
      new ServerGameResultState(system,system.otherGroup().id));
    else if(system.otherGroup().playerCenter.list.isEmpty()) system.currentState(
      new ServerGameResultState(system,system.myGroup().id));
  }
  public void checkCollision() {
    //TODO
    final ActorGroup myGroup=system.duelServer.core.myGroup();
    final ActorGroup otherGroup=system.duelServer.core.otherGroup();
    for(AbstractArrowActor eachMyArrow:myGroup.arrowCenter.list) {
      for(AbstractArrowActor eachEnemyArrow:otherGroup.arrowCenter.list) {
        if(!eachMyArrow.isCollided(eachEnemyArrow)) continue;
        breakArrow(eachMyArrow,myGroup);
        breakArrow(eachEnemyArrow,otherGroup);
      }
    }
    if(!otherGroup.playerCenter.list.isEmpty()) {
      for(AbstractArrowActor eachMyArrow:myGroup.arrowCenter.list) {
        AbstractPlayerActor enemyPlayer=otherGroup.playerCenter.list.getFirst();
        if(!eachMyArrow.isCollided(enemyPlayer)) continue;
        if(eachMyArrow.isLethal()) killPlayer(otherGroup.playerCenter.list.getFirst());
        else thrustPlayerActor(eachMyArrow,(ServerPlayerActor)enemyPlayer);
        breakArrow(eachMyArrow,myGroup);
      }
    }
    if(!myGroup.playerCenter.list.isEmpty()) {
      for(AbstractArrowActor eachEnemyArrow:otherGroup.arrowCenter.list) {
        if(!eachEnemyArrow.isCollided(myGroup.playerCenter.list.getFirst())) continue;
        if(eachEnemyArrow.isLethal()) killPlayer(myGroup.playerCenter.list.getFirst());
        else thrustPlayerActor(eachEnemyArrow,(ServerPlayerActor)myGroup.playerCenter.list.getFirst());
        breakArrow(eachEnemyArrow,otherGroup);
      }
    }
  }
  public void killPlayer(AbstractPlayerActor player) {
    system.duelServer.core.addSquareParticles(player.pos.x,player.pos.y,50,16,2,10,4);
    player.group.removePlayer(player);
  }
  public void breakArrow(AbstractArrowActor arrow,ActorGroup group) {
    system.duelServer.core.addSquareParticles(arrow.pos.x,arrow.pos.y,10,7,1,5,1);
    group.arrowCenter.remove.add(arrow);
  }
  public void thrustPlayerActor(Actor referenceActor,ServerPlayerActor targetPlayerActor) {
    final float relativeAngle=UtilMath.atan2(targetPlayerActor.pos.y-referenceActor.pos.y,targetPlayerActor.pos.x-referenceActor.pos.x);
    final float thrustAngle=relativeAngle+system.duelServer.random(-0.5f*UtilMath.HALF_PI,0.5f*UtilMath.HALF_PI);
    targetPlayerActor.vel.x+=20*UtilMath.cos(thrustAngle);
    targetPlayerActor.vel.y+=20*UtilMath.sin(thrustAngle);
    targetPlayerActor.state=system.duelServer.core.damagedState.entryState(targetPlayerActor);
    targetPlayerActor.group.damageCount++;//TODO shit
  }
  @Override
  public float getScore(int group) {
    if(group==0) return -system.myGroup().damageCount;
    else return -system.otherGroup().damageCount;
  }
}
