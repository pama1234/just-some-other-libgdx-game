package pama1234.gdx.game.state.state0001.game.net;

public class NetState{
  public enum NetMode{
    singlePlayer,integratedServer,client;
  }
  public static enum SceneState{
    TestWorld,
    SceneException;
    public static SceneState[] netStateArray=SceneState.values();
    public static int stateToInt(SceneState in) {
      return in.ordinal();
    }
    public static SceneState intToState(int in) {
      if(in<0||in>netStateArray.length) {
        System.out.println("SceneState intToState in="+in);
        return SceneState.SceneException;
      }
      return netStateArray[in];
    }
  }
}