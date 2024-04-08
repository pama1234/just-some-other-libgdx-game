package pama1234.gdx.game.app;

import pama1234.gdx.game.app.App0001.State0015Util.StateCenter0015;
import pama1234.gdx.game.app.App0001.State0015Util.StateEntity0015;
import pama1234.gdx.game.mbti.GameDesk;
import pama1234.gdx.util.app.ScreenCoreState2D;
import pama1234.gdx.util.listener.StateChanger;
import pama1234.gdx.util.listener.StateEntityListener;
import pama1234.gdx.util.wrapper.StateCenter;
import pama1234.gdx.util.wrapper.StateEntityBase;

public class App0001 extends ScreenCoreState2D<StateCenter0015,StateEntity0015>{
  GameDesk desk;
  @Override
  public void setup() {
    desk=new GameDesk(this);

    centerNeo.add(desk);
  }

  @Override
  public void update() {

  }

  @Override
  public void display() {

  }

  @Override
  public void displayWithCam() {

  }

  @Override
  public void frameResized() {

  }

  public static class State0015Util{
    public static void loadState0015(App0001 in,StateCenter0015 center) {
      //      center.list.add(center.startMenu=new StartMenu(in,0));
      //      center.list.add(center.game=new Game(in,1));
      //      center.list.add(center.tutorial=new Tutorial(in,2));
      //      center.list.add(center.settings=new Settings(in,3));
    }
    //    public static void loadState0003Test(App0001 in,DebugStateEntitys debug) {
    //      debug.list.add(debug.gamePrototype=new GamePrototype(in,-1));
    //    }
    public static abstract class StateEntity0015 extends StateEntityBase<App0001,StateEntityListener0015,StateEntity0015> implements StateEntityListener0015{
      public StateEntity0015(App0001 p,int id) {
        super(p,id);
      }
    }
    public interface StateEntityListener0015 extends StateEntityListener<StateEntity0015>{
    }
    public interface StateChanger0015 extends StateChanger<StateEntity0015>{
    }
    public static class StateCenter0015 extends StateCenter<App0001,StateEntity0015>{
      //      public DebugStateEntitys debug;
      //      public StartMenu startMenu;
      //      public Game game;
      //      public Tutorial tutorial;
      //      public Settings settings;
      public StateCenter0015(App0001 p) {
        super(p);
      }
      public StateCenter0015(App0001 p,StateEntity0015 in) {
        super(p,in);
      }
      public StateCenter0015(App0001 p,StateEntity0015[] in) {
        super(p,in);
      }
    }
  }
}
