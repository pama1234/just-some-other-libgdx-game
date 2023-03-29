package pama1234.gdx.game.duel.util.ai.nnet;

import pama1234.gdx.game.duel.util.actor.PlayerActor;
import pama1234.gdx.game.duel.util.player.PlayerEngine;
import pama1234.math.UtilMath;

public class ComputerLifeEngine extends PlayerEngine{
  public NetworkGroup networks;
  public int dfire;
  public ComputerLifeEngine(NetworkGroup networks) {
    this.networks=networks;
  }
  @Override
  public void run(PlayerActor player) {
    networks.execute();
    float rad=networks.output.get(0)*UtilMath.PI;
    float mag=UtilMath.constrain(networks.output.get(1),-1,1);
    float dx=UtilMath.sin(rad);
    float dy=UtilMath.cos(rad);
    inputDevice.operateMove(dx*mag,dy*mag);
    //---
    int fire=fireType(networks.output.get(2));
    if(fire!=dfire) {
      if(dfire==1) inputDevice.operateShotButton(false);
      else if(dfire==2) inputDevice.operateLongShotButton(false);
      //---
      if(fire==1) inputDevice.operateLongShotButton(true);
      else if(fire==2) inputDevice.operateLongShotButton(true);
      dfire=fire;
    }
    //---
    // networks.memory.set(0,networks.output.get(3));
  }
  public int fireType(float data) {
    return (int)(data*3);
  }
}