package pama1234.gdx.game.duel.util.ai.nnet;

import java.util.ArrayList;
import java.util.stream.IntStream;

import pama1234.gdx.game.duel.util.ai.nnet.NetworkGroup.NetworkGroupData;
import pama1234.math.UtilMath;
import pama1234.util.neat.raimannma.architecture.EvolveOptions;
import pama1234.util.neat.raimannma.architecture.NEAT;
import pama1234.util.neat.raimannma.architecture.Network;
import pama1234.util.wrapper.Center;

public class NeatCenter extends Center<NetworkGroup>{
  public NetworkGroupParam param;
  public NeatModule vision,logic,behavior,world;
  //---
  public int index;
  public NeatCenter(NetworkGroupParam param) {
    this.param=param;
    vision=new NeatModule(param.inputSize,param.logicSize,param.visionOptions);
    logic=new NeatModule(param.logicSize,param.logicSize,param.logicOptions);
    behavior=new NeatModule(param.logicSize,param.outputSize,param.behaviorOptions);
    world=new NeatModule(param.outputSize,param.inputSize,param.worldbehavior);
  }
  public NetworkGroup getNext() {
    refresh();
    if(index==list.size()) {
      NetworkGroup out=new NetworkGroup(new NetworkGroupData(
        vision.neat.getFittest(),
        logic.neat.getFittest(),
        behavior.neat.getFittest(),
        world.neat.getFittest()));
      add.add(out);
      refresh();
      index++;
      return out;
    }
    return list.get(index++);
  }
  public static class NeatModule{
    public NEAT neat;
    public NeatModule(int inputSize,int outputSize,EvolveOptions options) {
      neat=new NEAT(inputSize,outputSize,options);
    }
  }
  public static class Dataset{
    public ArrayList<float[]> inputs,outputs;
    public Dataset() {
      inputs=new ArrayList<>();
      outputs=new ArrayList<>();
    }
  }
  public static class NetworkGroupParam{
    public int canvasSize=256;
    public int inputSize,logicSize,outputSize;
    public EvolveOptions visionOptions,logicOptions,behaviorOptions,worldbehavior;
    {
      visionOptions=newEvolveOptions(inputSize,logicSize);
      logicOptions=newEvolveOptions(logicSize,logicSize);
      behaviorOptions=newEvolveOptions(logicSize,outputSize);
      worldbehavior=newEvolveOptions(outputSize,inputSize);
    }
    public EvolveOptions newEvolveOptions(int a,int b) {
      EvolveOptions out=new EvolveOptions();
      out.setError(0.05f);
      out.setPopulationSize(10);
      out.setTemplate(new Network(a,b));
      out.setFitnessFunction(genome->genome.score);
      return out;
    }
    public NetworkGroupParam(int canvasSize) {
      this.canvasSize=canvasSize;
      inputSize=UtilMath.sq(canvasSize)*3;
      logicSize=64;
      outputSize=4;
    }
  }
}