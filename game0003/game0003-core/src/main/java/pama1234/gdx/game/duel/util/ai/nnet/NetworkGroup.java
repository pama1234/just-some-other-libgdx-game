package pama1234.gdx.game.duel.util.ai.nnet;

import pama1234.util.neat.raimannma.architecture.FloatBlock;
import pama1234.util.neat.raimannma.architecture.Network;
import pama1234.util.neat.raimannma.architecture.NetworkModule;

/**
 * <pre>
 * {@code
 * 业务逻辑如下：
 *    1. 视觉（梯形）
 *    2. 业务逻辑（长方形）
 *    3. 动作执行（梯形）
 *    4. 世界模型
 *    +----1----+----2---+----3---+
 * 
 *    *+++··    |        |        |
 *    *+++++++··*········*····    |
 *    *+++++++++>++++++++>++++++··>
 *    *+++++++··*········*····    |
 *    *+++··    |        |        |
 * 
 * +--> vision -> logic -> behavior -+
 * |                                 |
 * +------------- world <------------+
 * }</pre>
 */
public class NetworkGroup{
  public FloatBlock input,output;
  public FloatBlock logicInput,logicOutput;
  public FloatBlock memory;
  public NetworkModule vision,logic,behavior,world;
  public NetworkGroup() {}
  public NetworkGroup(
    FloatBlock input,FloatBlock output,
    FloatBlock logicInput,FloatBlock logicOutput,
    FloatBlock memory,
    Network vision,Network logic,Network behavior,Network world) {
    this.input=input;
    this.output=output;
    this.logicInput=logicInput;
    this.logicOutput=logicOutput;
    this.memory=memory;
    this.vision=new NetworkModule(vision,input,logicInput);
    this.logic=new NetworkModule(logic,logicInput,logicOutput);
    this.behavior=new NetworkModule(behavior,logicOutput,output);
    this.world=new NetworkModule(world,output,input);
  }
  public void execute() {
    vision.execute();
    logic.execute();
    behavior.execute();
  }
  public void setScore(float score) {
    vision.network.score=score;
    logic.network.score=score;
    behavior.network.score=score;
    world.network.score=score;
  }
}