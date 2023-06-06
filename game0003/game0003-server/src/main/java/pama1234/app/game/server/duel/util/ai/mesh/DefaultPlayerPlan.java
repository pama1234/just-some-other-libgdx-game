package pama1234.app.game.server.duel.util.ai.mesh;

import pama1234.app.game.server.duel.GetRandom;
import pama1234.app.game.server.duel.util.Const;
import pama1234.app.game.server.duel.util.actor.AbstractPlayerActor;
import pama1234.app.game.server.duel.util.arrow.AbstractArrowActor;
import pama1234.app.game.server.duel.util.input.AbstractInputDevice;
import pama1234.app.game.server.duel.util.player.ServerPlayerActor;
import pama1234.math.UtilMath;

public abstract class DefaultPlayerPlan extends PlayerPlan{
  public GetRandom rng;
  public DefaultPlayerPlan(GetRandom rng) {
    this.rng=rng;
  }
  public PlayerPlan movePlan,jabPlan,escapePlan,killPlan;
  public int horizontalMove,verticalMove;
  public boolean shoot;
  @Override
  public void execute(ServerPlayerActor player,AbstractInputDevice input) {
    input.operateMoveButton(horizontalMove,verticalMove);
    input.operateLongShotButton(false);
  }
  @Override
  public PlayerPlan nextPlan(ServerPlayerActor player) {
    final AbstractPlayerActor enemy=player.group.enemyGroup.player;
    // Draw longbow if enemy is damaged
    if(enemy.state.isDamaged()) {
      if(rng.random(1)<0.3f) return killPlan;
    }
    // Avoid the nearest arrow
    AbstractArrowActor nearestArrow=null;
    float tmpMinDistancePow2=Float.MAX_VALUE;
    for(AbstractArrowActor eachArrow:enemy.group.arrowList) {
      final float distancePow2=player.getDistancePow2(eachArrow);
      if(distancePow2<tmpMinDistancePow2) {
        nearestArrow=eachArrow;
        tmpMinDistancePow2=distancePow2;
      }
    }
    if(tmpMinDistancePow2<40000) {
      final float playerAngleInArrowFrame=nearestArrow.getAngle(player);
      float escapeAngle=nearestArrow.directionAngle;
      if(playerAngleInArrowFrame-nearestArrow.directionAngle>0) escapeAngle+=UtilMath.QUARTER_PI+rng.random(UtilMath.QUARTER_PI);
      else escapeAngle-=UtilMath.QUARTER_PI+rng.random(UtilMath.QUARTER_PI);
      final float escapeTargetX=player.xPosition+100*UtilMath.cos(escapeAngle);
      final float escapeTargetY=player.yPosition+100*UtilMath.sin(escapeAngle);
      setMoveDirection(player,escapeTargetX,escapeTargetY,0);
      if(rng.random(1)<0.7f) return movePlan;
      else return jabPlan;
    }
    // Away from enemy
    setMoveDirection(player,enemy);
    if(player.getDistancePow2(enemy)<100000) {
      if(rng.random(1)<0.7f) return movePlan;
      else return jabPlan;
    }
    // If there is nothing special
    if(rng.random(1)<0.2f) return movePlan;
    else return jabPlan;
  }
  public void setMoveDirection(ServerPlayerActor player,AbstractPlayerActor enemy) {
    float targetX,targetY;
    if(enemy.xPosition>Const.CANVAS_SIZE*0.5f) targetX=rng.random(0,Const.CANVAS_SIZE*0.5f);
    else targetX=rng.random(Const.CANVAS_SIZE*0.5f,Const.CANVAS_SIZE);
    if(enemy.yPosition>Const.CANVAS_SIZE*0.5f) targetY=rng.random(0,Const.CANVAS_SIZE*0.5f);
    else targetY=rng.random(Const.CANVAS_SIZE*0.5f,Const.CANVAS_SIZE);
    setMoveDirection(player,targetX,targetY,100);
  }
  public void setMoveDirection(ServerPlayerActor player,float targetX,float targetY,float allowance) {
    if(targetX>player.xPosition+allowance) horizontalMove=1;
    else if(targetX<player.xPosition-allowance) horizontalMove=-1;
    else horizontalMove=0;
    if(targetY>player.yPosition+allowance) verticalMove=1;
    else if(targetY<player.yPosition-allowance) verticalMove=-1;
    else verticalMove=0;
  }
}