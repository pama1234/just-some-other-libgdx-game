package pama1234.gdx.game.duel;

import pama1234.gdx.game.state.state0002.Game;
import pama1234.gdx.game.state.state0002.Settings;
import pama1234.gdx.util.listener.StateChanger;
import pama1234.gdx.util.listener.StateEntityListener;
import pama1234.gdx.util.wrapper.DisplayEntity;
import pama1234.gdx.util.wrapper.StateCenter;
import pama1234.gdx.util.wrapper.StateEntity;

public class State0002Util{
  public static void loadState0002(Duel in,StateCenter0002 center) {
    center.list.add(center.game=new Game(in,0));
    center.list.add(center.settings=new Settings(in,0));
  }
  public static abstract class StateEntity0002 extends StateEntity<Duel,StateEntityListener0001> implements StateEntityListener0001{
    public int id;
    public StateEntity0002(Duel p,int id) {
      super(p);
      displayCam=new DisplayEntity(this::displayCam);
      this.id=id;
    }
  }
  public interface StateEntityListener0001 extends StateEntityListener<StateEntity0002>{
    @Override
    default public void from(StateEntity0002 in) {}
    @Override
    default public void to(StateEntity0002 in) {}
  }
  public interface StateChanger0002 extends StateChanger<StateEntity0002>{
  }
  public static class StateCenter0002 extends StateCenter<Duel,StateEntity0002>{
    public Game game;
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
    @Deprecated
    public void disposeAll() {
      dispose();
    }
  }
}