package pama1234.gdx.game.app;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;

import pama1234.gdx.game.asset.ImageAsset;
import pama1234.gdx.game.neat.GameState;
import pama1234.gdx.game.neat.InputHandler;
import pama1234.gdx.game.neat.NEAT;
import pama1234.gdx.game.neat.NEATConfig;
import pama1234.gdx.util.app.ScreenCore2D;

public class Screen0019 extends ScreenCore2D{
  private NEAT neat;
  private GameState gameState;
  private InputHandler inputHandler;
  @Override
  public void setup() {
    // 初始化 NEAT 参数
    NEATConfig config=new NEATConfig();
    // 设置输入和输出节点数
    config.setNumInputs(3); // 输入：玩家位置、敌人位置、障碍物位置
    config.setNumOutputs(1); // 输出：玩家是否跳跃
    // 设置种群大小和迭代次数
    config.setPopulationSize(50);
    config.setMaxGenerations(1000);
    // 创建 NEAT 实例
    neat=new NEAT(config);
    // 初始化游戏状态
    gameState=new GameState();
    // 初始化输入处理器
    inputHandler=new InputHandler(gameState);
    // 设置输入监听器
    Gdx.input.setInputProcessor(inputHandler);
    //---
    // Initialize AssetManager
    AssetManager assetManager=new AssetManager();
    // Load ImageAsset using AssetManager
    ImageAsset.load_0001(assetManager);
    while(true) {
      if(assetManager.update()) {
        ImageAsset.put_0001(assetManager);
        break;
      }
    }
  }
  @Override
  public void update() {}
  @Override
  public void displayWithCam() {}
  @Override
  public void display() {
    // 更新 NEAT 神经网络
    neat.update();
    // 获取 NEAT 产生的决策，并应用到游戏状态中
    float[] inputs=new float[] {
      gameState.getPlayerX(),
      gameState.getEnemyX(),
      gameState.getObstacleX()
    };
    float[] outputs=neat.evaluate(inputs);
    boolean shouldJump=outputs[0]>0.5f;
    gameState.update(shouldJump);
    // 渲染游戏画面
    renderGame();
  }
  @Override
  public void frameResized() {}
  public void renderGame() {
    // 渲染玩家
    image(ImageAsset.playerTexture,gameState.getPlayerX(),gameState.getPlayerY());
    // 渲染敌人
    image(ImageAsset.enemyTexture,gameState.getEnemyX(),gameState.getEnemyY());
    // 渲染障碍物
    image(ImageAsset.obstacleTexture,gameState.getObstacleX(),gameState.getObstacleY());
  }
}