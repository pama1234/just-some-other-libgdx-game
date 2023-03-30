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
  public NetworkGroup(NetworkGroupData in) {
    vision=new NetworkModule(in.vision,input,logicInput);
    logic=new NetworkModule(in.logic,logicInput,logicOutput);
    behavior=new NetworkModule(in.behavior,logicOutput,output);
    world=new NetworkModule(in.world,output,input);
  }
  public void execute() {
    vision.execute();
    logic.execute();
    behavior.execute();
  }
  public static class NetworkGroupData{
    public Network vision,logic,behavior,world;
    public NetworkGroupData() {}
    public NetworkGroupData(Network vision,Network logic,Network behavior,Network world) {
      this.vision=vision;
      this.logic=logic;
      this.behavior=behavior;
      this.world=world;
    }
  }
}