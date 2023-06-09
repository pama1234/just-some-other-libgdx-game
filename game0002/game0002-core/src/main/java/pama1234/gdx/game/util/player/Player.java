package pama1234.gdx.game.util.player;

import pama1234.gdx.game.app.app0002.Screen0005;
import pama1234.gdx.game.util.input.InputData;
import pama1234.gdx.util.entity.PointEntity;
import pama1234.math.physics.MassPoint;

public class Player extends PointEntity<Screen0005,MassPoint>{
  public InputData input;
  public Player(Screen0005 p,MassPoint in) {
    super(p,in);
  }
}
