package pama1234.gdx.game.state.state0001;

import pama1234.gdx.game.app.Screen0011;
import pama1234.gdx.util.listener.StateEntityListener;
import pama1234.gdx.util.wrapper.DisplayEntity;
import pama1234.gdx.util.wrapper.StateEntity;

public class StateGenerator0001{
  public static void loadState0001(Screen0011 in) {
    put(State0001.FirstRun,new FirstRun(in));
    put(State0001.Loading,new Loading(in));
    put(State0001.StartMenu,new StartMenu(in));
    put(State0001.GameMenu,new GameMenu(in));
    put(State0001.Game,new Game(in));
    put(State0001.Settings,new Settings(in));
    put(State0001.Announcement,new Announcement(in));
    put(State0001.Exception,new ExceptionState(in));
    put(State0001.Debug,new Debug(in));
  }
  public static <T extends StateEntity0001> void put(State0001 data,T in) {
    // data.entity=in;
    // data.displayCam=new DisplayEntity(in::displayCam);
    data.entity=in;
  }
  public static abstract class StateEntity0001 extends StateEntity<Screen0011,StateEntityListener0001> implements StateEntityListener0001{
    public StateEntity0001(Screen0011 p) {
      super(p);
      displayCam=new DisplayEntity(this::displayCam);
    }
  }
  public interface StateEntityListener0001 extends StateEntityListener<State0001>{
    @Override
    default public void from(State0001 in) {}
    @Override
    default public void to(State0001 in) {}
  }
}