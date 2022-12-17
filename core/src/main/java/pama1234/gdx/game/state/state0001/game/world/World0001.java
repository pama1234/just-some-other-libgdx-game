package pama1234.gdx.game.state.state0001.game.world;

import pama1234.gdx.game.app.Screen0011;
import pama1234.gdx.game.state.state0001.Game;
import pama1234.gdx.game.state.state0001.game.MainPlayer2D;
import pama1234.gdx.game.state.state0001.game.Player2D;
import pama1234.gdx.game.state.state0001.game.World;
import pama1234.gdx.util.wrapper.PointCenter;
import pama1234.math.physics.MassPoint;

public class World0001 extends World<Screen0011,Game>{
  public PointCenter<Screen0011,MassPoint,Player2D> players;
  public MainPlayer2D yourself;
  public World0001(Screen0011 p,Game pg) {
    super(p,pg,1);
    list[0]=players=new PointCenter<Screen0011,MassPoint,Player2D>(p);
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