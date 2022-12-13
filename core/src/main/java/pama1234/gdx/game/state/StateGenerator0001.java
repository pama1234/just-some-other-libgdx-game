package pama1234.gdx.game.state;

import pama1234.gdx.game.app.Screen0011;
import pama1234.gdx.game.state.DisplayEntity.DisplayWithCam;
import pama1234.gdx.util.entity.Entity;
import pama1234.gdx.util.listener.EntityListener;

public class StateGenerator0001{
  public static void loadState0001(Screen0011 in) {
    // State0001[] sa=State0001.stateArray;
    // for(int i=0;i<sa.length;i++);
    put(State0001.Loading,new Loading(in));
    put(State0001.FirstRun,new FirstRun(in));
  }
  public static <T extends DisplayWithCam&EntityListener> void put(State0001 data,T in) {
    data.entity=in;
    data.displayCam=new DisplayEntity(in::displayCam);
  }
  public static abstract class StateEntity0001 extends Entity<Screen0011> implements DisplayWithCam{
    public StateEntity0001(Screen0011 p) {
      super(p);
    }
  }
  //------------------------------------------------------------------------
  public static class Loading extends StateEntity0001{
    public Loading(Screen0011 p) {
      super(p);
    }
    @Override
    public void displayCam() {
      p.text("Loading",0,0);
      // System.out.println("1234");
    }
  }
  public static class FirstRun extends StateEntity0001{
    public FirstRun(Screen0011 p) {
      super(p);
    }
    @Override
    public void displayCam() {
      p.text("FirstRun",0,0);
    }
  }
}
