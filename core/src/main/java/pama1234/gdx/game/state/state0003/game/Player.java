package pama1234.gdx.game.state.state0003.game;

import pama1234.gdx.game.app.Screen0016;
import pama1234.gdx.util.entity.PointEntity;
import pama1234.math.physics.MassPoint;

public class Player extends PointEntity<Screen0016,MassPoint>{
  public Player(Screen0016 p,int x,int y) {
    super(p,new MassPoint(x,y));
  }
}
