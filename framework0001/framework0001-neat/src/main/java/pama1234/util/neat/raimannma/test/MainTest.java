package pama1234.util.neat.raimannma.test;

import pama1234.util.neat.raimannma.architecture.EvolveOptions;
import pama1234.util.neat.raimannma.architecture.Network;

public class MainTest{
  public static void main(String[] args) {
    EvolveOptions options;
    Network network;
    float[][] inputs;
    float[][] outputs;

    options=new EvolveOptions();
    options.setLog(5);
    options.setError(0.05f);
    options.setTemplate(new Network(2,1));

    inputs=new float[][] {
      {0,0},
      {0,1},
      {1,0},
      {1,1},
    };
    outputs=new float[][] {
      {0},
      {0},
      {0},
      {1},
    };
    network=new Network(inputs[0].length,outputs[0].length);
    System.out.println(network.evolve(inputs,outputs,options));
    System.out.println(network.toString());
  }
}