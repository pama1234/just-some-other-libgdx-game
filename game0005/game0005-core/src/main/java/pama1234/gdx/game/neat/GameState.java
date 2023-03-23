package pama1234.gdx.game.neat;

public class GameState{
  private int score;
  private double playerX,playerY;
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
  public double getPlayerX() {
    return playerX;
  }
  public double getPlayerY() {
    return playerY;
  }
}