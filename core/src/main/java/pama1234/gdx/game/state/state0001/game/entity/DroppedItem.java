package pama1234.gdx.game.state.state0001.game.entity;

import pama1234.gdx.game.app.Screen0011;
import pama1234.gdx.game.state.state0001.game.Game;
import pama1234.gdx.game.state.state0001.game.item.Item;
import pama1234.math.physics.MassPoint;

public class DroppedItem extends GamePointEntity<MassPoint>{
  public Item data;
  public DroppedItem(Screen0011 p,MassPoint in,Game pg) {
    super(p,in,pg);
  }
}
