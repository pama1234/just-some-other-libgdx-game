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
  public float movePlanAccuracy=0.7f,jabPlanAccuracy=0.2f;
  public DefaultPlayerPlan(GetRandom rng,float level) {
    this.rng=rng;
    this.generalLevel=level;
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
    if(player.group.enemyGroup.list.getFirst().playerCenter.list.isEmpty()) {
      return movePlan;
    }
    final AbstractPlayerActor enemy=player.group.enemyGroup.list.getFirst().playerCenter.list.getFirst();
    // Draw longbow if enemy is damaged
    // TODO fix enemy.state==null exception
    if(enemy.state!=null&&enemy.state.isDamaged()) {
      // if(rng.random(1)<0.3f) 
      return killPlan();
    }
    // Avoid the nearest arrow
    AbstractArrowActor nearestArrow=null;
    float tmpMinDistancePow2=Float.MAX_VALUE;
    for(AbstractArrowActor eachArrow:enemy.group.arrowCenter.list) {
      final float distancePow2=player.distPow2(eachArrow);
      if(distancePow2<tmpMinDistancePow2) {
        nearestArrow=eachArrow;
        tmpMinDistancePow2=distancePow2;
      }
    }
    // TODO remove magic 40000
    if(tmpMinDistancePow2<40000) {
      final float playerAngleInArrowFrame=nearestArrow.angle(player);
      float escapeAngle=nearestArrow.directionAngle;
      if(playerAngleInArrowFrame-nearestArrow.directionAngle>0) escapeAngle+=UtilMath.QUARTER_PI+rng.random(UtilMath.QUARTER_PI);
      else escapeAngle-=UtilMath.QUARTER_PI+rng.random(UtilMath.QUARTER_PI);
      final float escapeTargetX=player.pos.x+100*UtilMath.cos(escapeAngle);
      final float escapeTargetY=player.pos.y+100*UtilMath.sin(escapeAngle);
      setMoveDirection(player,escapeTargetX,escapeTargetY,0);
      if(rng.random(1)<movePlanAccuracy) return movePlan();
      else return jabPlan();
    }
    // Away from enemy
    setMoveDirection(player,enemy);
    if(player.distPow2(enemy)<100000) {
      if(rng.random(1)<movePlanAccuracy) return movePlan();
      else return jabPlan();
    }
    // If there is nothing special
    if(rng.random(1)<jabPlanAccuracy) return movePlan();
    else return jabPlan();
  }
  public PlayerPlan killPlan() {
    if(generalLevel>=1f) return killPlan;
    else {
      // return jabPlan();
      if(generalLevel<0.5f) return jabPlan();
      else return rng.random(1)<(generalLevel-0.5f)*2?killPlan:jabPlan();
    }
  }
  public PlayerPlan jabPlan() {
    // System.out.println("DefaultPlayerPlan.jabPlan() "+level);
    if(generalLevel>=0.5f) return jabPlan;
    else {
      if(generalLevel<0.25f) return movePlan();
      else return rng.random(1)<(generalLevel-0.25f)*4?jabPlan:movePlan();
    }
  }
  public PlayerPlan movePlan() {
    return movePlan;
  }
  public void setMoveDirection(ServerPlayerActor player,AbstractPlayerActor enemy) {
    float targetX,targetY;
    if(enemy.pos.x>Const.CANVAS_SIZE*0.5f) targetX=rng.random(0,Const.CANVAS_SIZE*0.5f);
    else targetX=rng.random(Const.CANVAS_SIZE*0.5f,Const.CANVAS_SIZE);
    if(enemy.pos.y>Const.CANVAS_SIZE*0.5f) targetY=rng.random(0,Const.CANVAS_SIZE*0.5f);
    else targetY=rng.random(Const.CANVAS_SIZE*0.5f,Const.CANVAS_SIZE);
    setMoveDirection(player,targetX,targetY,100);
  }
  public void setMoveDirection(ServerPlayerActor player,float targetX,float targetY,float allowance) {
    if(targetX>player.pos.x+allowance) horizontalMove=1;
    else if(targetX<player.pos.x-allowance) horizontalMove=-1;
    else horizontalMove=0;
    if(targetY>player.pos.y+allowance) verticalMove=1;
    else if(targetY<player.pos.y-allowance) verticalMove=-1;
    else verticalMove=0;
  }
}