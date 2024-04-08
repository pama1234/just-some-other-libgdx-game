package pama1234.app.game.server.duel.util.input;

import pama1234.util.net.SocketData;

public class ServerInputOutput{
  public ServerInputData inputData;
  public SocketData socket;
  // public InputData protoInput;
  // public OutputData protoOutput;
  public ServerInputOutput() {
    inputData=new ServerInputData();
  }
  // public ServerInputOutput(InputData.Builder builder) {
  //   this();
  //   this.protoInput=builder.build();
  // }
}
