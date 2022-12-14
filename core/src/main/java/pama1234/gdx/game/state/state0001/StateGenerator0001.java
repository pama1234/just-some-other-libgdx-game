package pama1234.gdx.game.state.state0001;

import pama1234.gdx.game.app.Screen0011;
import pama1234.gdx.game.state.DisplayEntity;
import pama1234.gdx.game.state.DisplayEntity.DisplayWithCam;
import pama1234.gdx.util.entity.Entity;
import pama1234.gdx.util.listener.EntityListener;

public class StateGenerator0001{
  public static void loadState0001(Screen0011 in) {
    put(State0001.FirstRun,new FirstRun(in));
    put(State0001.Loading,new Loading(in));
    put(State0001.StartMenu,new StartMenu(in));
    put(State0001.Game,new Game(in));
  }
  public static <T extends StateEntityListener0001> void put(State0001 data,T in) {
    data.entity=in;
    data.displayCam=new DisplayEntity(in::displayCam);
  }
  public static abstract class StateEntity0001 extends Entity<Screen0011> implements StateEntityListener0001{
    public StateEntity0001(Screen0011 p) {
      super(p);
    }
  }
  public interface StateEntityListener0001 extends EntityListener,DisplayWithCam{
    default public void from(State0001 in) {}
    default public void to(State0001 in) {}
  }
}
