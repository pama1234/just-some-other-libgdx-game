package pama1234.game.app.server.server0001.game.net.state;

public enum ClientState{
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