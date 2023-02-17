package pama1234.gdx.game.state.state0001.game.net;

public class NetState0002{
  public static enum ClientState{
    ClientProcessing,
    ClientFinishedProcessing,
    ClientProtocolVersion,
    ClientAuthentication,
    ClientDataTransfer,
    ClientException,
    ClientSendStringMessage,;
    public static ClientState[] netStateArray=ClientState.values();
    public static int stateToInt(ClientState in) {
      return in.ordinal();
    }
    public static ClientState intToState(int in) {
      if(in<0||in>netStateArray.length) {
        System.out.println("ClientState intToState in="+in);
        return ClientState.ClientException;
      }
      return netStateArray[in];
    }
  }
  public static enum ServerState{
    ServerProcessing,
    ServerFinishedProcessing,
    ServerProtocolVersion,
    ServerAuthentication,
    ServerDataTransfer,
    ServerException,
    ServerSendStringMessage,
    ServerSendSceneChange,;
    public static ServerState[] netStateArray=ServerState.values();
    public static int stateToInt(ServerState in) {
      return in.ordinal();
    }
    public static ServerState intToState(int in) {
      if(in<0||in>netStateArray.length) {
        System.out.println("ServerState intToState in="+in);
        return ServerState.ServerException;
      }
      return netStateArray[in];
    }
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