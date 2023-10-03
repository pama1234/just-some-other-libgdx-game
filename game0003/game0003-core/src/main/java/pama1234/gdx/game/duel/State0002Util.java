package pama1234.gdx.game.duel;

import pama1234.gdx.game.state.state0002.Game;
import pama1234.gdx.game.state.state0002.GamePrototype;
import pama1234.gdx.game.state.state0002.Settings;
import pama1234.gdx.game.state.state0002.StartMenu;
import pama1234.gdx.game.state.state0002.Tutorial;
import pama1234.gdx.util.listener.StateChanger;
import pama1234.gdx.util.listener.StateEntityListener;
import pama1234.gdx.util.wrapper.StateCenter;
import pama1234.gdx.util.wrapper.StateEntityBase;

public class State0002Util{
  public static void loadState0002(Duel in,StateCenter0002 center) {
    center.list.add(center.startMenu=new StartMenu(in,0));
    center.list.add(center.game=new Game(in,1));
    center.list.add(center.tutorial=new Tutorial(in,2));
    center.list.add(center.settings=new Settings(in,3));
  }
  public static void loadState0003Test(Duel in,DebugStateEntitys debug) {
    debug.list.add(debug.gamePrototype=new GamePrototype(in,-1));
  }
  public static abstract class StateEntity0002 extends StateEntityBase<Duel,StateEntityListener0002,StateEntity0002> implements StateEntityListener0002{
    public StateEntity0002(Duel p,int id) {
      super(p,id);
    }
  }
  public interface StateEntityListener0002 extends StateEntityListener<StateEntity0002>{
  }
  public interface StateChanger0002 extends StateChanger<StateEntity0002>{
  }
  public static class StateCenter0002 extends StateCenter<Duel,StateEntity0002>{
    public DebugStateEntitys debug;
    public StartMenu startMenu;
    public Game game;
    public Tutorial tutorial;
    public Settings settings;
    public StateCenter0002(Duel p) {
      super(p);
    }
    public StateCenter0002(Duel p,StateEntity0002 in) {
      super(p,in);
    }
    public StateCenter0002(Duel p,StateEntity0002[] in) {
      super(p,in);
    }
  }
  public static class DebugStateEntitys extends StateCenter<Duel,StateEntity0002>{
    public GamePrototype gamePrototype;
    public DebugStateEntitys(Duel p) {
      super(p);
    }
  }
}