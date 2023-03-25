package pama1234.gdx.game.neat.game;

import com.badlogic.gdx.math.MathUtils;

public class GameState{
  public int score;
  public float playerX,playerY;
  public float enemyX,enemyY;
  public float obstacleX,obstacleY;
  public boolean isGameOver;
  public GameState() {
    score=0;
    isGameOver=false;
  }
  public void update(float[] in) {
    boolean shouldJump=in[0]>0.5f;
    if(!isGameOver) {
      playerY+=GameConfig.GRAVITY;
      if(shouldJump) {
        playerY-=GameConfig.JUMP_SPEED;
      }
      enemyX-=GameConfig.ENEMY_SPEED;
      if(enemyX<-GameConfig.ENEMY_WIDTH) {
        enemyX=GameConfig.WORLD_WIDTH;
        enemyY=MathUtils.random(GameConfig.OBSTACLE_Y_MIN,GameConfig.OBSTACLE_Y_MAX);
        score+=1;
      }
      obstacleX-=GameConfig.OBSTACLE_SPEED;
      if(obstacleX<-GameConfig.OBSTACLE_WIDTH) {
        obstacleX=GameConfig.WORLD_WIDTH;
        obstacleY=MathUtils.random(GameConfig.OBSTACLE_Y_MIN,GameConfig.OBSTACLE_Y_MAX);
        score+=1;
      }
      if(playerY<=GameConfig.PLAYER_HEIGHT/2||playerY>=GameConfig.WORLD_HEIGHT-GameConfig.PLAYER_HEIGHT/2) {
        isGameOver=true;
      }
      if(enemyX<=playerX+GameConfig.PLAYER_WIDTH/2&&enemyX>=playerX-GameConfig.ENEMY_WIDTH&&enemyY<=playerY+GameConfig.PLAYER_HEIGHT/2&&enemyY>=playerY-GameConfig.ENEMY_HEIGHT) {
        isGameOver=true;
      }
      if(obstacleX<=playerX+GameConfig.PLAYER_WIDTH/2&&obstacleX>=playerX-GameConfig.OBSTACLE_WIDTH&&obstacleY<=playerY+GameConfig.PLAYER_HEIGHT/2&&obstacleY>=playerY-GameConfig.OBSTACLE_HEIGHT) {
        isGameOver=true;
      }
    }else reset();
  }
  public void reset() {
    score=0;
    isGameOver=false;
    playerX=GameConfig.PLAYER_START_X;
    playerY=GameConfig.PLAYER_START_Y;
    enemyX=GameConfig.ENEMY_START_X;
    enemyY=MathUtils.random(GameConfig.OBSTACLE_Y_MIN,GameConfig.OBSTACLE_Y_MAX);
    obstacleX=GameConfig.OBSTACLE_START_X;
    obstacleY=MathUtils.random(GameConfig.OBSTACLE_Y_MIN,GameConfig.OBSTACLE_Y_MAX);
  }
}