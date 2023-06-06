package pama1234.gdx.game.state.state0003;

import pama1234.gdx.game.app.app0004.Screen0016;
import pama1234.gdx.game.util.DisplayEntity;
import pama1234.gdx.game.util.DisplayEntity.DisplayWithCam;
import pama1234.gdx.util.entity.Entity;
import pama1234.gdx.util.listener.EntityListener;

public class StateGenerator0003{
  public static void loadState0001(Screen0016 in) {
    put(State0003.FirstRun,new FirstRun(in));
    put(State0003.Loading,new Loading(in));
    put(State0003.StartMenu,new StartMenu(in));
    put(State0003.Game,new Game(in));
    put(State0003.Settings,new Settings(in));
    put(State0003.Announcement,new Announcement(in));
    put(State0003.Exception,new ExceptionState(in));
  }
  public static <T extends StateEntityListener0002> void put(State0003 data,T in) {
    data.entity=in;
    data.displayCam=new DisplayEntity(in::displayCam);
  }
  public static abstract class StateEntity0003 extends Entity<Screen0016> implements StateEntityListener0002{
    public StateEntity0003(Screen0016 p) {
      super(p);
    }
  }
  public interface StateEntityListener0002 extends EntityListener,DisplayWithCam{
    default public void from(State0003 in) {}
    default public void to(State0003 in) {}
    // default public void exit() {}
  }
}
