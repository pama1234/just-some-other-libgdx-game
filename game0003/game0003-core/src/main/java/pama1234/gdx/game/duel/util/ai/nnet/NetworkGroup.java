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
    this.vision=createNetworkModule(input,logicInput,vision);
    this.logic=createNetworkModule(logicInput,logicOutput,logic);
    this.behavior=createNetworkModule(logicOutput,output,behavior);
    this.world=createNetworkModule(output,input,world);
  }
  public NetworkModule createNetworkModule(FloatBlock input,FloatBlock logicInput,Network vision) {
    NetworkModule out=new NetworkModule(vision,input,logicInput);
    out.network.floatData=new float[1];
    return out;
  }
  public void execute() {
    vision.execute();
    logic.execute();
    behavior.execute();
  }
  public void setScore(float score) {
    vision.network.floatData[0]=score;
    logic.network.floatData[0]=score;
    behavior.network.floatData[0]=score;
    world.network.floatData[0]=score;
  }
}