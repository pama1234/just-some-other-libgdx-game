package pama1234.gdx.game.state.state0001;

import pama1234.gdx.game.app.Screen0011;
import pama1234.gdx.util.listener.StateEntityListener;
import pama1234.gdx.util.wrapper.DisplayEntity;
import pama1234.gdx.util.wrapper.StateCenter;
import pama1234.gdx.util.wrapper.StateEntity;

public class StateGenerator0001{
  public static void loadState0001(Screen0011 in,StateCenter0001 center) {
    center.list.add(center.FirstRun=new FirstRun(in));
    center.list.add(center.Loading=new Loading(in));
    center.list.add(center.StartMenu=new StartMenu(in));
    center.list.add(center.GameMenu=new GameMenu(in));
    center.list.add(center.Game=new Game(in));
    center.list.add(center.Settings=new Settings(in));
    center.list.add(center.Announcement=new Announcement(in));
    center.list.add(center.Exception=new ExceptionState(in));
    center.list.add(center.Debug=new Debug(in));
  }
  public static void loadState0001(Screen0011 in) {
    State0001.FirstRun.entity=new FirstRun(in);
    State0001.Loading.entity=new Loading(in);
    State0001.StartMenu.entity=new StartMenu(in);
    State0001.GameMenu.entity=new GameMenu(in);
    State0001.Game.entity=new Game(in);
    State0001.Settings.entity=new Settings(in);
    State0001.Announcement.entity=new Announcement(in);
    State0001.Exception.entity=new ExceptionState(in);
    State0001.Debug.entity=new Debug(in);
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
  public static class StateCenter0001 extends StateCenter<Screen0011,StateEntity0001>{
    //TODO 使用了较为不科学的首字母大写
    public StateEntity0001 FirstRun,Loading,
      StartMenu,GameMenu,
      Game,
      Settings,Announcement,
      Exception,Debug;
    public StateCenter0001(Screen0011 p) {
      super(p);
    }
    public StateCenter0001(Screen0011 p,StateEntity0001 in) {
      super(p,in);
    }
    public StateCenter0001(Screen0011 p,StateEntity0001[] in) {
      super(p,in);
    }
  }
}