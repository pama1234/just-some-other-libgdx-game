package pama1234.gdx.game.duel.util.ai.mech;

import pama1234.gdx.game.duel.GetRandom;
import pama1234.gdx.game.duel.util.actor.PlayerActor;
import pama1234.gdx.game.duel.util.player.PlayerEngine;

public final class ComputerPlayerEngine extends PlayerEngine{
  public GetRandom rng;
  public int time;
  public final int planUpdateFrameCount=10;
  public PlayerPlan currentPlan;
  public ComputerPlayerEngine(GetRandom rng) {
    this.rng=rng;
    // There shoud be a smarter way!!!
    final MovePlayerPlan move=new MovePlayerPlan(rng);
    final JabPlayerPlan jab=new JabPlayerPlan(rng);
    final KillPlayerPlan kill=new KillPlayerPlan(rng);
    move.movePlan=move;
    move.jabPlan=jab;
    move.killPlan=kill;
    jab.movePlan=move;
    jab.jabPlan=jab;
    jab.killPlan=kill;
    kill.movePlan=move;
    currentPlan=move;
  }
  @Override
  public void run(PlayerActor player) {
    currentPlan.execute(player,inputDevice);
    if(time%planUpdateFrameCount==0) currentPlan=currentPlan.nextPlan(player);
    time++;
  }
  @Override
  public void setScore(float score) {
    //TODO nop
  }
}