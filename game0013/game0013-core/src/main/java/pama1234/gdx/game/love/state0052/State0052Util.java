package pama1234.gdx.game.love.state0052;

import pama1234.gdx.game.app.app0002.Screen0052;
import pama1234.gdx.util.listener.StateChanger;
import pama1234.gdx.util.listener.StateEntityListener;
import pama1234.gdx.util.wrapper.StateCenter;
import pama1234.gdx.util.wrapper.StateEntityBase;

public class State0052Util{

  public static void loadState0052(Screen0052 in,StateCenter0052 center) {
    center.list.add(center.firstRun=new FirstRun(in));
    center.list.add(center.loading=new Loading(in));
    center.list.add(center.mainMenu=new MainMenu(in));
    center.list.add(center.settings=new Settings(in));
  }
  public static abstract class StateEntity0052 extends StateEntityBase<Screen0052,StateEntityListener0052,StateEntity0052> implements StateEntityListener0052{
    public StateEntity0052(Screen0052 p) {
      super(p);
    }
  }
  public interface StateEntityListener0052 extends StateEntityListener<StateEntity0052>{
  }
  public interface StateChanger0052 extends StateChanger<StateEntity0052>{
  }
  public static class StateCenter0052 extends StateCenter<Screen0052,StateEntity0052>{
    public FirstRun firstRun;
    public Loading loading;
    public MainMenu mainMenu;
    public Settings settings;
    public StateCenter0052(Screen0052 p) {
      super(p);
    }
    public StateCenter0052(Screen0052 p,StateEntity0052 in) {
      super(p,in);
    }
    public StateCenter0052(Screen0052 p,StateEntity0052[] in) {
      super(p,in);
    }
  }
}
