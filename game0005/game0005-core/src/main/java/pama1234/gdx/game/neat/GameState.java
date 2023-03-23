package pama1234.gdx.game.neat;

import com.badlogic.gdx.math.MathUtils;

public class GameState{
  private int score;
  private float playerX,playerY;
  private float enemyX,enemyY; // Declare enemy xy variable
  // Declare obstacleXY variable
  private float obstacleX,obstacleY;
  private boolean isGameOver;
  public GameState() {
    score=0;
    isGameOver=false;
  }
  public int getScore() {
    return score;
  }
  public void setScore(int score) {
    this.score=score;
  }
  public void increaseScore(int increment) {
    this.score+=increment;
  }
  public boolean isGameOver() {
    return isGameOver;
  }
  public void setGameOver(boolean isGameOver) {
    this.isGameOver=isGameOver;
  }
  public float getPlayerX() {
    return playerX;
  }
  public float getPlayerY() {
    return playerY;
  }
  public float getEnemyX() {
    return enemyX;
  }
  public float getEnemyY() {
    return enemyY;
  }
  public float getObstacleX() {
    return obstacleX;
  }
  public void setObstacleX(float obstacleX) {
    this.obstacleX=obstacleX;
  }
  public float getObstacleY() {
    return obstacleY;
  }
  public void setObstacleY(float obstacleY) {
    this.obstacleY=obstacleY;
  }
  public void update(boolean shouldJump) {
    if(!isGameOver) {
      playerY+=GameConfig.GRAVITY;
      if(shouldJump) {
        playerY-=GameConfig.JUMP_SPEED;
      }
      enemyX-=GameConfig.ENEMY_SPEED;
      if(enemyX<-GameConfig.ENEMY_WIDTH) {
        enemyX=GameConfig.WORLD_WIDTH;
        enemyY=MathUtils.random(GameConfig.OBSTACLE_Y_MIN,GameConfig.OBSTACLE_Y_MAX);
        increaseScore(1);
      }
      obstacleX-=GameConfig.OBSTACLE_SPEED;
      if(obstacleX<-GameConfig.OBSTACLE_WIDTH) {
        obstacleX=GameConfig.WORLD_WIDTH;
        obstacleY=MathUtils.random(GameConfig.OBSTACLE_Y_MIN,GameConfig.OBSTACLE_Y_MAX);
        increaseScore(1);
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
    }
  }
}