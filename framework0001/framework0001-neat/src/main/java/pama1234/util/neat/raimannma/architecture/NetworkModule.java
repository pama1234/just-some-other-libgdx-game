package pama1234.util.neat.raimannma.architecture;

public class NetworkModule{
  public Network network;
  public FloatBlock input,output;
  public NetworkModule(Network network) {
    this.network=network;
  }
  public NetworkModule(Network network,FloatBlock input,FloatBlock output) {
    this(network);
  }
  public void execute() {
    network.activate(input,output);
  }
  public float[] execute(float[] in) {
    input.data(in);
    return network.activate(
      0,in.length,in,
      output.offset,output.size,output.data());
  }
}