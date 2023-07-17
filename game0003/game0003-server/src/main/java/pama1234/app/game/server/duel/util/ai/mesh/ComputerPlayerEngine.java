package pama1234.app.game.server.duel.util.ai.mesh;

import pama1234.app.game.server.duel.GetRandom;
import pama1234.app.game.server.duel.util.player.PlayerEngine;
import pama1234.app.game.server.duel.util.player.ServerPlayerActor;

public final class ComputerPlayerEngine extends PlayerEngine{
  public GetRandom rng;
  public int time;
  public final int planUpdateFrameCount=10;
  public PlayerPlan currentPlan;
  public float level;
  public ComputerPlayerEngine(GetRandom rng,float level) {
    this.rng=rng;
    // There shoud be a smarter way!!!
    final MovePlayerPlan move=new MovePlayerPlan(rng,level);
    final JabPlayerPlan jab=new JabPlayerPlan(rng,level);
    final KillPlayerPlan kill=new KillPlayerPlan(rng,level);
    this.level=level;
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
  public void run(ServerPlayerActor player) {
    currentPlan.execute(player,inputDevice);
    if(time%planUpdateFrameCount==0) currentPlan=currentPlan.nextPlan(player);
    time++;
  }
  @Override
  public void setScore(int scoreType,float score) {
    //TODO nop
  }
  @Override
  public float getScore(int index) {
    return 0; //TODO nop
  }
}