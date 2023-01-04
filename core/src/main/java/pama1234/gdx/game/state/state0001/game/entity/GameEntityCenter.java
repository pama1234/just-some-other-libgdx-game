package pama1234.gdx.game.state.state0001.game.entity;

import pama1234.gdx.game.app.Screen0011;
import pama1234.gdx.game.state.state0001.game.entity.entity0001.DroppedItem.DroppedItemCenter;
import pama1234.gdx.game.state.state0001.game.player.Player.PlayerCenter;
import pama1234.gdx.util.wrapper.EntityCenter;
import pama1234.gdx.util.wrapper.MultiEntityCenter;

public class GameEntityCenter extends MultiEntityCenter<Screen0011,EntityCenter<Screen0011,? extends GamePointEntity<?>>>{
  public GamePointEntityCenter pointEntities;
  public DroppedItemCenter items;
  public PlayerCenter players;
  public GameEntityCenter(Screen0011 p) {
    super(p);
    list.add(pointEntities=new GamePointEntityCenter(p));
    list.add(items=new DroppedItemCenter(p));
  }
  @Override
  public void display() {
    super.display();
    p.noTint();
  }
  public static class GamePointEntityCenter extends EntityCenter<Screen0011,GamePointEntity<?>>{
    public GamePointEntityCenter(Screen0011 p) {
      super(p);
    }
    public GamePointEntityCenter(Screen0011 p,GamePointEntity<?> in) {
      super(p,in);
    }
    public GamePointEntityCenter(Screen0011 p,GamePointEntity<?>[] in) {
      super(p,in);
    }
  }
}