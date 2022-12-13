package pama1234.gdx.game.state;

public enum State0001{
  FirstRun,
  Loading,
  StartMenu,
  Game,
  Settings,
  Announcement,
  Exception;
  public static State0001[] netStateArray=State0001.values();
  public static int stateToInt(State0001 in) {
    return in.ordinal();
  }
  public static State0001 intToState(int in) {
    if(in<0||in>netStateArray.length) {
      System.out.println("State0001 intToState in="+in);
      return State0001.Exception;
    }
    return netStateArray[in];
  }
}
