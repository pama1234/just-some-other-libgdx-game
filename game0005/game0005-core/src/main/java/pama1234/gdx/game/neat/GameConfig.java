package pama1234.gdx.game.neat;

public class GameConfig{
  public static final int SCREEN_WIDTH=800;
  public static final int SCREEN_HEIGHT=600;
  public static final float WORLD_WIDTH=20f;
  public static final float WORLD_HEIGHT=15f;
  public static final float GRAVITY=-9.81f;
  public static final float GROUND_Y=1.5f;
  public static final float OBSTACLE_SPAWN_TIME=2f;
  public static final float OBSTACLE_SPEED=5f;
  public static final float OBSTACLE_WIDTH=1f;
  public static final float OBSTACLE_HEIGHT=4f;
  public static final float BIRD_WIDTH=1f;
  public static final float BIRD_HEIGHT=1f;
  public static final float BIRD_DENSITY=0.5f;
  public static final float BIRD_GRAVITY_SCALE=3f;
  public static final float BIRD_JUMP_IMPULSE=8f;
  public static final float CAMERA_WIDTH=10f;
  public static final float CAMERA_HEIGHT=7.5f;
  //---
  public static final float JUMP_SPEED=10f;
  public static final float ENEMY_SPEED=4f;
  public static final float ENEMY_WIDTH=1f;
  public static final float ENEMY_HEIGHT=1.5f;
  public static final float OBSTACLE_Y_MIN=2f;
  public static final float OBSTACLE_Y_MAX=10f;
  public static final float PLAYER_WIDTH=1f;
  public static final float PLAYER_HEIGHT=1.5f;
  //---
  public static final float PLAYER_START_X=2f;
  public static final float PLAYER_START_Y=WORLD_HEIGHT/2f;
  public static final float ENEMY_START_X=WORLD_WIDTH+ENEMY_WIDTH/2f;
  public static final float OBSTACLE_START_X=WORLD_WIDTH+OBSTACLE_WIDTH/2f;
}