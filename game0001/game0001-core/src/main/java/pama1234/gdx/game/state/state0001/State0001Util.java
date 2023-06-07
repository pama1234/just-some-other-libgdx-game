package pama1234.gdx.game.state.state0001;

import pama1234.gdx.game.app.Screen0011;
import pama1234.gdx.util.listener.StateChanger;
import pama1234.gdx.util.listener.StateEntityListener;
import pama1234.gdx.util.wrapper.DisplayEntity;
import pama1234.gdx.util.wrapper.StateCenter;
import pama1234.gdx.util.wrapper.StateEntity;

public class State0001Util{
  public static void loadState0001(Screen0011 in,StateCenter0001 center) {
    center.list.add(center.FirstRun=new FirstRun(in,0));
    center.list.add(center.Loading=new Loading(in,1));
    center.list.add(center.StartMenu=new StartMenu(in,2));
    center.list.add(center.GameMenu=new GameMenu(in,3));
    center.list.add(center.Game=new Game(in,4));
    center.list.add(center.Settings=new Settings(in,5));
    center.list.add(center.Announcement=new Announcement(in,6));
    center.list.add(center.Exception=new ExceptionState(in,7));
    center.list.add(center.Debug=new Debug(in,8));
  }
  public static abstract class StateEntity0001 extends StateEntity<Screen0011,StateEntityListener0001> implements StateEntityListener0001{
    public int id;
    public StateEntity0001(Screen0011 p,int id) {
      super(p);
      displayCam=new DisplayEntity(this::displayCam);
      this.id=id;
    }
  }
  public interface StateEntityListener0001 extends StateEntityListener<StateEntity0001>{
    @Override
    default public void from(StateEntity0001 in) {}
    @Override
    default public void to(StateEntity0001 in) {}
  }
  public interface StateChanger0001 extends StateChanger<StateEntity0001>{
  }
  public static class StateCenter0001 extends StateCenter<Screen0011,StateEntity0001>{
    //TODO 使用了较为不科学的首字母大写
    public FirstRun FirstRun;
    public Loading Loading;
    public StartMenu StartMenu;
    public GameMenu GameMenu;
    public Game Game;
    public Settings Settings;
    public Announcement Announcement;
    public ExceptionState Exception;
    public Debug Debug;
    public StateCenter0001(Screen0011 p) {
      super(p);
    }
    public StateCenter0001(Screen0011 p,StateEntity0001 in) {
      super(p,in);
    }
    public StateCenter0001(Screen0011 p,StateEntity0001[] in) {
      super(p,in);
    }
    @Deprecated
    public void disposeAll() {
      dispose();
    }
  }
}