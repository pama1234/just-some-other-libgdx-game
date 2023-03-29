package pama1234.gdx.game.duel.util.ai.nnet;

import pama1234.util.neat.raimannma.architecture.FloatBlock;
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
  public NetworkGroup() {//TODO
    vision=new NetworkModule(null,input,logicInput);
    logic=new NetworkModule(null,logicInput,logicOutput);
    behavior=new NetworkModule(null,logicOutput,output);
    world=new NetworkModule(null,output,input);
  }
  public void execute() {
    vision.execute();
    logic.execute();
    behavior.execute();
  }
}