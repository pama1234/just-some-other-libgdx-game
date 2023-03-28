package pama1234.gdx.game.app;

import java.util.stream.IntStream;

import pama1234.gdx.util.app.ScreenCore2D;
import pama1234.util.function.ExecuteFunction;
import pama1234.util.neat.raimannma.architecture.EvolveOptions;
import pama1234.util.neat.raimannma.architecture.NEAT;
import pama1234.util.neat.raimannma.architecture.Network;
import pama1234.util.neat.raimannma.methods.Loss;

public class Screen0020 extends ScreenCore2D{
  public NEAT neat;
  public EvolveOptions options;
  public Network network;
  //---
  public float[][] inputs;
  public float[][] outputs;
  public float score;
  //---
  public float error=-Float.MAX_VALUE;
  public float bestScore=-Float.MAX_VALUE;
  public Network bestGenome=null;
  //---
  public int state;
  public ExecuteFunction[] stateDo;
  @Override
  public void setup() {
    stateDo=new ExecuteFunction[] {this::evolve,ExecuteFunction.doNothing};
    //---
    options=new EvolveOptions();
    options.setLog(5);
    options.setError(0.05f);
    if(Double.isNaN(options.getError())) {
      options.setError(-1);
    }
    // else if(options.getIterations()<=0) {
    //   options.setIterations(Integer.MAX_VALUE);
    // }
    final int amount=options.getAmount();
    final float growth=options.getGrowth();
    final Loss loss=options.getLoss();
    options.setFitnessFunction(genome-> {
      final float sum=(float)IntStream.range(0,amount)
        .mapToDouble(i->-genome.test(inputs,outputs,loss))
        .sum();
      return (sum-genome.getGrowthScore(growth))/amount;
    });
    //---
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
    neat=new NEAT(network.inputSize,network.outputSize,options);
  }
  @Override
  public void update() {
    stateDo[state].execute();
  }
  public void evolve() {
    // System.out.println(error+" "+options.getError()+" "+neat.generation+" "+options.getIterations());
    if(error<-options.getError()&&
      (neat.generation<options.getIterations()||options.getIterations()<0)) {
      final Network fittest=neat.evolve(); // run one evolution step
      error=fittest.score+fittest.getGrowthScore(options.getGrowth()); // calculate error of the fittest genome
      if(fittest.score>bestScore) {
        // if fittest is fitter than the best genome so far
        // set fittest to new global best genome
        bestScore=fittest.score;
        bestGenome=fittest;
      }
      // Log
      network.printLog(options,neat,error,fittest);
    }else {
      state=1;
      if(bestGenome!=null) {
        // set this network equal to fittest network of evolution
        network.nodes=bestGenome.nodes;
        network.connections=bestGenome.connections;
        network.selfConnections=bestGenome.selfConnections;
        network.gates=bestGenome.gates;
        if(options.isClear()) network.clear();
      }
    }
  }
  @Override
  public void displayWithCam() {
    text("测试："+network.test(inputs,outputs),0,-20);
    text("得分："+score,0,0);
  }
  @Override
  public void display() {}
  @Override
  public void frameResized() {}
  @Override
  public void dispose() {
    super.dispose();
  }
}