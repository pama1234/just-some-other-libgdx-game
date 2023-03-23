package pama1234.gdx.game.state.state0002.game;

import pama1234.gdx.game.app.app0004.Screen0012;
import pama1234.gdx.util.entity.PointEntity;
import pama1234.math.physics.MassPoint;

public class Player extends PointEntity<Screen0012,MassPoint>{
  public Player(Screen0012 p,int x,int y) {
    super(p,new MassPoint(x,y));
  }
}
