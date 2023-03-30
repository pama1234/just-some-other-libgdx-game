package pama1234.gdx.game.duel.util.ai.nnet;

import java.util.ArrayList;

import pama1234.math.UtilMath;
import pama1234.util.neat.raimannma.architecture.EvolveOptions;
import pama1234.util.neat.raimannma.architecture.NEAT;
import pama1234.util.wrapper.Center;

public class NeatCenter extends Center<NetworkGroup>{
  public NetworkGroupParam param;
  public NeatModule vision,logic,behavior,world;
  public NeatCenter(NetworkGroupParam param) {
    this.param=param;
    vision=new NeatModule(param.inputSize,param.logicSize,param.options);
    logic=new NeatModule(param.logicSize,param.logicSize,param.options);
    behavior=new NeatModule(param.logicSize,param.outputSize,param.options);
    world=new NeatModule(param.outputSize,param.inputSize,param.options);
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
    public EvolveOptions options;
    {
      options=new EvolveOptions();
      options.setError(0.05f);
      options.setPopulationSize(10);
    }
    public NetworkGroupParam(int canvasSize) {
      this.canvasSize=canvasSize;
      inputSize=UtilMath.sq(canvasSize)*3;
      logicSize=64;
      outputSize=4;
    }
  }
}