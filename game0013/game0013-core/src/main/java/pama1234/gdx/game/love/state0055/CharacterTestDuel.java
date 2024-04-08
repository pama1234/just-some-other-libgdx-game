package pama1234.gdx.game.love.state0055;

import pama1234.gdx.game.app.app0002.Screen0055;
import pama1234.gdx.game.element.actor.PlayerActor;
import pama1234.gdx.game.love.state0055.State0055Util.StateEntity0055;
import space.earlygrey.shapedrawer.CapType;

public class CharacterTestDuel extends StateEntity0055{

  public CharacterTestDuel(Screen0055 p) {
    super(p);
  }

  @Override
  public void from(StateEntity0055 in) {
    //    p.fill(0);
    p.capType=CapType.ROUND;

    p.centerNeoAddAll(new PlayerActor(p));

    p.centerCam.list.remove(p);

    p.depth(true);
  }

  @Override
  public void displayCam() {}

}
