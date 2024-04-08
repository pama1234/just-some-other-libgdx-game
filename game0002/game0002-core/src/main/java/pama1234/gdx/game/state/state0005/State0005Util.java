package pama1234.gdx.game.state.state0005;

import pama1234.gdx.game.util.ParticleScreen3D;
import pama1234.gdx.util.listener.StateChanger;
import pama1234.gdx.util.listener.StateEntityListener;
import pama1234.gdx.util.wrapper.StateCenter;
import pama1234.gdx.util.wrapper.StateEntityBase;

public class State0005Util{
  public static void loadState0005(ParticleScreen3D in,StateCenter0005 center) {
    center.list.add(center.startMenu=new StartMenu(in,0));
    center.list.add(center.game=new Game(in,1));
    center.list.add(center.settings=new Settings(in,2));
  }
  public static void loadState0005Test(ParticleScreen3D in,DebugStateEntitys debug) {
    // debug.list.add(debug.editorTest=new EditorTest(in,-1));
  }
  public static abstract class StateEntity0005 extends StateEntityBase<ParticleScreen3D,StateEntityListener0005,StateEntity0005> implements StateEntityListener0005{
    public StateEntity0005(ParticleScreen3D p,int id) {
      super(p,id);
    }
  }
  public interface StateEntityListener0005 extends StateEntityListener<StateEntity0005>{
  }
  public interface StateChanger0005 extends StateChanger<StateEntity0005>{
  }
  public static class StateCenter0005 extends StateCenter<ParticleScreen3D,StateEntity0005>{
    public DebugStateEntitys debug;

    public StartMenu startMenu;
    public Game game;
    public Settings settings;
    public StateCenter0005(ParticleScreen3D p) {
      super(p);
    }
    public StateCenter0005(ParticleScreen3D p,StateEntity0005 in) {
      super(p,in);
    }
    public StateCenter0005(ParticleScreen3D p,StateEntity0005[] in) {
      super(p,in);
    }
  }
  public static class DebugStateEntitys extends StateCenter<ParticleScreen3D,StateEntity0005>{
    // public EditorTest editorTest;
    public DebugStateEntitys(ParticleScreen3D p) {
      super(p);
    }
    public DebugStateEntitys(ParticleScreen3D p,StateEntity0005 in) {
      super(p,in);
    }
    public DebugStateEntitys(ParticleScreen3D p,StateEntity0005[] in) {
      super(p,in);
    }
  }
}
