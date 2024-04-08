package pama1234.gdx.game.sandbox.platformer.world;

import pama1234.math.UtilMath;

public class WorldSettings{
  public enum GameDifficulty{
    Peaceful,Easy,Normal,Hard;
  }
  public int blockWidth=18,blockHeight=18;
  public float g=1f,jumpForce=-blockHeight*1.5f;
  public int daySize=72000;//20 minute
  public int lightDist=7;
  public float lightCount=UtilMath.sq(lightDist)*UtilMath.PI;
  public GameDifficulty difficulty=GameDifficulty.Peaceful;
}