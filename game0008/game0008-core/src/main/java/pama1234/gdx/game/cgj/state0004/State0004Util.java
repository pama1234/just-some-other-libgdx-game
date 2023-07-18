package pama1234.gdx.game.cgj.state0004;

import pama1234.gdx.game.cgj.app.app0002.RealGame0002;
import pama1234.gdx.util.listener.StateChanger;
import pama1234.gdx.util.listener.StateEntityListener;
import pama1234.gdx.util.wrapper.StateCenter;
import pama1234.gdx.util.wrapper.StateEntityBase;

public class State0004Util{
  public static void loadState0004(RealGame0002 in,StateCenter0004 center) {
    center.list.add(center.startMenu=new StartMenu(in,0));
    center.list.add(center.game=new Game(in,1));
    center.list.add(center.settings=new Settings(in,2));
    center.list.add(center.tutorial=new Tutorial(in,3));
  }
  public static void loadState0004Test(RealGame0002 in,DebugStateEntitys debug) {
    // debug.list.add(debug.editorTest=new EditorTest(in,-1));
  }
  public static abstract class StateEntity0004 extends StateEntityBase<RealGame0002,StateEntityListener0004,StateEntity0004> implements StateEntityListener0004{
    public StateEntity0004(RealGame0002 p,int id) {
      super(p,id);
    }
  }
  public interface StateEntityListener0004 extends StateEntityListener<StateEntity0004>{
  }
  public interface StateChanger0004 extends StateChanger<StateEntity0004>{
  }
  public static class StateCenter0004 extends StateCenter<RealGame0002,StateEntity0004>{
    public DebugStateEntitys debug;
    public StartMenu startMenu;
    public Game game;
    public Settings settings;
    public Tutorial tutorial;
    public StateCenter0004(RealGame0002 p) {
      super(p);
    }
    public StateCenter0004(RealGame0002 p,StateEntity0004 in) {
      super(p,in);
    }
    public StateCenter0004(RealGame0002 p,StateEntity0004[] in) {
      super(p,in);
    }
  }
  public static class DebugStateEntitys extends StateCenter<RealGame0002,StateEntity0004>{
    // public EditorTest editorTest;
    public DebugStateEntitys(RealGame0002 p) {
      super(p);
    }
    public DebugStateEntitys(RealGame0002 p,StateEntity0004 in) {
      super(p,in);
    }
    public DebugStateEntitys(RealGame0002 p,StateEntity0004[] in) {
      super(p,in);
    }
  }
}
