package pama1234.gdx.game.duel.util.ai.mech;

import pama1234.gdx.game.duel.Duel;
import pama1234.gdx.game.duel.util.actor.PlayerActor;
import pama1234.gdx.game.duel.util.input.AbstractInputDevice;

public final class MovePlayerPlan extends DefaultPlayerPlan{
  public MovePlayerPlan(Duel duel) {
    super(duel);
  }
  @Override
  public void execute(PlayerActor player,AbstractInputDevice input) {
    super.execute(player,input);
    input.operateShotButton(false);
  }
}