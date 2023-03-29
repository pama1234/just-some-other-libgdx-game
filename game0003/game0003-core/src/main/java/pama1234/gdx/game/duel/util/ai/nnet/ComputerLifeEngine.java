package pama1234.gdx.game.duel.util.ai.nnet;

import pama1234.gdx.game.duel.util.actor.PlayerActor;
import pama1234.gdx.game.duel.util.player.PlayerEngine;
import pama1234.math.UtilMath;

public class ComputerLifeEngine extends PlayerEngine{
  public NetworkGroup networks;
  public ComputerLifeEngine(NetworkGroup networks) {
    this.networks=networks;
  }
  @Override
  public void run(PlayerActor player) {
    networks.execute();
    float rad=networks.output.data(0)*UtilMath.PI;
    float mag=UtilMath.constrain(networks.output.data(1),-1,1);
    float dx=UtilMath.sin(rad);
    float dy=UtilMath.cos(rad);
    inputDevice.operateMove(dx*mag,dy*mag);
  }
}