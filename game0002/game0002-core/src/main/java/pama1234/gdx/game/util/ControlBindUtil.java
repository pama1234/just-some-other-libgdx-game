package pama1234.gdx.game.util;

import com.badlogic.gdx.Input.Keys;

import pama1234.gdx.util.AbstractControlBindUtil;

public class ControlBindUtil extends AbstractControlBindUtil{
  public static final int doUpdate=0,addViewSpeed=1,subViewSpeed=2;
  public static final int camZoomIn=10,camZoomOut=11;
  {
    keyCodeArray=new int[][] {
      {Keys.Z,Keys.SPACE},
      {Keys.X},
      {Keys.C},
      {Keys.R},
      {Keys.F},
      {Keys.H},
      {Keys.N},
      {Keys.M},
      {Keys.T},
      {Keys.P},
      {Keys.EQUALS},
      {Keys.MINUS},
    };
  }
}
