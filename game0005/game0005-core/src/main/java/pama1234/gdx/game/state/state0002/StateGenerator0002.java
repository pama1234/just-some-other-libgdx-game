package pama1234.gdx.game.state.state0002;

import pama1234.gdx.game.app.app0004.Screen0012;
import pama1234.gdx.util.entity.Entity;
import pama1234.gdx.util.listener.EntityListener;
import pama1234.gdx.util.wrapper.DisplayEntity;
import pama1234.gdx.util.wrapper.DisplayEntity.DisplayWithCam;

public class StateGenerator0002{
  public static void loadState0001(Screen0012 in) {
    put(State0002.FirstRun,new FirstRun(in));
    put(State0002.Loading,new Loading(in));
    put(State0002.StartMenu,new StartMenu(in));
    put(State0002.Game,new Game(in));
    put(State0002.Settings,new Settings(in));
    put(State0002.Announcement,new Announcement(in));
    put(State0002.Exception,new ExceptionState(in));
  }
  public static <T extends StateEntityListener0002> void put(State0002 data,T in) {
    data.entity=in;
    data.displayCam=new DisplayEntity(in::displayCam);
  }
  public static abstract class StateEntity0002 extends Entity<Screen0012> implements StateEntityListener0002{
    public StateEntity0002(Screen0012 p) {
      super(p);
    }
  }
  public interface StateEntityListener0002 extends EntityListener,DisplayWithCam{
    default public void from(State0002 in) {}
    default public void to(State0002 in) {}
    // default public void exit() {}
  }
}
