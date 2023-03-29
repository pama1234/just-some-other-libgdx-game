package pama1234.gdx.game.duel.util.ai.mech;

import pama1234.gdx.game.duel.Duel;
import pama1234.gdx.game.duel.util.actor.PlayerActor;
import pama1234.gdx.game.duel.util.player.PlayerEngine;

public final class ComputerPlayerEngine extends PlayerEngine{
  private final Duel duel;
  public final int planUpdateFrameCount=10;
  public PlayerPlan currentPlan;
  public ComputerPlayerEngine(Duel duel) {
    this.duel=duel;
    // There shoud be a smarter way!!!
    final MovePlayerPlan move=new MovePlayerPlan(duel);
    final JabPlayerPlan jab=new JabPlayerPlan(duel);
    final KillPlayerPlan kill=new KillPlayerPlan(this.duel);
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
    currentPlan.execute(player,controllingInputDevice);
    if(duel.frameCount%planUpdateFrameCount==0) currentPlan=currentPlan.nextPlan(player);
  }
}