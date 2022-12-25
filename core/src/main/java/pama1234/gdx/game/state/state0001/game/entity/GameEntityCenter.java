package pama1234.gdx.game.state.state0001.game.entity;

import pama1234.gdx.game.app.Screen0011;
import pama1234.gdx.util.wrapper.EntityCenter;
import pama1234.gdx.util.wrapper.MultiEntityCenter;

// public class GameEntityCenter extends EntityCenter<Screen0011,GamePointEntity<?>>{
public class GameEntityCenter extends MultiEntityCenter<Screen0011,EntityCenter<Screen0011,GamePointEntity<?>>>{
  public GamePointEntityCenter points;
  public GameEntityCenter(Screen0011 p) {
    super(p);
    list.add(points=new GamePointEntityCenter(p));
  }
  // public GameEntityCenter(Screen0011 p) {
  // super(p,2);
  // list[0]=points=new GamePointEntityCenter(p);
  // list[0]=points=new GamePointEntityCenter(p);
  // }
  // public GameEntityCenter(Screen0011 p) {
  //   super(p);
  // }
  // public GameEntityCenter(Screen0011 p,GamePointEntity<?> in) {
  //   super(p,in);
  // }
  // public GameEntityCenter(Screen0011 p,GamePointEntity<?>[] in) {
  //   super(p,in);
  // }
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