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
  public NetworkModule vision,logic,behavior,world;
  public NetworkGroup() {}
  public void execute() {
    vision.execute();
    logic.execute();
    behavior.execute();
  }
}