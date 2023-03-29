package pama1234.gdx.game.duel.util.ai.nnet;

import pama1234.gdx.game.duel.util.actor.PlayerActor;
import pama1234.gdx.game.duel.util.player.PlayerEngine;

public class ComputerLifeEngine extends PlayerEngine{
  public NetworkGroup networks;
  public ComputerLifeEngine(NetworkGroup networks) {
    this.networks=networks;
  }
  @Override
  public void run(PlayerActor player) {}
  /**
   * <pre>
   * {@code
   * 业务逻辑如下：
   *    1. 视觉（梯形）
   *    2. 业务逻辑（长方形）
   *    3. 动作执行（梯形）
   *    +----1----+----2---+----3---+
   * 
   *    *+++··
   *    *+++++++··*········*····
   *    *+++++++++>++++++++>++++++··>
   *    *+++++++··*········*····
   *    *+++··
   * 
   * +--> vision -> logic -> behavior -+
   * |                                 |
   * +------------- world <------------+
   * }</pre>
   */
  public static class NetworkGroup{
    public NetworkModule vision,logic,behavior,world;
  }
}