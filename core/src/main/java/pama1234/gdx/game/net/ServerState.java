package pama1234.gdx.game.net;

public enum ServerState{
  ServerProcessing,
  ServerFinishedProcessing,
  ServerProtocolVersion,
  ServerAuthentication,
  ServerDataTransfer,
  ServerException;
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