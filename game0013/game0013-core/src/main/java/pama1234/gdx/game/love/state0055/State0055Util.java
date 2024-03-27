package pama1234.gdx.game.love.state0055;

import pama1234.gdx.game.app.app0002.Screen0055;
import pama1234.gdx.util.listener.StateChanger;
import pama1234.gdx.util.listener.StateEntityListener;
import pama1234.gdx.util.wrapper.StateCenter;
import pama1234.gdx.util.wrapper.StateEntityBase;

public class State0055Util {

  public static void loadState0055(Screen0055 in,StateCenter0055 center) {
    center.list.add(center.firstRun=new FirstRun(in));
    center.list.add(center.loading=new Loading(in));
    center.list.add(center.mainMenu=new MainMenu(in));
    center.list.add(center.settings=new Settings(in));

//    center.list.add(center.bulletTest =new BulletTest(in));
//    center.list.add(center.bulletTest =new BulletTest3D(in));
  }
  public static abstract class StateEntity0055 extends StateEntityBase<Screen0055,StateEntityListener0055,StateEntity0055> implements StateEntityListener0055{
    public StateEntity0055(Screen0055 p) {
      super(p);
    }
  }
  public interface StateEntityListener0055 extends StateEntityListener<StateEntity0055>{
  }
  public interface StateChanger0055 extends StateChanger<StateEntity0055>{
  }
  public static class StateCenter0055 extends StateCenter<Screen0055,StateEntity0055>{
    public FirstRun firstRun;
    public Loading loading;
    public MainMenu mainMenu;
    public Settings settings;

//    public BulletTest bulletTest;
    public StateCenter0055(Screen0055 p) {
      super(p);
    }
    public StateCenter0055(Screen0055 p,StateEntity0055 in) {
      super(p,in);
    }
    public StateCenter0055(Screen0055 p,StateEntity0055[] in) {
      super(p,in);
    }
  }
}
