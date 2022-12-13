package pama1234.gdx.game.state;

import pama1234.gdx.game.app.Screen0011;
import pama1234.gdx.util.entity.Entity;

public class StateUtil0001{
  public static void loadState0001(Screen0011 in) {
    // State0001[] sa=State0001.stateArray;
    // for(int i=0;i<sa.length;i++);
    State0001.Loading.entity=new Loading(in);
  }
  public static class Loading extends Entity<Screen0011>{
    public Loading(Screen0011 p) {
      super(p);
    }
  }
}
