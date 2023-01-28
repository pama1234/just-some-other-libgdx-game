package pama1234.game.app.server.server0002;

public enum SceneState{
  testWorld,
  SceneException,;
  public static SceneState[] netStateArray=SceneState.values();
  public static int stateToInt(SceneState in) {
    return in.ordinal();
  }
  public static SceneState intToState(int in) {
    if(in<0||in>netStateArray.length) {
      System.out.println("ServerState intToState in="+in);
      return SceneState.SceneException;
    }
    return netStateArray[in];
  }
}
