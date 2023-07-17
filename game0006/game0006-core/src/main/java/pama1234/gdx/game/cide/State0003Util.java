package pama1234.gdx.game.cide;

import pama1234.gdx.game.cide.state.state0003.EditorTest;
import pama1234.gdx.game.cide.state.state0003.FirstRun;
import pama1234.gdx.game.cide.state.state0003.Loading;
import pama1234.gdx.game.cide.state.state0003.MainMenu;
import pama1234.gdx.game.cide.state.state0003.Settings;
import pama1234.gdx.game.cide.util.app.ScreenCide2D;
import pama1234.gdx.util.listener.StateChanger;
import pama1234.gdx.util.listener.StateEntityListener;
import pama1234.gdx.util.wrapper.StateCenter;
import pama1234.gdx.util.wrapper.StateEntityBase;

public class State0003Util{
  public static void loadState0003(ScreenCide2D in,StateCenter0003 center) {
    center.list.add(center.firstRun=new FirstRun(in,0));
    center.list.add(center.loading=new Loading(in,1));
    center.list.add(center.mainMenu=new MainMenu(in,2));
    center.list.add(center.settings=new Settings(in,3));
  }
  public static void loadState0003Test(ScreenCide2D in,DebugStateEntitys debug) {
    debug.list.add(debug.editorTest=new EditorTest(in,-1));
  }
  public static abstract class StateEntity0003 extends StateEntityBase<ScreenCide2D,StateEntityListener0003,StateEntity0003> implements StateEntityListener0003{
    public StateEntity0003(ScreenCide2D p,int id) {
      super(p,id);
    }
  }
  public interface StateEntityListener0003 extends StateEntityListener<StateEntity0003>{
  }
  public interface StateChanger0003 extends StateChanger<StateEntity0003>{
  }
  public static class StateCenter0003 extends StateCenter<ScreenCide2D,StateEntity0003>{
    public DebugStateEntitys debug;
    public FirstRun firstRun;
    public Loading loading;
    public MainMenu mainMenu;
    public Settings settings;
    public StateCenter0003(ScreenCide2D p) {
      super(p);
    }
    public StateCenter0003(ScreenCide2D p,StateEntity0003 in) {
      super(p,in);
    }
    public StateCenter0003(ScreenCide2D p,StateEntity0003[] in) {
      super(p,in);
    }
  }
  public static class DebugStateEntitys extends StateCenter<ScreenCide2D,StateEntity0003>{
    public EditorTest editorTest;
    public DebugStateEntitys(ScreenCide2D p) {
      super(p);
    }
    public DebugStateEntitys(ScreenCide2D p,StateEntity0003 in) {
      super(p,in);
    }
    public DebugStateEntitys(ScreenCide2D p,StateEntity0003[] in) {
      super(p,in);
    }
  }
}