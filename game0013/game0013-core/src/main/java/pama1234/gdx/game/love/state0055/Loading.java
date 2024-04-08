package pama1234.gdx.game.love.state0055;

import pama1234.gdx.game.app.app0002.Screen0055;
import pama1234.gdx.game.love.state0055.State0055Util.StateEntity0055;

public class Loading extends StateEntity0055{

  public Loading(Screen0055 p) {
    super(p);
  }

  @Override
  public void update() {

    //    p.state(new BulletTest3DModel(p));
    //    p.state(new Duel3D(p));
    p.state(new CharacterTestDuel(p));
    //    p.state(new CharacterTest(p));
  }
}
