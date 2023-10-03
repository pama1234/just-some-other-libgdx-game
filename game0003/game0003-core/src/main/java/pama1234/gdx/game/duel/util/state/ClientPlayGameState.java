package pama1234.gdx.game.duel.util.state;

import pama1234.app.game.server.duel.ServerGameSystem;
import pama1234.app.game.server.duel.util.Const;
import pama1234.app.game.server.duel.util.actor.AbstractPlayerActor;
import pama1234.app.game.server.duel.util.actor.Actor;
import pama1234.app.game.server.duel.util.actor.ActorGroup;
import pama1234.app.game.server.duel.util.arrow.AbstractArrowActor;
import pama1234.gdx.game.duel.ClientGameSystem;
import pama1234.gdx.game.duel.Duel;
import pama1234.gdx.game.duel.TextUtil;
import pama1234.gdx.game.duel.util.actor.ClientPlayerActor;
import pama1234.math.UtilMath;

public final class ClientPlayGameState extends ClientGameSystemState{
  public ClientPlayGameState(Duel duel,ClientGameSystem system) {
    super(duel,system);
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
    system.commonParticleSet.update();
  }
  @Override
  public void displaySystem() {
    p.beginBlend();
    for(var group:system.groupCenter.list) group.displayPlayer();
    for(var group:system.groupCenter.list) group.displayArrows();
    system.commonParticleSet.display();
    p.endBlend();
  }
  @Override
  public void displayMessage() {
    if(properFrameCount>=messageDurationFrameCount) return;
    p.setTextColor(p.theme().text,(int)(255*(1-(float)(properFrameCount)/messageDurationFrameCount)));
    p.setTextScale(p.pus);
    p.textColor(p.theme().text);
    p.fullText(TextUtil.used.go.text,(p.width-TextUtil.used.go.width*p.pus)/2f,(p.height-p.pu)/2f);
    p.setTextScale(1);
  }
  @Override
  public void checkStateTransition() {
    if(system.myGroup().playerCenter.list.isEmpty()) system.currentState(new ClientGameResultState(p,system,system.otherGroup().id,TextUtil.used.lose));
    else if(system.otherGroup().playerCenter.list.isEmpty()) system.currentState(new ClientGameResultState(p,system,system.myGroup().id,TextUtil.used.win));
  }
  public void checkCollision() {
    //TODO
    final ActorGroup myGroup=p.core().myGroup();
    final ActorGroup otherGroup=p.core().otherGroup();
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
        else thrustPlayerActor(eachMyArrow,(ClientPlayerActor)enemyPlayer);
        breakArrow(eachMyArrow,myGroup);
      }
    }
    if(!myGroup.playerCenter.list.isEmpty()) {
      for(AbstractArrowActor eachEnemyArrow:otherGroup.arrowCenter.list) {
        if(!eachEnemyArrow.isCollided(myGroup.playerCenter.list.getFirst())) continue;
        if(eachEnemyArrow.isLethal()) killPlayer(myGroup.playerCenter.list.getFirst());
        else thrustPlayerActor(eachEnemyArrow,(ClientPlayerActor)myGroup.playerCenter.list.getFirst());
        breakArrow(eachEnemyArrow,otherGroup);
      }
    }
  }
  public void killPlayer(AbstractPlayerActor player) {
    p.core().addSquareParticles(player.pos.x,player.pos.y,50,16,2,10,4);
    player.group.removePlayer(player);
    p.core().screenShakeValue=50;
  }
  public void breakArrow(AbstractArrowActor arrow,ActorGroup group) {
    p.core().addSquareParticles(arrow.pos.x,arrow.pos.y,10,7,1,5,1);
    group.arrowCenter.remove.add(arrow);
  }
  public void thrustPlayerActor(Actor referenceActor,ClientPlayerActor targetPlayerActor) {
    final float relativeAngle=UtilMath.atan2(targetPlayerActor.pos.y-referenceActor.pos.y,targetPlayerActor.pos.x-referenceActor.pos.x);
    final float thrustAngle=relativeAngle+p.random(-0.5f*UtilMath.HALF_PI,0.5f*UtilMath.HALF_PI);
    targetPlayerActor.vel.x+=20*UtilMath.cos(thrustAngle);
    targetPlayerActor.vel.y+=20*UtilMath.sin(thrustAngle);
    targetPlayerActor.state=p.core().damagedState.entryState(targetPlayerActor);
    targetPlayerActor.group.damageCount++;//TODO shit
    p.core().screenShakeValue+=10;
  }
  @Override
  public float getScore(int group) {
    if(group==0) return -system.myGroup().damageCount;
    else return -system.otherGroup().damageCount;
  }
}