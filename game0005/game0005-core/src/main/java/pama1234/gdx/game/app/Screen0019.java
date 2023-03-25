package pama1234.gdx.game.app;

import java.util.Arrays;

import pama1234.gdx.game.neat.util.raimannma.architecture.EvolveOptions;
import pama1234.gdx.game.neat.util.raimannma.architecture.NEAT;
import pama1234.gdx.game.neat.util.raimannma.architecture.Network;
import pama1234.gdx.util.app.ScreenCore2D;

/**
 * 此草图直接搬运并魔改了 https://github.com/raimannma/NEAT4J 的neat实现
 */
public class Screen0019 extends ScreenCore2D{
  public NEAT neat;
  public Network network;
  public EvolveOptions options;
  public float[][] inputs;
  public float[][] outputs;
  @Override
  public void setup() {
    // 初始化neat对象
    // 2, // 输入节点数
    // 1, // 输出节点数
    // 100, // 最大种群数
    // 0.5, // 物种间距离阈值
    // 0.2, // 物种同一性阈值
    // 0.5, // 物种基因杂交概率
    // 0.5, // 物种基因突变概率
    // 0.1, // 神经元基因添加概率
    // 0.1, // 连接基因添加概率
    // new FeedForwardNetwork(), // 神经网络类型
    // new EuclideanDistanceMetric() // 物种距离计算方法
    options=new EvolveOptions();
    options.setError(0.05f); // set target error for evolution
    options.setFitnessFunction(n->n.score);//TODO
    neat=new NEAT(2,1,options);
    // 创建并训练网络
    // neat.createPopulation();
    // neat.train();
    // 获取最佳网络
    // network=neat.evolve();
    network=new Network(2,1);
    // 初始化输入和输出数组
    inputs=new float[][] {{0.0f,1.0f}};
    outputs=new float[1][1];
  }
  @Override
  public void update() {
    // 更新网络
    // network.setInputValues(inputs);
    // network.calculate();
    // network.getOutputValues(outputs);
    // network.test(inputs,outputs);
    network.evolve(inputs,outputs,options);
    // 输出结果
    System.out.println("Output: "+Arrays.deepToString(outputs));
  }
  @Override
  public void displayWithCam() {}
  @Override
  public void display() {}
  @Override
  public void frameResized() {}
  @Override
  public void dispose() {
    super.dispose();
    // neat.dispose();
  }
}