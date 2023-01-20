package pama1234.gdx.game.state.state0001.game.world;

import pama1234.math.UtilMath;

public class WorldSettings{
  public int blockWidth=18,blockHeight=18;
  public float g=1f,jumpForce=-blockHeight*1.5f;
  // public int daySize=216000/3;
  public int daySize=72000;//20 minute
  // public int daySize=7200;//2 minute
  // public int daySize=3600;//1 minute
  // public int time=daySize/4*3;
  // public float ambientLight;
  public int lightDist=7;
  // public int lightDist=7,lightCount=(int)(UtilMath.sq(lightDist)*UtilMath.PI);
  public float lightCount=UtilMath.sq(lightDist)*UtilMath.PI;
  // public int lightDist=7,lightCount=UtilMath.sq(lightDist*2+1);
}
