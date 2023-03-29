package pama1234.gdx.game.duel.util.ai.nnet;

import pama1234.util.neat.raimannma.architecture.Network;

public class NetworkModule{
  public Network network;
  public FloatBlock input,output;
  public NetworkModule(Network network) {
    this.network=network;
  }
  public float[] activate(float[] in) {
    input.data(in);
    return network.activate(
      0,in,
      output.offset,output.data());
  }
  public static class FloatBlock{
    public int offset,size;
    private float[] data;
    public FloatBlock(float[] data) {
      this.data=data;
      size=data.length;
    }
    public float[] data() {
      return data;
    }
    public void data(float[] in) {
      offset=0;
      size=in.length;
      data=in;
    }
  }
}