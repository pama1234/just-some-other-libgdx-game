package pama1234.game.app.server.server0001.game.net.state;

public enum ServerState{
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