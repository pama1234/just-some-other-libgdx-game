package hhs.game.doomlibgdx;

import com.badlogic.gdx.Game;
import squidpony.squidgrid.mapping.FlowingCaveGenerator;

public class MainGame extends Game{

  @Override
  public void create() {
    setScreen(new StartGame(new FlowingCaveGenerator(50,50).generate()));
  }
}
