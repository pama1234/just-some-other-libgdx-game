package pama1234.gdx.game.app;

import pama1234.gdx.game.neat.util.raimannma.architecture.EvolveOptions;
import pama1234.gdx.game.neat.util.raimannma.architecture.Network;
import pama1234.gdx.util.app.ScreenCore2D;

/**
 * 此草图直接搬运并魔改了 https://github.com/raimannma/NEAT4J 的neat实现
 */
public class Screen0019 extends ScreenCore2D{
  // public NEAT neat;
  public Network network;
  public EvolveOptions options;
  public float[][] inputs;
  public float[][] outputs;
  public float score;
  //---
  public Thread evolve;
  @Override
  public void setup() {
    options=new EvolveOptions();
    options.setLog(1);
    options.setError(0.05f);
    network=new Network(2,1);
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
    (evolve=new Thread(()->score=network.evolve(inputs,outputs,options),
      "NEAT Evolve Thread")).start();
  }
  @Override
  public void update() {
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
    evolve.interrupt();
    evolve.stop();
    // neat.dispose();
  }
}