package pama1234.gdx.game.state.state0001.game.world;

import pama1234.gdx.game.app.Screen0011;
import pama1234.gdx.game.state.state0001.game.Game;
import pama1234.gdx.game.state.state0001.game.player.MainPlayer2D;
import pama1234.gdx.game.state.state0001.game.player.Player2D.PlayerCenter2D;

public class World0001 extends World<Screen0011,Game>{
  public PlayerCenter2D players;
  public MainPlayer2D yourself;
  public World0001(Screen0011 p,Game pg) {
    super(p,pg,1);
    list[0]=players=new PlayerCenter2D(p);
    yourself=new MainPlayer2D(p,0,0,pg);
  }
  @Override
  public void init() {
    yourself.init();
    p.cam2d.activeDrag=false;
    p.centerCam.add.add(yourself);
  }
  @Override
  public void dispose() {
    p.cam2d.activeDrag=true;
    p.centerCam.remove.add(yourself);
  }
}